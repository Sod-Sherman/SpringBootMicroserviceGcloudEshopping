package edu.mum.shared.interfaces;

import edu.mum.shared.models.Product;
import edu.mum.shared.models.Result;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

public interface ProductsServiceShowcase {

	@GetMapping("/products/get/{id}")
	Product findById(@PathVariable(value = "id") int id);

	@PostMapping("/products/decrease/{id}/{quantity}")
	ResponseEntity<Result> decreaseAvailableCount(@PathVariable(name = "id") int id, @PathVariable(name = "quantity") int quantity,  @RequestHeader("service-secret") String serviceSecret);

	@PostMapping("/products/increase/{id}/{quantity}")
	ResponseEntity<Result> increaseAvailableCount(@PathVariable(name = "id") int id, @PathVariable(name = "quantity") int quantity,  @RequestHeader("service-secret") String serviceSecret);
}
