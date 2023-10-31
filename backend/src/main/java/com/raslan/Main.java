package com.raslan;

import com.github.javafaker.Faker;
import com.raslan.customer.Customer;
import com.raslan.customer.CustomerRepository;
import com.raslan.customer.Gender;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Random;
import java.util.UUID;

@SpringBootApplication
public class Main {
    public static void main(String[] args) {
        SpringApplication.run(Main.class, args);
    }

    @Bean
    CommandLineRunner runner(CustomerRepository customerRepository, PasswordEncoder passwordEncoder) {
        Faker faker = new Faker() ;
        Random random = new Random() ;
        return args -> {
            String firstName = faker.name().firstName().toLowerCase();
            String lastName = faker.name().lastName().toLowerCase();
            int age = random.nextInt(16,99) ;
            Gender gender = age % 2 == 0 ? Gender.MALE : Gender.FEMALE;

            Customer customer = new Customer(
                    firstName + " " + lastName,
                    age,
                    firstName + "_" + lastName + "@StartApp.com",
                    passwordEncoder.encode(UUID.randomUUID().toString()),
                    gender
            );
            System.out.println(customer);
            customerRepository.save(customer);
        };
    }

}


