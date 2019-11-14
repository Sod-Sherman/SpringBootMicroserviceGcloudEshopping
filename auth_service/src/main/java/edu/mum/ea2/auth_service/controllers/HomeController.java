package edu.mum.ea2.auth_service.controllers;

import edu.mum.ea2.auth_service.entities.UserEntity;
import edu.mum.ea2.auth_service.repos.UsersRepo;
import edu.mum.shared.models.User;
import edu.mum.shared.utils.EaUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.net.InetAddress;
import java.net.UnknownHostException;

@RestController
public class HomeController {
	@Autowired
	UsersRepo usersRepo;

	@GetMapping("/")
	public ResponseEntity<?> index() {
		String host = "Unknown host";
		try {
			host = InetAddress.getLocalHost().getHostName();
		} catch (UnknownHostException e) {
			e.printStackTrace();
		}

		return new ResponseEntity<>("auth-service. Host: " + host, HttpStatus.OK);
	}

	@Value("${service-secret}")
	private String serviceSecret;

	@GetMapping("/test")
	public String firstPage() {

		return "Ok. You authenticated user";
	}

	@GetMapping("/user/{id}")
	public ResponseEntity<User> getUserInfo(@PathVariable(name = "id") int id, HttpServletRequest request) {
		if (!EaUtils.isServiceAuthorized(request, serviceSecret))
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();

		UserEntity entity = usersRepo.findById(id);

		if (entity == null) return ResponseEntity.noContent().build();

		return ResponseEntity.ok(entity.toUserModel());
	}
}
