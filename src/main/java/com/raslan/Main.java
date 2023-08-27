package com.raslan;

import com.raslan.customer.Customer;
import com.raslan.customer.CustomerRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;

import java.util.List;

@SpringBootApplication
public class Main {
    public static void main(String[] args) {
        ApplicationContext applicationContext = SpringApplication.run(Main.class, args);

//        String[] beanNames = applicationContext.getBeanDefinitionNames();
//        for (String beanName : beanNames) {
//            System.out.println(beanName);
//        }
    }

    @Bean
    CommandLineRunner runner(CustomerRepository customerRepository) {
        return args -> {
            Customer ahmed = new Customer("ahmed", "23", "ahmedraslan28@gmail.com");
            Customer rana = new Customer("rana", "20", "ranaraslan28@gmail.com");
            customerRepository.saveAll(List.of(ahmed, rana));
        };
    }

}


