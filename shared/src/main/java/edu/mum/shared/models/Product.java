package edu.mum.shared.models;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class Product {

	private int id;

	private String name;

	private String vendor;

	private String category;

	private int availableCount;

	private int price;
}
