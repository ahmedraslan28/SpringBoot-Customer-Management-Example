package com.raslan.customer;

import com.raslan.exception.ResourceNotFound;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class CustomerService {
    private final CustomerDAO customerDAO ;

    CustomerService(CustomerDAO customerDAO){
        this.customerDAO = customerDAO ;
    }

    public List<Customer> getAllCustomers(){
        return customerDAO.getCustomers() ;
    }

    public Customer getCustomer(Integer id){
        return customerDAO.getCustomer(id)
                .orElseThrow(
                        ()-> new ResourceNotFound("no customer with given id")
                );
    }
}
