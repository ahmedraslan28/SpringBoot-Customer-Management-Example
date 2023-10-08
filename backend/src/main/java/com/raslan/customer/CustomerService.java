package com.raslan.customer;

import com.raslan.exception.DuplicatedRowException;
import com.raslan.exception.RequestValidationException;
import com.raslan.exception.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CustomerService {
    private final CustomerDAO customerDAO;

    CustomerService(@Qualifier("jpa") CustomerDAO customerDAO) {
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

    public void createCustomer(CustomerRegistrationRequest request) {
        if (customerDAO.existCustomerWithEmail(request.email())) {
            throw new DuplicatedRowException("the email already exists!!");
        }

        Customer customer = new Customer(
                request.name(),
                request.age(),
                request.email(),
                request.password(),
                request.gender()
        );

        customerDAO.createCustomer(customer);
    }

    public void deleteCustomer(Integer id) {
        if (!customerDAO.existCustomerWithId(id)) {
            throw new ResourceNotFoundException("no customer with given id !!");
        }
        customerDAO.deleteCustomer(id);
    }

    public Customer updateCustomer(Integer id, CustomerUpdateRequest customerToUpdate) {
        Customer customer = getCustomer(id);

        boolean isChanged = false;
        if (customerToUpdate.name() != null && !customer.getName().equals(customerToUpdate.name())) {
            isChanged = true;
            customer.setName(customerToUpdate.name());
        }
        if (customerToUpdate.email() != null && !customer.getEmail().equals(customerToUpdate.email())) {
            if (customerDAO.existCustomerWithEmail(customerToUpdate.email())) {
                throw new DuplicatedRowException("the email already exists!!");
            }
            isChanged = true;
            customer.setEmail(customerToUpdate.email());
        }
        if (customerToUpdate.age() != null && !customer.getAge().equals(customerToUpdate.age())) {
            isChanged = true;
            customer.setAge(customerToUpdate.age());
        }
        if (!isChanged) {
            throw new RequestValidationException("No changes found");
        }
        customerDAO.updateCustomer(customer);
        return customer;
    }
}
