package com.raslan.customer;

import com.raslan.dto.CustomerDTO;
import com.raslan.dto.CustomerRegistrationRequestDTO;
import com.raslan.dto.CustomerUpdateRequestDTO;
import com.raslan.dto.TokenDTO;
import com.raslan.jwt.JWTUtil;
import com.raslan.mapper.CustomerMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/customers")
public class CustomerController {

    private final CustomerService customerService;
    private final JWTUtil jwtUtil;

    private final CustomerMapper customerMapper ;

    public CustomerController(CustomerService customerService, JWTUtil jwtUtil, CustomerMapper customerMapper) {
        this.customerService = customerService;
        this.jwtUtil = jwtUtil;
        this.customerMapper = customerMapper;
    }

    @GetMapping
    public List<CustomerDTO> getCustomers() {
        List<Customer> customers = customerService.getAllCustomers();
        return customerMapper.listOfCustomersToDto(customers) ;
    }

    @GetMapping("/{id}")
    public CustomerDTO getCustomer(@PathVariable("id") Integer id) {
        Customer customer = customerService.getCustomer(id) ;
        return customerMapper.customerToCustomerDto(customer);
    }

    @PostMapping
    public ResponseEntity<?> CreateCustomer(@RequestBody CustomerRegistrationRequestDTO customer) {
        customerService.createCustomer(customer);
        String token = jwtUtil.issueToken(customer.email(), "ROLE_USER");
        return ResponseEntity.ok(new TokenDTO(token));
    }

    @DeleteMapping("/{id}")
    public void deleteCustomer(@PathVariable("id") Integer id) {
        customerService.deleteCustomer(id);
    }

    @PutMapping("/{id}")
    public CustomerDTO updateCustomer(@PathVariable("id") Integer id,
                                   @RequestBody CustomerUpdateRequestDTO customerToUpdate) {
        return customerMapper.customerToCustomerDto(
                customerService.updateCustomer(id, customerToUpdate)
        );
    }


}
