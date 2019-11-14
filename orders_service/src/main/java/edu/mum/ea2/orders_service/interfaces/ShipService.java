package edu.mum.ea2.orders_service.interfaces;

import edu.mum.shared.interfaces.ShipServiceShowcase;
import org.springframework.cloud.openfeign.FeignClient;

@FeignClient(name = "ship-service"
//		, url = "http://localhost:8095"
)
public interface ShipService extends ShipServiceShowcase { }

