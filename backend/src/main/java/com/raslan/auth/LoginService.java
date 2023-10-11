package com.raslan.auth;

import com.raslan.customer.Customer;
import com.raslan.dto.CustomerDTO;
import com.raslan.dto.CustomerTokenResponseDTO;
import com.raslan.dto.LoginRequestDTO;
import com.raslan.jwt.JWTUtil;
import com.raslan.mapper.CustomerMapper;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

@Service
public class LoginService {
    private final AuthenticationManager authenticationManager ;

    private final CustomerMapper customerMapper ;
    private final JWTUtil jwtUtil ;

    public LoginService(AuthenticationManager authenticationManager, CustomerMapper customerMapper, JWTUtil jwtUtil) {
        this.authenticationManager = authenticationManager;
        this.customerMapper = customerMapper;
        this.jwtUtil = jwtUtil;
    }

    public CustomerTokenResponseDTO login(LoginRequestDTO request){
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.username(),
                        request.password()
                )
        );

        Customer currentAuthenticatedCustomer = (Customer) authentication.getPrincipal();
        CustomerDTO customer = customerMapper.customerToCustomerDto(
                currentAuthenticatedCustomer
        );

        String token = jwtUtil.issueToken(customer.username(), customer.rules());
        return new CustomerTokenResponseDTO(token, customer) ;
    }
}
