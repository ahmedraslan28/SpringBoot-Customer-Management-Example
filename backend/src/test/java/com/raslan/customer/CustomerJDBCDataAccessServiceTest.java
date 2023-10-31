package com.raslan.customer;

import com.raslan.TestContainersAbstract;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

class CustomerJDBCDataAccessServiceTest extends TestContainersAbstract {
    private static CustomerJDBCDataAccessService underTest;

    @BeforeAll
    static void setUP() {
        underTest = new CustomerJDBCDataAccessService(
                getJdbcTemplate()
        );
    }

    @Test
    void getCustomers() {
        Customer customer = new Customer(
                faker.name().fullName(),
                25,
                faker.internet().emailAddress(),
                "password"
                , Gender.FEMALE
        );

        underTest.createCustomer(customer);

        List<Customer> customers = underTest.getCustomers();

        assertThat(customers).isNotEmpty();
    }

    @Test
    void getCustomer() {
        Customer customer = new Customer(
                faker.name().fullName(),
                25,
                faker.internet().emailAddress(),
                "password", Gender.FEMALE
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
            assertThat(c.getGender()).isEqualTo(customer.getGender());
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
                faker.internet().emailAddress(),
                "password",
                Gender.FEMALE
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
                faker.internet().emailAddress(),
                "password", Gender.FEMALE
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
                faker.internet().emailAddress(),
                "password", Gender.FEMALE
        );

        underTest.createCustomer(customer);

        int id = underTest.getCustomers()
                .stream()
                .filter(c -> c.getEmail().equals(customer.getEmail()))
                .map(Customer::getId)
                .findFirst()
                .orElseThrow();


        var newName = "name updated";
        var newAge = 30;
        var newEmail = faker.internet().emailAddress();
        Customer updated = new Customer();
        updated.setId(id);
        updated.setName(newName);
        updated.setAge(newAge);
        updated.setEmail(newEmail);


        underTest.updateCustomer(updated);

        var actual = underTest.getCustomer(id);

        assertThat(actual).isPresent().hasValueSatisfying(c -> {
            assertThat(c.getId()).isEqualTo(id);
            assertThat(c.getName()).isEqualTo(newName);
            assertThat(c.getAge()).isEqualTo(newAge);
            assertThat(c.getEmail()).isEqualTo(newEmail);
            assertThat(c.getGender()).isEqualTo(customer.getGender());
        });


    }

    @Test
    void updateCustomerName() {
        Customer customer = new Customer(
                faker.name().fullName(),
                25,
                faker.internet().emailAddress(),
                "password", Gender.FEMALE
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
            assertThat(c.getGender()).isEqualTo(customer.getGender());
        });


    }

    @Test
    void updateCustomerEmail() {
        Customer customer = new Customer(
                faker.name().fullName(),
                25,
                faker.internet().emailAddress(),
                "password", Gender.FEMALE
        );

        underTest.createCustomer(customer);

        int id = underTest.getCustomers()
                .stream()
                .filter(c -> c.getEmail().equals(customer.getEmail()))
                .map(Customer::getId)
                .findFirst()
                .orElseThrow();


        var newEmail = faker.internet().emailAddress();

        Customer updated = new Customer();
        updated.setId(id);
        updated.setEmail(newEmail);

        underTest.updateCustomer(updated);

        var actual = underTest.getCustomer(id);

        assertThat(actual).isPresent().hasValueSatisfying(c -> {
            assertThat(c.getId()).isEqualTo(id);
            assertThat(c.getName()).isEqualTo(customer.getName());
            assertThat(c.getAge()).isEqualTo(customer.getAge());
            assertThat(c.getEmail()).isEqualTo(newEmail);
            assertThat(c.getGender()).isEqualTo(customer.getGender());
        });
    }

    @Test
    void WillNotUpdateWhenNoThingChanged() {
        Customer customer = new Customer(
                faker.name().fullName(),
                25,
                faker.internet().emailAddress(),
                "password",
                Gender.FEMALE
        );

        underTest.createCustomer(customer);

        int id = underTest
                .getCustomers()
                .stream()
                .filter(c -> c.getEmail().equals(customer.getEmail()))
                .map(Customer::getId)
                .findFirst()
                .orElseThrow();

        Customer updated = new Customer();
        updated.setId(id);
        underTest.updateCustomer(updated);

        Optional<Customer> actual = underTest.getCustomer(id);

        assertThat(actual).isPresent().hasValueSatisfying(c -> {
            assertThat(c.getId()).isEqualTo(id);
            assertThat(c.getAge()).isEqualTo(customer.getAge());
            assertThat(c.getName()).isEqualTo(customer.getName());
            assertThat(c.getEmail()).isEqualTo(customer.getEmail());
            assertThat(c.getGender()).isEqualTo(customer.getGender());
        });


    }

    @Test
    void updateCustomerAge() {
        Customer customer = new Customer(
                faker.name().fullName(),
                25,
                faker.internet().emailAddress(),
                "password", Gender.FEMALE
        );

        underTest.createCustomer(customer);

        int id = underTest.getCustomers()
                .stream()
                .filter(c -> c.getEmail().equals(customer.getEmail()))
                .map(Customer::getId)
                .findFirst()
                .orElseThrow();


        var newAge = 30;

        Customer updated = new Customer();
        updated.setId(id);
        updated.setAge(newAge);

        underTest.updateCustomer(updated);

        var actual = underTest.getCustomer(id);

        assertThat(actual).isPresent().hasValueSatisfying(c -> {
            assertThat(c.getId()).isEqualTo(id);
            assertThat(c.getName()).isEqualTo(customer.getName());
            assertThat(c.getAge()).isEqualTo(newAge);
            assertThat(c.getEmail()).isEqualTo(customer.getEmail());
            assertThat(c.getGender()).isEqualTo(customer.getGender());
        });
    }

    @Test
    void existsCustomerWithEmail() {
        String email = faker.internet().emailAddress();
        Customer customer = new Customer(
                faker.name().fullName(),
                25,
                email,
                "password", Gender.FEMALE
        );
        underTest.createCustomer(customer);
        boolean actual = underTest.existCustomerWithEmail(email);
        assertThat(actual).isTrue();
    }

    @Test
    void existsCustomerWithEmailReturnsFalseWhenDoesNotExists() {
        String email = faker.internet().emailAddress();

        boolean actual = underTest.existCustomerWithEmail(email);

        assertThat(actual).isFalse();
    }

    @Test
    void existsCustomerWithId() {
        Customer customer = new Customer(
                faker.name().fullName(),
                25,
                faker.internet().emailAddress(),
                "password", Gender.FEMALE
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

    @Test
    void existsCustomerWithIdWillReturnFalseWhenNotPresent() {
        int id = -1;
        var actual = underTest.existCustomerWithId(id);
        assertThat(actual).isFalse();
    }


}