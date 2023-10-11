package com.raslan.customer;

import java.util.List;
import java.util.Optional;

public interface CustomerDAO {
    List<Customer> getCustomers() ;

    Optional<Customer> getCustomer(Integer customerId) ;

    void createCustomer(Customer customer) ;

    Customer deleteCustomer(Integer id) ;

    void updateCustomer(Customer customer) ;

    boolean existCustomerWithEmail(String email) ;
    boolean existCustomerWithId(Integer id) ;
    Optional<Customer> getUserByEmail(String email) ;

}
