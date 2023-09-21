package com.raslan.customer;

import com.raslan.TestContainersAbstract;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class CustomerRepositoryTest extends TestContainersAbstract {

    @Autowired
    private CustomerRepository underTest ;

    @Test
    void existsByEmail() {
        String email = faker.internet().emailAddress() ;
        Customer customer = new Customer(
                faker.name().fullName(),
                25,
                email
        );

        underTest.save(customer);

        boolean actual = underTest.existsByEmail(email);

        assertThat(actual).isTrue() ;
    }
}