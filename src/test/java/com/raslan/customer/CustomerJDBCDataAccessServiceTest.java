package com.raslan.customer;

import com.raslan.TestContainersAbstract;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class CustomerJDBCDataAccessServiceTest extends TestContainersAbstract {
    private CustomerJDBCDataAccessService underTest;

    @BeforeEach
    void setUP() {
        underTest = new CustomerJDBCDataAccessService(
                getJdbcTemplate()
        );
    }

    @Test
    void getCustomers() {
        Customer customer = new Customer(
                faker.name().fullName(),
                25,
                faker.internet().emailAddress()
        );

        underTest.createCustomer(customer);

        List<Customer> customers = underTest.getCustomers();
        System.out.println(customers);

        assertThat(customers).isNotEmpty();
    }

    @Test
    void getCustomer() {
        Customer customer = new Customer(
                faker.name().fullName(),
                25,
                faker.internet().emailAddress()
        );

        underTest.createCustomer(customer);

        int id = underTest.getCustomers()
                .stream()
                .filter(c -> c.getEmail().equals(customer.getEmail()))
                .map(Customer::getId)
                .findFirst()
                .orElseThrow();


        Optional<Customer> actual = underTest.getCustomer(id);

        assertThat(actual).isPresent().hasValueSatisfying(c -> {
            assertThat(c.getId()).isEqualTo(id);
            assertThat(c.getName()).isEqualTo(customer.getName());
            assertThat(c.getAge()).isEqualTo(customer.getAge());
            assertThat(c.getEmail()).isEqualTo(customer.getEmail());
        });
    }

    @Test
    void willReturnEmptyWhenGetCustomerById() {
        int id = -1;

        var actual = underTest.getCustomer(id);

        assertThat(actual).isEmpty();
    }

    @Test
    void deleteCustomer() {
        Customer customer = new Customer(
                faker.name().fullName(),
                25,
                faker.internet().emailAddress()
        );

        underTest.createCustomer(customer);

        int id = underTest.getCustomers()
                .stream()
                .filter(c -> c.getEmail().equals(customer.getEmail()))
                .map(Customer::getId)
                .findFirst()
                .orElseThrow();

        underTest.deleteCustomer(id);

        var actual = underTest.existCustomerWithId(id);
        assertThat(actual).isFalse();
    }

    @Test
    void createCustomer() {
        Customer customer = new Customer(
                faker.name().fullName(),
                25,
                faker.internet().emailAddress()
        );

        underTest.createCustomer(customer);

        int id = underTest.getCustomers()
                .stream()
                .filter(c -> c.getEmail().equals(customer.getEmail()))
                .map(Customer::getId)
                .findFirst()
                .orElseThrow();

        var actual = underTest.existCustomerWithId(id);
        assertThat(actual).isTrue();
    }

    @Test
    void updateCustomer() {
        Customer customer = new Customer(
                faker.name().fullName(),
                25,
                faker.internet().emailAddress()
        );

        underTest.createCustomer(customer);

        int id = underTest.getCustomers()
                .stream()
                .filter(c -> c.getEmail().equals(customer.getEmail()))
                .map(Customer::getId)
                .findFirst()
                .orElseThrow();


        var newName = "name updated";

        Customer updated = new Customer();
        updated.setId(id);
        updated.setName(newName);

        underTest.updateCustomer(updated);

        var actual = underTest.getCustomer(id);

        assertThat(actual).isPresent().hasValueSatisfying(c -> {
            assertThat(c.getId()).isEqualTo(id);
            assertThat(c.getName()).isEqualTo(newName);
            assertThat(c.getAge()).isEqualTo(customer.getAge());
            assertThat(c.getEmail()).isEqualTo(customer.getEmail());
        });


    }

    @Test
    void existCustomerWithEmail() {
        String email = faker.internet().emailAddress();
        Customer customer = new Customer(
                faker.name().fullName(),
                25,
                email
        );
        underTest.createCustomer(customer);
        boolean actual = underTest.existCustomerWithEmail(email);
        assertThat(actual).isTrue();
    }

    @Test
    void existCustomerWithId() {
        Customer customer = new Customer(
                faker.name().fullName(),
                25,
                faker.internet().emailAddress()
        );

        underTest.createCustomer(customer);

        int id = underTest.getCustomers()
                .stream()
                .filter(c -> c.getEmail().equals(customer.getEmail()))
                .map(Customer::getId)
                .findFirst()
                .orElseThrow();

        boolean actual = underTest.existCustomerWithId(id);

        assertThat(actual).isTrue();
    }
}