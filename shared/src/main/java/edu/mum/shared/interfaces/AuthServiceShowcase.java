package edu.mum.shared.interfaces;

import edu.mum.shared.models.User;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;

public interface AuthServiceShowcase {

	@GetMapping("/user/{id}")
	ResponseEntity<User> getUserInfo(@PathVariable(name = "id") int id, @RequestHeader("service-secret") String serviceSecret);
}
