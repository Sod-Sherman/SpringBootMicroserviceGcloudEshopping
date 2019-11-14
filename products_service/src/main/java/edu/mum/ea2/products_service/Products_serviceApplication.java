package edu.mum.ea2.products_service;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

@EnableDiscoveryClient
@SpringBootApplication
@EnableFeignClients
public class Products_serviceApplication {
	public static void main(String[] args) {
		SpringApplication.run(Products_serviceApplication.class, args);
	}
}
