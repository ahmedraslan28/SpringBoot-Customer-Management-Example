package com.raslan.customer;

import com.raslan.dto.*;
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

    private final CustomerMapper customerMapper;

    public CustomerController(CustomerService customerService, JWTUtil jwtUtil, CustomerMapper customerMapper) {
        this.customerService = customerService;
        this.jwtUtil = jwtUtil;
        this.customerMapper = customerMapper;
    }

    @GetMapping
    public List<CustomerDTO> getCustomers() {
        List<Customer> customers = customerService.getAllCustomers();
        return customerMapper.listOfCustomersToDto(customers);
    }

    @GetMapping("/{id}")
    public CustomerDTO getCustomer(@PathVariable("id") Integer id) {
        Customer customer = customerService.getCustomer(id);
        return customerMapper.customerToCustomerDto(customer);
    }

    @PostMapping
    public ResponseEntity<?> CreateCustomer(@RequestBody CustomerRegistrationRequestDTO request) {
        Customer customer = customerService.createCustomer(request);
        String token = jwtUtil.issueToken(request.email(), "ROLE_USER");
        return ResponseEntity.ok().body(new CustomerTokenResponseDTO(
                        token,
                        customerMapper.customerToCustomerDto(customer)
                )
        );
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteCustomer(@PathVariable("id") Integer id) {
        Customer customer = customerService.deleteCustomer(id);
        return ResponseEntity.ok().body(new CustomerMessageResponseDTO(
                        "deleted successfully",
                        customerMapper.customerToCustomerDto(customer)
                )
        );
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateCustomer(@PathVariable("id") Integer id,
                                            @RequestBody CustomerUpdateRequestDTO customerToUpdate) {
        Customer customer = customerService.updateCustomer(id, customerToUpdate);
        return ResponseEntity.ok().body(new CustomerMessageResponseDTO(
                        "updated successfully",
                        customerMapper.customerToCustomerDto(customer)
                )
        );
    }


}
