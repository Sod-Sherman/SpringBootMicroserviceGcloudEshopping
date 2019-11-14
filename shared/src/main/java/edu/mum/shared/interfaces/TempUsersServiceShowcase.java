package edu.mum.shared.interfaces;

import edu.mum.shared.models.Order;
import edu.mum.shared.models.User;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

public interface TempUsersServiceShowcase {

	@RequestMapping("/users/{id}")
	User findById(@PathVariable(value = "id") int id);

//	@RequestMapping("/users")
//	List<User> findAll();
}
