package edu.mum.ea2.auth_service.entities;

import edu.mum.shared.models.Payment;
import edu.mum.shared.models.UserPaymentMethod;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.ManyToAny;

import javax.persistence.*;

@Getter
@Setter
@ToString
@Entity
@Table(name = "UsersPaymentMethods")
public class UserPaymentMethodEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

	private String type;

	private String cardNumber;

	private String holderName;

	private String cvc;

	private String bankAccountNumber;

	private String payPalUsername;

//	public static edu.mum.shared.models.UserPaymentMethod newCreditCard(String cardNumber, String holderName, String cvc){
//		edu.mum.shared.models.UserPaymentMethod paymentMethod = new edu.mum.shared.models.UserPaymentMethod();
//		paymentMethod.setType("cc");
//		paymentMethod.setCardNumber(cardNumber);
//		paymentMethod.setHolderName(holderName);
//		paymentMethod.setCvc(cvc);
//		return paymentMethod;
//	}
//
//	public static edu.mum.shared.models.UserPaymentMethod newPayPal(String payPalUsername) {
//		edu.mum.shared.models.UserPaymentMethod paymentMethod = new edu.mum.shared.models.UserPaymentMethod();
//		paymentMethod.setType("pp");
//		paymentMethod.setPayPalUsername(payPalUsername);
//		return paymentMethod;
//	}
//
//	public static edu.mum.shared.models.UserPaymentMethod newBankAccount(String bankAccountNumber) {
//		edu.mum.shared.models.UserPaymentMethod paymentMethod = new edu.mum.shared.models.UserPaymentMethod();
//		paymentMethod.setType("ba");
//		paymentMethod.setBankAccountNumber(bankAccountNumber);
//		return paymentMethod;
//	}

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


	public UserPaymentMethod toUserPaymentMethod() {
		UserPaymentMethod payment = new UserPaymentMethod();
		payment.setType(this.type);
		payment.setCardNumber(this.cardNumber);
		payment.setHolderName(this.holderName);
		payment.setCvc(this.cvc);
		payment.setBankAccountNumber(this.bankAccountNumber);
		payment.setPayPalUsername(this.payPalUsername);

		return payment;
	}
}
