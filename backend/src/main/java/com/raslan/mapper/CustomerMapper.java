package com.raslan.mapper;

import com.raslan.customer.Customer;
import com.raslan.dto.CustomerDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring")
public interface CustomerMapper {

    @Mapping(target = "rules", expression = "java( mapAuthoritiesToListString(customer.getAuthorities()) )")
    CustomerDTO customerToCustomerDto(Customer customer);

//    @Mapping(target = "authorities", expression = "java( mapAuthoritiesToListString(customer.getAuthorities()) )")
//    Customer customerDtoToCustomer(CustomerDTO customerDTO);

    default List<CustomerDTO> listOfCustomersToDto(List<Customer> customers) {
        return customers.stream().map(this::customerToCustomerDto).collect(Collectors.toList());
    }


    default List<String> mapAuthoritiesToListString(Collection<? extends GrantedAuthority> authorities) {
        return authorities.stream().map(GrantedAuthority::getAuthority).collect(Collectors.toList());
    }

}
