package edu.mum.ea2.products_service.entites;


import edu.mum.shared.models.Product;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;

@Getter
@Setter
@ToString
@Entity
@Table(name="Products")
public class ProductEntity {

	public ProductEntity() {}

	public ProductEntity(Product model) {
		if (model == null) return;

		this.id = model.getId();
		this.name = model.getName();
		this.vendor = model.getVendor();
		this.category = model.getCategory();
		this.availableCount = model.getAvailableCount();
		this.price = model.getPrice();

	}

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

	private String name;

	private String vendor;

	private String category;

	private int availableCount;

	private int price;

	public Product toProductModel(){
		Product product = new Product();
		product.setId(this.id);
		product.setName(this.name);
		product.setVendor(this.vendor);
		product.setCategory(this.category);
		product.setAvailableCount(this.availableCount);
		product.setPrice(this.price);
		return product;
	}

}
