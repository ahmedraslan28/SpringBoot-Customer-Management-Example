package com.raslan.customer;

import java.util.List;
import java.util.Optional;

public interface CustomerDAO {
    public List<Customer> getCustomers() ;

    public Optional<Customer> getCustomer(Integer customerId) ;
}
