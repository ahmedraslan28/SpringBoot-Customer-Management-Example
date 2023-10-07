package com.raslan.customer;

import com.raslan.jwt.JWTUtil;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/customers")
public class CustomerController {

    private final CustomerService customerService;
    private final JWTUtil jwtUtil;

    public CustomerController(CustomerService customerService, JWTUtil jwtUtil) {
        this.customerService = customerService;
        this.jwtUtil = jwtUtil;
    }

    @GetMapping
    public List<Customer> getCustomers() {
        return customerService.getAllCustomers();
    }

    @GetMapping("/{id}")
    public Customer getCustomer(@PathVariable("id") Integer id) {
        return customerService.getCustomer(id);
    }

    @PostMapping
    public ResponseEntity<?> CreateCustomer(@RequestBody CustomerRegistrationRequest customer) {
        customerService.createCustomer(customer);
        String token = jwtUtil.issueToken(customer.email(), "ROLE_USER");
        String body = """
                {"token" : "%s"}
                """ ;
        return ResponseEntity.ok(String.format(body, token));
    }

    @DeleteMapping("/{id}")
    public void deleteCustomer(@PathVariable("id") Integer id) {
        customerService.deleteCustomer(id);
    }

    @PutMapping("/{id}")
    public Customer updateCustomer(@PathVariable("id") Integer id,
                                   @RequestBody CustomerUpdateRequest customerToUpdate) {

        return customerService.updateCustomer(id, customerToUpdate) ;
    }


}
