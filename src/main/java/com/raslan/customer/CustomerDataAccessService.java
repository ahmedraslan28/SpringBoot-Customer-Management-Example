package com.raslan.customer;

import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
public class CustomerDataAccessService implements CustomerDAO{

    private static final List<Customer> customers ;

    static {
        customers = new ArrayList<>() ;
        Customer ahmed = new Customer(1, "ahmed", "23", "ahmedraslan28@gmail.com");
        Customer rana = new Customer(2, "rana", "20", "ranaraslan28@gmail.com");
        customers.add(ahmed); 
        customers.add(rana);
    }
    @Override
    public List<Customer> getCustomers() {
        return customers ;
    }

    @Override
    public Optional<Customer> getCustomer(Integer customerId) {
        return customers.stream()
                .filter(customer -> customer.getId().equals(customerId)).findFirst();
    }
}
