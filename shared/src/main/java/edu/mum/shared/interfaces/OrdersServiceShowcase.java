package edu.mum.shared.interfaces;

import edu.mum.shared.models.Order;
import edu.mum.shared.models.Product;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

public interface OrdersServiceShowcase {

	@RequestMapping("/orders/find/{id}")
	Order findById(@PathVariable(value="id") int id);

	@RequestMapping("/orders/find")
	List<Order> findAll();

	@PostMapping("/orders/add")
	Order add(Order model);

//
//	@RequestMapping("/api/getProduct")
//	Product getProduct();
//
//
//	@RequestMapping("/api/getProductsList")
//	List<Product> getProductsList();

}
