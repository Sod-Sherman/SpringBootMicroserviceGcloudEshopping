package edu.mum.ea2.pays_ba_service.controllers;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.HashMap;

@RestController
public class HomeController {

	@GetMapping("/")
	public ResponseEntity<?> index() {
		String host = "Unknown host";
		try {
			host = InetAddress.getLocalHost().getHostName();
		} catch (UnknownHostException e) {
			e.printStackTrace();
		}

		return new ResponseEntity<>("pays-ba-service. Host: " + host, HttpStatus.OK);
	}
//
//
//	@Value("${jwt-secret}")
//	private String jwtSecret;
//
//	@Value("${service-secret}")
//	private String serviceSecret;
//
//	@GetMapping("/secrets")
//	public ResponseEntity<?> secrets() {
//		HashMap<String, String> secrets = new HashMap<>();
//		secrets.put("jwt-secret", jwtSecret);
//		secrets.put("service-secret", serviceSecret);
//
//		return new ResponseEntity<>(secrets, HttpStatus.OK);
//	}
}
