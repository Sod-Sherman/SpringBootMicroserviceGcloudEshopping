package edu.mum.ea2.stock_service.interfaces;

import edu.mum.shared.interfaces.ProductsServiceShowcase;
import org.springframework.cloud.openfeign.FeignClient;

@FeignClient(name = "products-service"
//		, url = "http://localhost:8085"
)
public interface ProductsService extends ProductsServiceShowcase { }

