package edu.mum.ea2.orders_service;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;


@SpringBootApplication
@EnableFeignClients
public class Orders_serviceApplication {
	public static void main(String[] args) {
		SpringApplication.run(Orders_serviceApplication.class, args);
	}

}
