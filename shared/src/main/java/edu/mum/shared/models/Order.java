package edu.mum.shared.models;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Date;
import java.util.List;

@Getter
@Setter
@ToString
public class Order {
	private int id;

	private Date orderDate;

	private int userId;

	private String adrStreet;
	private String adrCity;
	private String adrState;
	private String adrZip;

	private String payType;
	private String payCardNumber;
	private String payHolderName;
	private String payCvc;
	private String payBankAccountNumber;
	private String payPayPalUsername;

	private List<OrderItem> items;
}
