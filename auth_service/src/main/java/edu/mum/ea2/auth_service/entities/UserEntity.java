package edu.mum.ea2.auth_service.entities;

import edu.mum.shared.models.User;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import javax.persistence.*;

@Getter
@Setter
@Entity
@ToString
@Table(name = "Users")
public class UserEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String firstName;
    private String lastName;
    private String password;
    private String email;

    // shipping address
    private String adrStreet;
    private String adrCity;
    private String adrState;
    private String adrZip;

    @OneToOne(cascade = CascadeType.ALL)
    UserPaymentMethodEntity paymentCreditCard;

    @OneToOne(cascade = CascadeType.ALL)
    UserPaymentMethodEntity paymentBankAccount;

    @OneToOne(cascade = CascadeType.ALL)
    UserPaymentMethodEntity paymentPayPal;

    String preferredPaymentMethod;      // cc, ba, pp

    public User toUserModel(){
        User user = new User();
        user.setId(this.id);
        user.setFirstName(this.firstName);
        user.setLastName(this.lastName);
        user.setEmail(this.email);

        user.setAdrStreet(this.adrStreet);
        user.setAdrCity(this.adrCity);
        user.setAdrState(this.adrState);
        user.setAdrZip(this.adrZip);

        user.setAdrZip(this.adrZip);

        user.setPaymentCreditCard(this.paymentCreditCard.toUserPaymentMethod());
        user.setPaymentBankAccount(this.paymentBankAccount.toUserPaymentMethod());
        user.setPaymentPayPal(this.paymentPayPal.toUserPaymentMethod());

        user.setPreferredPaymentMethod(this.preferredPaymentMethod);

        return user;
    }
}