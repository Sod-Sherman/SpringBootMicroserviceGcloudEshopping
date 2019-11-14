package edu.mum.shared.models;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OrderItem {
	private int productId;
	private int quantity;
}
