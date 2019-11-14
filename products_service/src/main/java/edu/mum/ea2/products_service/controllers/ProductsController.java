package edu.mum.ea2.products_service.controllers;

import edu.mum.ea2.products_service.entites.ProductEntity;
import edu.mum.ea2.products_service.interfaces.StockService;
import edu.mum.ea2.products_service.repos.ProductsRepo;
import edu.mum.shared.models.Product;
import edu.mum.shared.models.Result;
import edu.mum.shared.utils.EaUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/products")
public class ProductsController {
	@Value("${service-secret}")
	private String serviceSecret;

	@Autowired
	ProductsRepo productsRepo;

	@Autowired
	StockService stockService;

	@GetMapping("/")
	public ResponseEntity<?> index() {
		String host = "Unknown host";
		try {
			host = InetAddress.getLocalHost().getHostName();
		} catch (UnknownHostException e) {
			e.printStackTrace();
		}

		return new ResponseEntity<>("products-service. Host: " + host, HttpStatus.OK);
	}

	// accessible without authentication
	@GetMapping("/get/{id}")
	public ResponseEntity<Product> getProduct(@PathVariable(name = "id") int id) {
		ProductEntity entity = productsRepo.findById(id);
		if (entity == null) return ResponseEntity.noContent().build();

		return ResponseEntity.ok(entity.toProductModel());
	}

	// accessible without authentication
	@GetMapping("/list")
	public ResponseEntity<List<Product>> getProductsList() {
		List<ProductEntity> entitiesList = EaUtils.iterableToCollection(productsRepo.findAll());
		List<Product> productList = entitiesList.stream().map(x -> x.toProductModel()).collect(Collectors.toList());

		return ResponseEntity.ok(productList);
	}

	// authentication required
	@PostMapping("/add")
	public ResponseEntity<Result> add(@RequestBody Product model) {
		System.out.println(model);

		if (model == null) return ResponseEntity.ok().body(new Result(false, "Incorrect model"));

		try {
			productsRepo.save(new ProductEntity(model));
		} catch (Exception ex) {
			return ResponseEntity.ok().body(new Result(false, ex.getMessage()));
		}

		return ResponseEntity.ok(new Result(true, "Saved successfully"));
	}

	// authentication required
	@DeleteMapping("/delete/{id}")
	public ResponseEntity<Result> deleteProduct(@PathVariable(name = "id") int id) {
		try {
			productsRepo.deleteById(id);
		} catch (Exception ex) {
			return ResponseEntity.ok().body(new Result(false, ex.getMessage()));
		}
		return ResponseEntity.ok().body(new Result(true, "Deleted successfully"));
	}

	// service token required
	@PostMapping("/decrease/{id}/{quantity}")
	public ResponseEntity<Result> decreaseAvailableCount(@PathVariable(name = "id") int id, @PathVariable(name = "quantity") int quantity, HttpServletRequest request) {
		if (!EaUtils.isServiceAuthorized(request, serviceSecret))
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
		System.out.println("decreaseAvailableCount. productId: " + id + ", quantity: " + quantity);

		ProductEntity entity = productsRepo.findById(id);
		if (entity == null) return ResponseEntity.ok().body(new Result(false, "Can't find product with id: " + id));
		if (entity.getAvailableCount() < quantity) return ResponseEntity.ok().body(new Result(false, "Product " + entity.getName() + " available count = " + entity.getAvailableCount() + " items. Can't place order for buy " + quantity + " items"));

		try {
			entity.setAvailableCount(entity.getAvailableCount() - quantity);
			productsRepo.save(entity);
		} catch (Exception ex) {
			return ResponseEntity.ok().body(new Result(false, ex.getMessage()));
		}

		// Check available count using Stock service
		stockService.checkProductAmount(entity.toProductModel(), serviceSecret);

		return ResponseEntity.ok(new Result(true, "Product available count decreased successfully"));
	}

	// service token required
	@PostMapping("/increase/{id}/{quantity}")
	public ResponseEntity<Result> increaseAvailableCount(@PathVariable(name = "id") int id, @PathVariable(name = "quantity") int quantity, HttpServletRequest request) {
		if (!EaUtils.isServiceAuthorized(request, serviceSecret))
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
		System.out.println("increaseAvailableCount. productId: " + id + ", quantity: " + quantity);

		ProductEntity entity = productsRepo.findById(id);
		if (entity == null) return ResponseEntity.ok().body(new Result(false, "Can't find product with id: " + id));

		try {
			entity.setAvailableCount(entity.getAvailableCount() + quantity);
			productsRepo.save(entity);
		} catch (Exception ex) {
			return ResponseEntity.ok().body(new Result(false, ex.getMessage()));
		}

		return ResponseEntity.ok(new Result(true, "Product available count increased successfully"));
	}

}
