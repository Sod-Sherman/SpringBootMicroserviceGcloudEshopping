package edu.mum.shared.models;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class UserPaymentMethod {
	private String type;

	private String cardNumber;

	private String holderName;

	private String cvc;

	private String bankAccountNumber;

	private String payPalUsername;

	public static UserPaymentMethod newCreditCard(String cardNumber, String holderName, String cvc){
		UserPaymentMethod paymentMethod = new UserPaymentMethod();
		paymentMethod.setType("cc");
		paymentMethod.setCardNumber(cardNumber);
		paymentMethod.setHolderName(holderName);
		paymentMethod.setCvc(cvc);
		return paymentMethod;
	}

	public static UserPaymentMethod newPayPal(String payPalUsername) {
		UserPaymentMethod paymentMethod = new UserPaymentMethod();
		paymentMethod.setType("pp");
		paymentMethod.setPayPalUsername(payPalUsername);
		return paymentMethod;
	}

	public static UserPaymentMethod newBankAccount(String bankAccountNumber) {
		UserPaymentMethod paymentMethod = new UserPaymentMethod();
		paymentMethod.setType("ba");
		paymentMethod.setBankAccountNumber(bankAccountNumber);
		return paymentMethod;
	}

	public Payment toPaymentModel() {
		Payment payment = new Payment();
		payment.setType(this.type);
		payment.setCardNumber(this.cardNumber);
		payment.setHolderName(this.holderName);
		payment.setCvc(this.cvc);
		payment.setBankAccountNumber(this.bankAccountNumber);
		payment.setPayPalUsername(this.payPalUsername);

		return payment;
	}
}
