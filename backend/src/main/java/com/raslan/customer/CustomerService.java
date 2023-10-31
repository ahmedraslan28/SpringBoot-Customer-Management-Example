package com.raslan.customer;

import com.raslan.dto.CustomerRegistrationRequestDTO;
import com.raslan.dto.CustomerUpdateRequestDTO;
import com.raslan.exception.DuplicatedRowException;
import com.raslan.exception.RequestValidationException;
import com.raslan.exception.ResourceNotFoundException;
import com.raslan.mapper.CustomerRequestsMapper;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CustomerService {
    private final CustomerDAO customerDAO;
    private final PasswordEncoder passwordEncoder;

    private final CustomerRequestsMapper customerRequestsMapper;

    CustomerService(@Qualifier("jdbc") CustomerDAO customerDAO,
                    PasswordEncoder passwordEncoder,
                    CustomerRequestsMapper customerRequestsMapper) {
        this.customerDAO = customerDAO;
        this.passwordEncoder = passwordEncoder;
        this.customerRequestsMapper = customerRequestsMapper;
    }

    public List<Customer> getAllCustomers() {
        return customerDAO.getCustomers();
    }

    public List<Customer> getAllCustomers(int offset, int limit) {
        return customerDAO.getCustomers(offset, limit);
    }


    public Customer getCustomer(Integer id) {
        return customerDAO.getCustomer(id)
                .orElseThrow(
                        () -> new ResourceNotFoundException("no customer with given id")
                );
    }

    public Customer createCustomer(CustomerRegistrationRequestDTO request) {
        if (customerDAO.existCustomerWithEmail(request.email())) {
            throw new DuplicatedRowException("the email already exists!!");
        }

        Customer customer = customerRequestsMapper.registrationRequestrequestToCustomer(passwordEncoder, request);

        customerDAO.createCustomer(customer);

        return customer;
    }

    public Customer deleteCustomer(Integer id) {
        if (!customerDAO.existCustomerWithId(id)) {
            throw new ResourceNotFoundException("no customer with given id");
        }
        return customerDAO.deleteCustomer(id);
    }

    public Customer updateCustomer(Integer id, CustomerUpdateRequestDTO customerToUpdate) {
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


    public Long countCustomers(){
        return this.customerDAO.countCustomers();
    }
}
