package com.raslan.journey;


import com.github.javafaker.Faker;
import com.raslan.customer.Customer;
import com.raslan.customer.CustomerRegistrationRequest;
import com.raslan.customer.CustomerUpdateRequest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.Random;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;

@SpringBootTest(webEnvironment = RANDOM_PORT)
public class CustomerIntegrationTest {

    @Autowired
    private WebTestClient client;

    private static final Faker faker = new Faker();

    private static final Random random = new Random();

    private static final String CUSTOMER_URL = "/api/v1/customers";

    @Test
    void canRegisterACustomer() {
        String name = faker.name().fullName();
        String email = name + "@reso.com";
        int age = random.nextInt(18, 90);

        CustomerRegistrationRequest request = new CustomerRegistrationRequest(name, email, age);

        // post a new customer
        client.post()
                .uri(CUSTOMER_URL)
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .body(Mono.just(request), CustomerRegistrationRequest.class)
                .exchange()
                .expectStatus()
                .isOk();

        // make sure that the posted customer is present
        List<Customer> allCustomers = client.get()
                .uri(CUSTOMER_URL)
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus()
                .isOk()
                .expectBodyList(new ParameterizedTypeReference<Customer>() {
                })
                .returnResult()
                .getResponseBody();

        int id = allCustomers.stream()
                .filter(c -> c.getEmail().equals(email))
                .map(Customer::getId)
                .findFirst()
                .orElseThrow();
        Customer expectedCustomer = new Customer(id, name, age, email);
        assertThat(allCustomers).contains(expectedCustomer);
    }

    @Test
    void canDeleteACustomer() {
        String name = faker.name().fullName();
        String email = name + "@reso.com";
        int age = random.nextInt(18, 90);

        CustomerRegistrationRequest request = new CustomerRegistrationRequest(name, email, age);

        // post a new customer
        client.post()
                .uri(CUSTOMER_URL)
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .body(Mono.just(request), CustomerRegistrationRequest.class)
                .exchange()
                .expectStatus()
                .isOk();

        // make sure that the posted customer is present and get his id
        List<Customer> allCustomers = client.get()
                .uri(CUSTOMER_URL)
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus()
                .isOk()
                .expectBodyList(new ParameterizedTypeReference<Customer>() {
                })
                .returnResult()
                .getResponseBody();

        int id = allCustomers.stream()
                .filter(c -> c.getEmail().equals(email))
                .map(Customer::getId)
                .findFirst()
                .orElseThrow();

        // delete the customer
        client.delete()
                .uri(CUSTOMER_URL + "/{id}", id)
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus()
                .isOk();

        // check if the customer is deleted

        client.get()
                .uri(CUSTOMER_URL + "/{id}", id)
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus()
                .isNotFound();
    }

    @Test
    void canUpdateACustomer() {
        String name = faker.name().fullName();
        String email = name + "@reso.com";
        int age = random.nextInt(18, 90);

        CustomerRegistrationRequest request = new CustomerRegistrationRequest(name, email, age);

        // post a new customer
        client.post()
                .uri(CUSTOMER_URL)
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .body(Mono.just(request), CustomerRegistrationRequest.class)
                .exchange()
                .expectStatus()
                .isOk();

        // make sure that the posted customer is present and get his id
        List<Customer> allCustomers = client.get()
                .uri(CUSTOMER_URL)
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus()
                .isOk()
                .expectBodyList(new ParameterizedTypeReference<Customer>() {
                })
                .returnResult()
                .getResponseBody();

        int id = allCustomers.stream()
                .filter(c -> c.getEmail().equals(email))
                .map(Customer::getId)
                .findFirst()
                .orElseThrow();

        // update the customer
        String newName = "new name" ;
        CustomerUpdateRequest updated = new CustomerUpdateRequest(newName, null, null);

        client.put()
                .uri(CUSTOMER_URL + "/{id}", id)
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .body(Mono.just(updated), CustomerUpdateRequest.class)
                .exchange()
                .expectStatus()
                .isOk();


        // get the updated customer
        Customer updatedCustomer = client.get()
                .uri(CUSTOMER_URL + "/{id}", id)
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus()
                .isOk()
                .expectBody(Customer.class)
                .returnResult()
                .getResponseBody();

        Customer expectedCustomer = new Customer(id, newName, age, email);

        assertThat(expectedCustomer).isEqualTo(updatedCustomer) ;
    }
}
