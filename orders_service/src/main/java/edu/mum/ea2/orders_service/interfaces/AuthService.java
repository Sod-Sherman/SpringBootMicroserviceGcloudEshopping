package edu.mum.ea2.orders_service.interfaces;


import edu.mum.shared.interfaces.AuthServiceShowcase;
import org.springframework.cloud.openfeign.FeignClient;

@FeignClient(name = "auth-service"
//		, url = "http://localhost:8081"
)
public interface AuthService extends AuthServiceShowcase { }

