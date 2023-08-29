package com.raslan.customer;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository("jdbc")
public class CustomerJDBCDataAccessService implements CustomerDAO {

    private final CustomerRepository customerRepository;

    private final CustomerRowMapper customerRowMapper;

    private final JdbcTemplate jdbcTemplate;


    public CustomerJDBCDataAccessService(CustomerRepository customerRepository, CustomerRowMapper customerRowMapper, JdbcTemplate jdbcTemplate) {
        this.customerRepository = customerRepository;
        this.customerRowMapper = customerRowMapper;
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<Customer> getCustomers() {
        var sql = """
                    select * from customer
                """;
        return jdbcTemplate.query(sql, customerRowMapper);
    }

    @Override
    public Optional<Customer> getCustomer(Integer customerId) {
        return customerRepository.findById(customerId);
    }

    @Override
    public void createCustomer(Customer customer) {
        var sql = """
                insert into customer (name, email, age) values (?,?,?)
                """;

        jdbcTemplate.update(sql, customer.getName(),
                customer.getEmail(), customer.getAge());

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
        return customerRepository.existsByEmail(email);
    }

    @Override
    public boolean existCustomerWithId(Integer id) {
        return customerRepository.existsById(id);
    }


}
