package edu.mum.ea2.ship_service.controllers;

import edu.mum.shared.models.Order;
import edu.mum.shared.models.Payment;
import edu.mum.shared.models.Result;
import edu.mum.shared.utils.EaUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/ship")
public class ShipController {
	@Value("${service-secret}")
	private String serviceSecret;

	@PostMapping(value = "/order-placed")
	public ResponseEntity<Result> orderPlaced(@RequestBody Order order, HttpServletRequest request) {
		if (!EaUtils.isServiceAuthorized(request, serviceSecret))
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();

		System.out.println("New order placed");
		System.out.println(order);
		System.out.println("Please ship this order ASAP");

		// You can save this info to db or do your own logic

		return ResponseEntity.ok().body(new Result(true, "Shipping service notified successfully"));
	}
}
