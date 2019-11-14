package edu.mum.ea2.orders_service.repos;

import edu.mum.ea2.orders_service.entities.OrderEntity;
import edu.mum.ea2.orders_service.entities.OrderItemEntity;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface OrdersItemsRepo extends CrudRepository<OrderItemEntity, Integer> {

//	@Query("select x from OrderItemEntity x where x.order.id = :id")
//	List<OrderItemEntity> findAllByOrderId(int id);

}
