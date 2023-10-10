package com.raslan.mapper;

import com.raslan.customer.Customer;
import com.raslan.dto.CustomerRegistrationRequestDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.security.crypto.password.PasswordEncoder;

@Mapper(componentModel = "spring")
public interface CustomerRequestsMapper {

    @Mapping(target = "password", expression = "java( passwordEncoder.encode(request.password()) )")
    Customer registrationRequestrequestToCustomer(PasswordEncoder passwordEncoder,
                                                  CustomerRegistrationRequestDTO request) ;
}
