package edu.mum.ea2.stock_service;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;


@EnableDiscoveryClient
@SpringBootApplication
@EnableFeignClients
public class Stock_serviceApplication {
	public static void main(String[] args) {
		SpringApplication.run(Stock_serviceApplication.class, args);
	}
}
