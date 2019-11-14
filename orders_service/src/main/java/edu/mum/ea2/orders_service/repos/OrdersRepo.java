package edu.mum.ea2.orders_service.repos;

import edu.mum.ea2.orders_service.entities.OrderEntity;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

public interface OrdersRepo extends CrudRepository<OrderEntity, Integer> {

	OrderEntity findById(int id);

}
