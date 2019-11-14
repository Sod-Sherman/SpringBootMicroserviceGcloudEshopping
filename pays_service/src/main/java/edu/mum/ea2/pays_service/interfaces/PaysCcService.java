package edu.mum.ea2.pays_service.interfaces;


import edu.mum.shared.interfaces.PaysCcServiceShowcase;
import org.springframework.cloud.openfeign.FeignClient;

@FeignClient(name = "pays-cc-service"
//		, url = "http://localhost:8091"
)
public interface PaysCcService extends PaysCcServiceShowcase { }

