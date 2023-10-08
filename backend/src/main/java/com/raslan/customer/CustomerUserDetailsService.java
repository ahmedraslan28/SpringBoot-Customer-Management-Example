package com.raslan.customer;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

public class CustomerUserDetailsService implements UserDetailsService {
    private final CustomerDAO customerDAO;

    public CustomerUserDetailsService(@Qualifier("jpa") CustomerDAO customerDAO) {
        this.customerDAO = customerDAO;
    }


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return customerDAO.getUserByEmail(username).orElseThrow(() ->
                new UsernameNotFoundException("Username " + username + " not found")
        );

    }
}
