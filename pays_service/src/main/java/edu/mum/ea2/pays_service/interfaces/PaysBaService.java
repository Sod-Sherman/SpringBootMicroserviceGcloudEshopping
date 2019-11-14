package edu.mum.ea2.pays_service.interfaces;


import edu.mum.shared.interfaces.PaysBaServiceShowcase;
import org.springframework.cloud.openfeign.FeignClient;

@FeignClient(name = "pays-ba-service"
//		, url = "http://localhost:8092"
)
public interface PaysBaService extends PaysBaServiceShowcase { }

