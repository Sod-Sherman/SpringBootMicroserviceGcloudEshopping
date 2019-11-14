package edu.mum.ea2.products_service.interfaces;

import edu.mum.shared.interfaces.StockServiceShowcase;
import org.springframework.cloud.openfeign.FeignClient;

@FeignClient(name = "stock-service"
//		, url = "http://localhost:8096"
)
public interface StockService extends StockServiceShowcase { }