package com.raslan.customer;

import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository("jpa")
public class CustomerJPADataAccessService implements CustomerDAO {

    private final CustomerRepository customerRepository;


    public CustomerJPADataAccessService(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    @Override
    public List<Customer> getCustomers() {
        return customerRepository.findAll();
    }

    @Override
    public Optional<Customer> getCustomer(Integer customerId) {
        return customerRepository.findById(customerId);
    }

    @Override
    public void createCustomer(Customer customer) {
        customerRepository.save(customer);
    }

    @Override
    public void deleteCustomer(Integer id) {
        customerRepository.deleteById(id);
    }

    @Override
    public void updateCustomer(Customer customer) {
        customerRepository.save(customer);
    }

    @Override
    public boolean existCustomerWithEmail(String email) {
        return customerRepository.existsByEmail(email) ;
    }

    @Override
    public boolean existCustomerWithId(Integer id) {
        return customerRepository.existsById(id) ;
    }


}
