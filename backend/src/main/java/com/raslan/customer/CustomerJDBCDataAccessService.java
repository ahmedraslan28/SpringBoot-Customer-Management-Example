package com.raslan.customer;

import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository("jdbc")
public class CustomerJDBCDataAccessService implements CustomerDAO {
    private final JdbcTemplate jdbcTemplate;


    public CustomerJDBCDataAccessService(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<Customer> getCustomers() {
        var sql = """
                    select * from customer
                """;
        return jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(Customer.class));
    }

    @Override
    public List<Customer> getCustomers(Integer offset, Integer limit) {
        var sql = """
            SELECT * FROM customer LIMIT ? OFFSET ?
          """;
        return jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(Customer.class), limit, offset);
    }

    @Override
    public Optional<Customer> getCustomer(Integer customerId) {
        var sql = """
                    select * from customer where id = ?
                """;
        return jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(Customer.class), customerId).stream().findFirst();
    }

    @Override
    public void createCustomer(Customer customer) {
        var sql = """
                insert into customer (name, email, password, age, gender) values (?,?,?,?,?)
                """;

        jdbcTemplate.update(sql, customer.getName(),
                customer.getEmail(), customer.getPassword(), customer.getAge(), customer.getGender().name());
    }

    @Override
    public Customer deleteCustomer(Integer id) {
        var sql = """
                    delete from customer where id = ?
                """;
        jdbcTemplate.update(sql, id);
        return null;
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
        String sql = """
                    select count(id) from customer where email = ?
                """;
        Integer found = jdbcTemplate.queryForObject(sql, Integer.class, email);
        return found != null && found > 0;
    }

    @Override
    public boolean existCustomerWithId(Integer id) {
        String sql = """
                    select count(id) from customer where id = ?
                """;
        Integer found = jdbcTemplate.queryForObject(sql, Integer.class, id);
        return found != null && found > 0;
    }

    @Override
    public Optional<Customer> getUserByEmail(String email) {
        var sql = """
                    select * from customer where email = ?
                """;
        return jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(Customer.class), email).stream().findFirst();
    }

    @Override
    public long countCustomers() {
        String sql = """
                    select count(id) from customer 
                """;
        Integer count = jdbcTemplate.queryForObject(sql, Integer.class);
        return count != null ? count : 0 ;
    }
}
