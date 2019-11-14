package edu.mum.ea2.stock_service.controllers;

import edu.mum.ea2.stock_service.interfaces.ProductsService;
import edu.mum.shared.models.Product;
import edu.mum.shared.models.Result;
import edu.mum.shared.utils.EaUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/stock")
public class StockController {
	@Value("${service-secret}")
	private String serviceSecret;

	@Value("${minimum-stock-amount}")
	private int minimumStockAmount;

	@Autowired
	ProductsService productsService;

	@PostMapping(value = "/check-product-amount")
	public ResponseEntity<Result> checkProductAmount(@RequestBody Product product, HttpServletRequest request) {
		if (!EaUtils.isServiceAuthorized(request, serviceSecret))
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();

		System.out.println("Checking product available count...");
		System.out.println("Product: " + product.getName() + ", Available count is: " + product.getAvailableCount());

		if (product.getAvailableCount() < minimumStockAmount){
			System.out.println("Available count is not enough. Asking products service to Increase by 10.");
			productsService.increaseAvailableCount(product.getId(), 10, serviceSecret);
		} else{
			System.out.println("Available count is enough.");
		}

		// You can save this info to db or do your own logic

		return ResponseEntity.ok().body(new Result(true, ""));
	}
}
