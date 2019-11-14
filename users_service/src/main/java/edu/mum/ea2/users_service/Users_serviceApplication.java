package edu.mum.ea2.users_service;

import edu.mum.ea2.users_service.model.Role;
import edu.mum.ea2.users_service.model.User;
import edu.mum.ea2.users_service.service.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.ArrayList;
import java.util.Arrays;

@SpringBootApplication
public class Users_serviceApplication implements CommandLineRunner {

    @Autowired
    UserService userService;

    public static void main(String[] args) {
        SpringApplication.run(Users_serviceApplication.class, args);
    }
    @Bean
    public ModelMapper modelMapper() {
        return new ModelMapper();
    }

    @Override
    public void run(String... params) throws Exception {
        try {
            User admin = new User();
            admin.setUsername("admin");
            admin.setPassword("admin");
            admin.setEmail("admin@email.com");
            admin.setRoles(new ArrayList<Role>(Arrays.asList(Role.ROLE_ADMIN)));

            userService.signup(admin);

            User client = new User();
            client.setUsername("client");
            client.setPassword("client");
            client.setEmail("client@email.com");
            client.setRoles(new ArrayList<Role>(Arrays.asList(Role.ROLE_CLIENT)));

            userService.signup(client);
        }catch (Exception e){
            System.out.println("CommandLineRunner exception = " + e);
        }
    }


}
