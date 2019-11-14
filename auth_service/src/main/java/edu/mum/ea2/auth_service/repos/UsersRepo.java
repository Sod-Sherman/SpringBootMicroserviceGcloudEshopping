package edu.mum.ea2.auth_service.repos;

import edu.mum.ea2.auth_service.entities.UserEntity;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UsersRepo extends CrudRepository<UserEntity, Integer> {
    UserEntity findByEmail(String email);

	UserEntity findById(int id);
//
//    @Query("select u.credit from User u where u.id= :id and u.credit.preferred = true")
//    CreditCard findCredit(Integer id);
//
//    @Query("select u.bank from User u where u.id= :id and u.bank.preferred = true")
//    BankAccount findBank(Integer id);
//
//    @Query("select u.paypal from User u where u.id= :id and u.paypal.preferred = true")
//    Paypal findPaypal(Integer id);
//
//    @Query("select u.address from User u where u.id= :id")
//    Address findAddress(Integer id);
}