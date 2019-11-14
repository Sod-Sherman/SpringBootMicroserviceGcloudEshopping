package edu.mum.ea2.orders_service.controllers;

import edu.mum.ea2.orders_service.entities.OrderEntity;
import edu.mum.ea2.orders_service.entities.OrderItemEntity;
import edu.mum.ea2.orders_service.interfaces.*;
import edu.mum.ea2.orders_service.repos.OrdersItemsRepo;
import edu.mum.ea2.orders_service.repos.OrdersRepo;
import edu.mum.shared.models.*;
import edu.mum.shared.utils.EaUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/orders")
public class OrdersController {
	@Value("${service-secret}")
	private String serviceSecret;

	@Autowired
	OrdersRepo ordersRepo;

	@Autowired
	OrdersItemsRepo ordersItemsRepo;

	@Autowired
	ShipService shipService;

	@Autowired
	AuthService authService;

	@Autowired
	PaysService paysService;

	@Autowired
	ProductsService productsService;

	@GetMapping("/")
	public ResponseEntity<?> index() {
		String host = "Unknown host";
		try {
			host = InetAddress.getLocalHost().getHostName();
		} catch (UnknownHostException e) {
			e.printStackTrace();
		}

		return new ResponseEntity<>("orders-service. Host: " + host, HttpStatus.OK);
	}

	@GetMapping("/get/{id}")
	public ResponseEntity<Order> getById(@PathVariable(value = "id") int id) {
		System.out.println(id);

		OrderEntity orderEntity = ordersRepo.findById(id);
		if (orderEntity == null) return ResponseEntity.noContent().build();

		return ResponseEntity.ok(orderEntity.toOrderModel());
	}

	@GetMapping("/list")
	public ResponseEntity<List<Order>> getAll() {
		List<OrderEntity> orderEntityList = EaUtils.iterableToCollection(ordersRepo.findAll());

		List<Order> orders = orderEntityList.stream().map(x -> x.toOrderModel()).collect(Collectors.toList());

		return ResponseEntity.ok(orders);
	}

	@PostMapping("/place-order")
	public ResponseEntity<Result> placeOrder(@RequestBody PlaceOrder model) {
		System.out.println("Placing new order");
		System.out.println(model);

		// 1. Check data
		if (model == null) return ResponseEntity.ok().body(new Result(false, "Incorrect model"));
		if (model.getUserId() == 0) return ResponseEntity.ok().body(new Result(false, "Incorrect user id"));
		if (!model.isUsePreferredAddress()) {
			// if not using preferred address
			if (model.getAdrStreet().isEmpty())
				return ResponseEntity.ok().body(new Result(false, "Please specify address street"));
			if (model.getAdrCity().isEmpty())
				return ResponseEntity.ok().body(new Result(false, "Please specify address city"));
			if (model.getAdrState().isEmpty())
				return ResponseEntity.ok().body(new Result(false, "Please specify address state"));
			if (model.getAdrZip().isEmpty())
				return ResponseEntity.ok().body(new Result(false, "Please specify address zip"));
		}

		// 2. Retrieve user data
		ResponseEntity<User> responseEntity = authService.getUserInfo(model.getUserId(), serviceSecret);

		if (responseEntity.getStatusCode() != HttpStatus.OK)
			return ResponseEntity.ok().body(new Result(false, "Can't access auth service"));

		User user = responseEntity.getBody();

		// 3. Recheck retrieved user data
		if (user == null)
			return ResponseEntity.ok().body(new Result(false, "Can't find user with id = " + model.getUserId()));
		if (model.isUsePreferredAddress()) {
			// if using preferred address
			if (user.getAdrStreet().isEmpty())
				return ResponseEntity.ok().body(new Result(false, "User has no preferred address street"));
			if (user.getAdrCity().isEmpty())
				return ResponseEntity.ok().body(new Result(false, "User has no preferred address city"));
			if (user.getAdrState().isEmpty())
				return ResponseEntity.ok().body(new Result(false, "User has no preferred address state"));
			if (user.getAdrZip().isEmpty())
				return ResponseEntity.ok().body(new Result(false, "User has no preferred address zip"));
		}

		// 4. Retrieve and check products data
		if (model.getItems() == null || model.getItems().size() == 0)
			return ResponseEntity.ok().body(new Result(false, "Please specify products list to buy"));

		for (OrderItem orderItem : model.getItems()) {
			Product product = productsService.findById(orderItem.getProductId());
			if (product.getAvailableCount() < orderItem.getQuantity())
				return ResponseEntity.ok().body(new Result(false, "Product " + product.getName() + " available count = " + product.getAvailableCount() + " items. Can't place order for buy " + orderItem.getQuantity() + " items"));
		}

		// 5. Try to decrease products
		// Automatically check if product available count going out of stock using stock-service
		for (OrderItem orderItem : model.getItems()) {
			ResponseEntity<Result> decreaseResult = productsService.decreaseAvailableCount(orderItem.getProductId(), orderItem.getQuantity(), serviceSecret);
			if (!decreaseResult.getBody().isSuccess())
				return decreaseResult;
		}

		// 6. Determine payment credentials
		Payment paymentCredentials = null;
		if (model.isUsePreferredPaymentMethod()) {
			paymentCredentials = user.getPaymentModel();
			if (paymentCredentials == null)
				return ResponseEntity.ok().body(new Result(false, "User has no preferred payment method"));
		} else {
			if (model.getPayType().equals("cc"))
				paymentCredentials = Payment.newCreditCard(model.getPayCardNumber(), model.getPayHolderName(), model.getPayCvc());
			if (model.getPayType().equals("ba"))
				paymentCredentials = Payment.newBankAccount(model.getPayBankAccountNumber());
			if (model.getPayType().equals("pp")) paymentCredentials = Payment.newPayPal(model.getPayPayPalUsername());
		}

		// 7. Try to pay
		System.out.println("Trying to pay using: " + paymentCredentials.toString());
		if (paymentCredentials == null)
			return ResponseEntity.ok().body(new Result(false, "Error determining your payment credentials"));

		ResponseEntity<Result> pay = paysService.pay(paymentCredentials, serviceSecret);
		if (!pay.getBody().isSuccess()) {
			// if payment was not successful

			// 8. Increase products amount back if payment was wrong
			for (OrderItem orderItem : model.getItems()) {
				ResponseEntity<Result> decreaseResult = productsService.decreaseAvailableCount(orderItem.getProductId(), orderItem.getQuantity(), serviceSecret);
				if (!decreaseResult.getBody().isSuccess())
					return decreaseResult;
			}
			return pay;
		}

		// 9. Save order
		OrderEntity orderEntity = new OrderEntity(model);
		if (model.isUsePreferredAddress()) {
			orderEntity.setAdrStreet(user.getAdrStreet());
			orderEntity.setAdrCity(user.getAdrCity());
			orderEntity.setAdrState(user.getAdrState());
			orderEntity.setAdrZip(user.getAdrZip());
		}
		if (model.isUsePreferredPaymentMethod()) {
			Payment payment = user.getPaymentModel();
			orderEntity.setPayType(payment.getType());
			orderEntity.setPayCardNumber(payment.getCardNumber());
			orderEntity.setPayHolderName(payment.getHolderName());
			orderEntity.setPayCvc(payment.getCvc());
			orderEntity.setPayBankAccountNumber(payment.getBankAccountNumber());
			orderEntity.setPayPayPalUsername(payment.getPayPalUsername());
		}

		try {
			orderEntity = ordersRepo.save(orderEntity);
			if (orderEntity != null) {
				for (OrderItem item : model.getItems()) {
					OrderItemEntity orderItemEntity = new OrderItemEntity(item);
					orderItemEntity.setOrder(orderEntity);
					ordersItemsRepo.save(orderItemEntity);
				}
			}
		} catch (Exception ex) {
			return ResponseEntity.ok().body(new Result(false, ex.getMessage()));
		}

		Order placedOrder = ordersRepo.findById(orderEntity.getId()).toOrderModel();

		// 10. Notify shipping service
		shipService.orderPlaced(placedOrder, serviceSecret);

		// 11. Return result
		return ResponseEntity.ok(new Result(true, "Saved successfully"));
	}
}
