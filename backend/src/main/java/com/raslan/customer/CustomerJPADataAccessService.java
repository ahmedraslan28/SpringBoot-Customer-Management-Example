package com.raslan.customer;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
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
    public List<Customer> getCustomers(Integer offset, Integer limit) {
        Pageable pageable = PageRequest.of(offset, limit);
        Page<Customer> customerPage = customerRepository.findAll(pageable);
        System.out.println("this customer page  " + customerPage);
        return customerPage.getContent();
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
    public Customer deleteCustomer(Integer id) {
        customerRepository.deleteById(id);
        return null;
    }

    @Override
    public void updateCustomer(Customer customer) {
        customerRepository.save(customer);
    }

    @Override
    public boolean existCustomerWithEmail(String email) {
        return customerRepository.existsByEmail(email);
    }

    @Override
    public boolean existCustomerWithId(Integer id) {
        return customerRepository.existsById(id);
    }

    @Override
    public Optional<Customer> getUserByEmail(String email) {
        return customerRepository.findByEmail(email);
    }

    @Override
    public long countCustomers() {
        return this.customerRepository.count();
    }

}
