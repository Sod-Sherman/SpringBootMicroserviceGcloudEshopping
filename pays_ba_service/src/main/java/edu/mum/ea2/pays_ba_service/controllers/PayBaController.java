package edu.mum.ea2.pays_ba_service.controllers;

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
@RequestMapping("/pays_ba")
public class PayBaController {
	@Value("${service-secret}")
	private String serviceSecret;

	@PostMapping(value = "/pay")
	public ResponseEntity<Result> pay(@RequestBody Payment payment, HttpServletRequest request) {
		System.out.println(payment);
		if (!EaUtils.isServiceAuthorized(request, serviceSecret))
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();

		if (!payment.getType().equals("ba"))
			return ResponseEntity.ok().body(new Result(false, "Payment type should be Bank Account: ba"));

		if (payment.getBankAccountNumber().length() != 8)
			return ResponseEntity.ok().body(new Result(false, "Bank account number should be 8 chars length"));

		return ResponseEntity.ok().body(new Result(true, "Paid successfully using bank account"));
	}
}
