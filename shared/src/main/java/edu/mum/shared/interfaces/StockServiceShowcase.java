package edu.mum.shared.interfaces;

import edu.mum.shared.models.Product;
import edu.mum.shared.models.Result;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;

public interface StockServiceShowcase {
	@PostMapping(value = "/stock/check-product-amount")
	ResponseEntity<Result> checkProductAmount(@RequestBody Product product, @RequestHeader("service-secret") String serviceSecret);
}
