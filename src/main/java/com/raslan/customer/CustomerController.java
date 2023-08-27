package com.raslan.customer;

import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/customers")
public class CustomerController {

    private final CustomerService customerService;

    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
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
    public Customer CreateCustomer(@RequestBody Customer customer) {
        return customerService.createCustomer(customer.getName(), customer.getAge(), customer.getEmail());
    }

    @DeleteMapping("/{id}")
    public void deleteCustomer(@PathVariable("id") Integer id) {
        customerService.deleteCustomer(id);
    }

    @PutMapping("/{id}")
    public Customer updateCustomer(@PathVariable("id") Integer id,
                                   @RequestBody Customer customerToUpdate) {

        return customerService.updateCustomer(id, customerToUpdate) ;
    }
}
