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
        var sql = """
                    select * from customer where id = ?
                """;
        return jdbcTemplate.query(sql, customerRowMapper, customerId).stream().findFirst();
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
        var sql = """
                    delete from customer where id = ?
                """;
        jdbcTemplate.update(sql, id) ;
    }

    @Override
    public void updateCustomer(Customer customer) {
        if (customer.getName() != null) {
            String sql = "UPDATE customer SET name = ? WHERE id = ?";
            int result = jdbcTemplate.update(
                    sql,
                    customer.getName(),
                    customer.getId()
            );
            System.out.println("update customer name result = " + result);
        }
        if (customer.getAge() != null) {
            String sql = "UPDATE customer SET age = ? WHERE id = ?";
            int result = jdbcTemplate.update(
                    sql,
                    customer.getAge(),
                    customer.getId()
            );
            System.out.println("update customer age result = " + result);
        }
        if (customer.getEmail() != null) {
            String sql = "UPDATE customer SET email = ? WHERE id = ?";
            int result = jdbcTemplate.update(
                    sql,
                    customer.getEmail(),
                    customer.getId());
            System.out.println("update customer email result = " + result);
        }

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
