package edu.mum.shared.interfaces;

import edu.mum.shared.models.Order;
import edu.mum.shared.models.Payment;
import edu.mum.shared.models.Result;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;

public interface ShipServiceShowcase {

	@RequestMapping("/ship/order-placed")
	ResponseEntity<Result> orderPlaced(@RequestBody Order order, @RequestHeader("service-secret") String serviceSecret);

}
