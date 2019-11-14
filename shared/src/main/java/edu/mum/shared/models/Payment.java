package edu.mum.shared.models;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class Payment {
	private String type;

	private String cardNumber;

	private String holderName;

	private String cvc;

	private String bankAccountNumber;

	private String payPalUsername;

	public static Payment newCreditCard(String cardNumber, String holderName, String cvc){
		Payment payment = new Payment();
		payment.setType("cc");
		payment.setCardNumber(cardNumber);
		payment.setHolderName(holderName);
		payment.setCvc(cvc);
		return payment;
	}

	public static Payment newPayPal(String payPalUsername) {
		Payment payment = new Payment();
		payment.setType("pp");
		payment.setPayPalUsername(payPalUsername);
		return payment;
	}

	public static Payment newBankAccount(String bankAccountNumber) {
		Payment payment = new Payment();
		payment.setType("ba");
		payment.setBankAccountNumber(bankAccountNumber);
		return payment;
	}
}
