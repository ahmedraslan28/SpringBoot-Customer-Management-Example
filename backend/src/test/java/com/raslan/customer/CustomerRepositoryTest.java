package com.raslan.customer;

import com.raslan.TestContainersAbstract;
import com.raslan.TestSecurityConfig;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Import(TestSecurityConfig.class)
class CustomerRepositoryTest extends TestContainersAbstract {

    @Autowired
    private CustomerRepository underTest ;

    @Test
    void existsByEmail() {
        String email = faker.internet().emailAddress() ;
        Customer customer = new Customer(
                faker.name().fullName(),
                25,
                email,
                "password",
                Gender.FEMALE
        );

        underTest.save(customer);

        boolean actual = underTest.existsByEmail(email);

        assertThat(actual).isTrue() ;
    }
}