package edu.mum.ea2.products_service.repos;

import edu.mum.ea2.products_service.entites.ProductEntity;
import org.springframework.data.repository.CrudRepository;

public interface ProductsRepo extends CrudRepository<ProductEntity, Integer> {

	ProductEntity findById(int id);

}
