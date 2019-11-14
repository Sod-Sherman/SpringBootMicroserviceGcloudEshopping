package edu.mum.ea2.orders_service.entities;

import edu.mum.shared.models.Order;
import edu.mum.shared.models.PlaceOrder;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Cascade;

import javax.persistence.*;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@Setter
@Entity
@Table(name = "Orders")
public class OrderEntity {

	public OrderEntity() {
	}

	public OrderEntity(PlaceOrder model) {
		if (model == null) return;

		this.orderDate = new Date();
		this.userId = model.getUserId();

		this.adrStreet = model.getAdrStreet();
		this.adrCity = model.getAdrCity();
		this.adrState = model.getAdrState();
		this.adrZip = model.getAdrZip();

		this.payType = model.getPayType();
		this.payCardNumber = model.getPayCardNumber();
		this.payHolderName = model.getPayHolderName();
		this.payCvc = model.getPayCvc();
		this.payBankAccountNumber = model.getPayBankAccountNumber();
		this.payPayPalUsername = model.getPayPayPalUsername();

//		if (model.getItems() != null)
//			this.items = model.getItems().stream().map(x -> new OrderItemEntity(x)).collect(Collectors.toList());
	}

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
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

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "order")
//	@Cascade(org.hibernate.annotations.CascadeType.ALL)
	private List<OrderItemEntity> items;

	public Order toOrderModel() {
		Order model = new Order();
		model.setId(this.id);
		model.setOrderDate(this.orderDate);
		model.setUserId(this.userId);

		model.setAdrStreet(this.adrStreet);
		model.setAdrCity(this.adrCity);
		model.setAdrState(this.adrState);
		model.setAdrZip(this.adrZip);

		model.setPayType(this.payType);
		model.setPayCardNumber(this.payCardNumber);
		model.setPayHolderName(this.payHolderName);
		model.setPayCvc(this.payCvc);
		model.setPayBankAccountNumber(this.payBankAccountNumber);
		model.setPayPayPalUsername(this.payPayPalUsername);
		if (this.items != null)
			model.setItems(this.items.stream().map(x -> x.toOrderItemModel()).collect(Collectors.toList()));

		return model;
	}
}
