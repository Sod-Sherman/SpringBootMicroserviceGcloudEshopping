package edu.mum.ea2.orders_service.interfaces;

import edu.mum.shared.interfaces.PaysServiceShowcase;
import org.springframework.cloud.openfeign.FeignClient;

@FeignClient(name = "pays-service"
//		, url = "http://localhost:8090"
)
public interface PaysService extends PaysServiceShowcase { }

