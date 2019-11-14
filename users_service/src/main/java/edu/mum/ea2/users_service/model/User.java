package edu.mum.ea2.users_service.model;

import lombok.Data;
import lombok.ToString;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@Data@ToString@Entity
public class User implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String fullName;

    private String username;

    private String password;

    private String email;

    @ElementCollection(fetch = FetchType.EAGER)
    List<Role> roles;


}
