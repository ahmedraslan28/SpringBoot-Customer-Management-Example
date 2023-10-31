package com.raslan.customer;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.mockito.Mockito.verify;

class CustomerJPADataAccessServiceTest {

    private CustomerJPADataAccessService underTest;
    @Mock
    private CustomerRepository customerRepository;
    private AutoCloseable autoCloseable ;

    @BeforeEach
    void setUp() {
        autoCloseable = MockitoAnnotations.openMocks(this);
        underTest = new CustomerJPADataAccessService(customerRepository);
    }

    @AfterEach
    void tearDown() throws Exception {
        autoCloseable.close();
    }

    @Test
    void getCustomers() {
        underTest.getCustomers();
        verify(customerRepository).findAll();
    }

    @Test
    void getCustomer() {
        int id = 1;
        underTest.getCustomer(id);
        verify(customerRepository).findById(id);
    }

    @Test
    void createCustomer() {
        Customer customer = new Customer(
                "ahmed raslan",
                18,
                "ahmedraslan28@gmail.com",
                "password", Gender.FEMALE
        );
        underTest.createCustomer(customer);
        verify(customerRepository).save(customer);
    }

    @Test
    void deleteCustomer() {
        int id = 1;
        underTest.deleteCustomer(id);
        verify(customerRepository).deleteById(id);
    }

    @Test
    void updateCustomer() {
        Customer customer = new Customer(
                "ahmed raslan",
                18,
                "ahmedraslan28@gmail.com",
                "password", Gender.FEMALE
        );
        underTest.updateCustomer(customer);
        verify(customerRepository).save(customer);
    }

    @Test
    void existCustomerWithEmail() {
        String email = "ahmed@test.com";
        underTest.existCustomerWithEmail(email);
        verify(customerRepository).existsByEmail(email);
    }

    @Test
    void existCustomerWithId() {
        int id = 1;
        underTest.existCustomerWithId(id);
        verify(customerRepository).existsById(id);
    }
}