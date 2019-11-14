package edu.mum.ea2.pays_service.interfaces;


import edu.mum.shared.interfaces.PaysPpServiceShowcase;
import org.springframework.cloud.openfeign.FeignClient;

@FeignClient(name = "pays-pp-service"
//		, url = "http://localhost:8093"
)
public interface PaysPpService extends PaysPpServiceShowcase { }

