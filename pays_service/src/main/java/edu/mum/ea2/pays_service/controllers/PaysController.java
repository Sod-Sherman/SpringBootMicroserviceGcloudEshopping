package edu.mum.ea2.pays_service.controllers;

import edu.mum.ea2.pays_service.interfaces.PaysCcService;
import edu.mum.ea2.pays_service.interfaces.PaysBaService;
import edu.mum.ea2.pays_service.interfaces.PaysPpService;
import edu.mum.shared.models.Payment;
import edu.mum.shared.models.Result;
import edu.mum.shared.utils.EaUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/pays")
public class PaysController {
	@Value("${service-secret}")
	private String serviceSecret;

	@Autowired
	PaysCcService paysCcService;

	@Autowired
	PaysBaService paysBaService;

	@Autowired
	PaysPpService paysPpService;

	@PostMapping(value = "/pay")
	public ResponseEntity<Result> pay(@RequestBody Payment payment, HttpServletRequest request){
		System.out.println(payment);
		if (!EaUtils.isServiceAuthorized(request, serviceSecret))
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();

		// Paying with Credit card
		if (payment.getType().equals("cc")) {
			return paysCcService.pay(payment, serviceSecret);
		}

		// Paying with Bank Account
		if (payment.getType().equals("ba")) {
			return paysBaService.pay(payment, serviceSecret);
		}

		// Paying with Pay pal
		if (payment.getType().equals("pp")) {
			return paysPpService.pay(payment, serviceSecret);
		}

		return ResponseEntity.badRequest().body(new Result(false, "Can't detect card type. Use: cc, ba, pp"));
	}

}
