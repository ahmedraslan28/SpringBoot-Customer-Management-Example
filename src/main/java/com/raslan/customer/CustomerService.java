package com.raslan.customer;

import com.raslan.exception.DuplicatedRowException;
import com.raslan.exception.RequestValidationException;
import com.raslan.exception.ResourceNotFoundException;
import org.springframework.stereotype.Indexed;
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
        if (customerDAO.existCustomerWithEmail(email)) {
            throw new DuplicatedRowException("the email already exists!!");
        }
        customerDAO.createCustomer(customer);

        return customer;
    }

    public void deleteCustomer(Integer id) {
        if (!customerDAO.existCustomerWithId(id)) {
            throw new ResourceNotFoundException("no customer with given id !!");
        }
        customerDAO.deleteCustomer(id);
    }

    public Customer updateCustomer(Integer id, Customer customerToUpdate) {
        Customer customer = getCustomer(id);

        boolean isChanged = false;
        if (customer.getName() != null && !customer.getName().equals(customerToUpdate.getName())) {
            isChanged = true;
            customer.setName(customerToUpdate.getName());
        }
        if (customer.getEmail() != null && !customer.getEmail().equals(customerToUpdate.getEmail())) {
            if (customerDAO.existCustomerWithEmail(customerToUpdate.getEmail())) {
                throw new DuplicatedRowException("the email already exists!!");
            }
            isChanged = true;
            customer.setEmail(customerToUpdate.getEmail());
        }
        if (customer.getAge() != null && !customer.getAge().equals(customerToUpdate.getAge())) {
            isChanged = true;
            customer.setAge(customerToUpdate.getAge());
        }
        if (!isChanged) {
            throw new RequestValidationException("No changes found");
        }
        customerDAO.updateCustomer(customer);
        return customer;
    }
}
