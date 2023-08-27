package com.raslan.customer;

import com.raslan.exception.DuplicatedRowException;
import com.raslan.exception.ResourceNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CustomerService {
    private final CustomerDAO customerDAO;

    CustomerService(CustomerDAO customerDAO) {
        this.customerDAO = customerDAO;
    }

    public List<Customer> getAllCustomers() {
        return customerDAO.getCustomers();
    }

    public Customer getCustomer(Integer id) {
        return customerDAO.getCustomer(id)
                .orElseThrow(
                        () -> new ResourceNotFoundException("no customer with given id")
                );
    }

    public Customer createCustomer(String name, String age, String email) {
        Customer customer = new Customer(name, age, email);
        if(customerDAO.existCustomerWithEmail(email)){
            throw new DuplicatedRowException("the email already exists!!") ;
        }
        customerDAO.createCustomer(customer);

        return customer;
    }
}
