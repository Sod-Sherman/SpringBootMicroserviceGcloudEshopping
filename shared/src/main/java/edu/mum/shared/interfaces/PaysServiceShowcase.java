package edu.mum.shared.interfaces;

import edu.mum.shared.models.Payment;
import edu.mum.shared.models.Result;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;

public interface PaysServiceShowcase {

	@RequestMapping("/pays/pay")
	ResponseEntity<Result> pay(@RequestBody Payment payment,  @RequestHeader("service-secret") String serviceSecret);

}
