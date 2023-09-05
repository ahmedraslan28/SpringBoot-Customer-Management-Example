package com.raslan;

import com.github.javafaker.Faker;
import com.raslan.customer.Customer;
import com.raslan.customer.CustomerRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;

import java.util.Random;

@SpringBootApplication
public class Main {
    public static void main(String[] args) {
        ApplicationContext applicationContext = SpringApplication.run(Main.class, args);
    }

    @Bean
    CommandLineRunner runner(CustomerRepository customerRepository) {
        return args -> {
            String firstName = new Faker().name().firstName().toLowerCase();
            String lastName = new Faker().name().lastName().toLowerCase();
            Random random = new Random();
            Customer customer = new Customer(
                    firstName + " " + lastName,
                    random.nextInt(16, 99),
                    firstName + "_" + lastName + "@raslan.com");
            customerRepository.save(customer);
        };
    }

}


