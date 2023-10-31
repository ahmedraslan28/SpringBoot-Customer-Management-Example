package com.raslan.journey;


import com.github.javafaker.Faker;
import com.raslan.customer.Gender;
import com.raslan.dto.*;
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
import static org.springframework.http.HttpHeaders.AUTHORIZATION;

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
        Gender gender = age % 2 == 0 ? Gender.MALE : Gender.FEMALE;

        CustomerRegistrationRequestDTO request = new CustomerRegistrationRequestDTO(
                name,
                email,
                "89@jkbfk#jkdkjl%886",
                age,
                gender
        );

        // post a new customer and get token
        String jwtToken = client.post()
                .uri(CUSTOMER_URL)
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .body(Mono.just(request), CustomerRegistrationRequestDTO.class)
                .exchange()
                .expectStatus()
                .isOk()
                .expectBody(CustomerTokenResponseDTO.class)
                .returnResult()
                .getResponseBody()
                .token();

        // make sure that the posted customer is present
        List<CustomerDTO> allCustomers = client.get()
                .uri(CUSTOMER_URL)
                .accept(MediaType.APPLICATION_JSON)
                .header(AUTHORIZATION, String.format("Bearer %s", jwtToken))
                .exchange()
                .expectStatus()
                .isOk()
                .expectBodyList(new ParameterizedTypeReference<CustomerDTO>() {
                })
                .returnResult()
                .getResponseBody();

        int id = allCustomers
                .stream()
                .filter(c -> c.email().equals(email))
                .map(CustomerDTO::id)
                .findFirst()
                .orElseThrow();

        CustomerDTO expectedCustomer = new CustomerDTO(
                id, name, email, gender, age, List.of("ROLE_USER"), email);

        assertThat(allCustomers).contains(expectedCustomer);
    }

    @Test
    void canDeleteACustomer() {
        String name = faker.name().fullName();
        String email = name + "@reso.com";
        int age = random.nextInt(18, 90);
        Gender gender = age % 2 == 0 ? Gender.MALE : Gender.FEMALE;

        CustomerRegistrationRequestDTO request = new CustomerRegistrationRequestDTO(
                name,
                email,
                "password",
                age,
                gender
        );
        CustomerRegistrationRequestDTO request2 = new CustomerRegistrationRequestDTO(
                name,
                email + ".eg",
                "password",
                age,
                gender
        );

        // create customer1
        client.post()
                .uri(CUSTOMER_URL)
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .body(Mono.just(request), CustomerRegistrationRequestDTO.class)
                .exchange()
                .expectStatus()
                .isOk();

        // create customer2
        String jwtToken = client.post()
                .uri(CUSTOMER_URL)
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .body(Mono.just(request2), CustomerRegistrationRequestDTO.class)
                .exchange()
                .expectStatus()
                .isOk()
                .expectBody(CustomerTokenResponseDTO.class)
                .returnResult()
                .getResponseBody()
                .token();

        // make sure that the posted customer is present and get his id
        List<CustomerDTO> allCustomers = client.get()
                .uri(CUSTOMER_URL)
                .accept(MediaType.APPLICATION_JSON)
                .header(AUTHORIZATION, String.format("Bearer %s", jwtToken))
                .exchange()
                .expectStatus()
                .isOk()
                .expectBodyList(new ParameterizedTypeReference<CustomerDTO>() {
                })
                .returnResult()
                .getResponseBody();

        int id = allCustomers.stream()
                .filter(c -> c.email().equals(email))
                .map(CustomerDTO::id)
                .findFirst()
                .orElseThrow();

        // delete the customer
        client.delete()
                .uri(CUSTOMER_URL + "/{id}", id)
                .accept(MediaType.APPLICATION_JSON)
                .header(AUTHORIZATION, String.format("Bearer %s", jwtToken))
                .exchange()
                .expectStatus()
                .isOk();

        // using the token of the second customer to check if the first customer is successfully deleted
        client.get()
                .uri(CUSTOMER_URL + "/{id}", id)
                .accept(MediaType.APPLICATION_JSON)
                .header(AUTHORIZATION, String.format("Bearer %s", jwtToken))
                .exchange()
                .expectStatus()
                .isNotFound();
    }

    @Test
    void canUpdateACustomer() {
        String name = faker.name().fullName();
        String email = name + "@reso.com";
        int age = random.nextInt(18, 90);
        Gender gender = age % 2 == 0 ? Gender.MALE : Gender.FEMALE;
        CustomerRegistrationRequestDTO request = new CustomerRegistrationRequestDTO(
                name, email, "password", age, gender);

        // post a new customer
        String jwtToken = client.post()
                .uri(CUSTOMER_URL)
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .body(Mono.just(request), CustomerRegistrationRequestDTO.class)
                .exchange()
                .expectStatus()
                .isOk()
                .expectBody(CustomerTokenResponseDTO.class)
                .returnResult()
                .getResponseBody()
                .token();

        // make sure that the posted customer is present and get his id
        List<CustomerDTO> allCustomers = client.get()
                .uri(CUSTOMER_URL)
                .accept(MediaType.APPLICATION_JSON)
                .header(AUTHORIZATION, String.format("Bearer %s", jwtToken))
                .exchange()
                .expectStatus()
                .isOk()
                .expectBodyList(new ParameterizedTypeReference<CustomerDTO>() {
                })
                .returnResult()
                .getResponseBody();

        int id = allCustomers.stream()
                .filter(c -> c.email().equals(email))
                .map(CustomerDTO::id)
                .findFirst()
                .orElseThrow();

        // update the customer
        String newName = "new name";
        CustomerUpdateRequestDTO updated = new CustomerUpdateRequestDTO(newName, null, null);

        client.put()
                .uri(CUSTOMER_URL + "/{id}", id)
                .accept(MediaType.APPLICATION_JSON)
                .header(AUTHORIZATION, String.format("Bearer %s", jwtToken))
                .contentType(MediaType.APPLICATION_JSON)
                .body(Mono.just(updated), CustomerUpdateRequestDTO.class)
                .exchange()
                .expectStatus()
                .isOk();


        // get the updated customer
        CustomerDTO updatedCustomer = client.get()
                .uri(CUSTOMER_URL + "/{id}", id)
                .accept(MediaType.APPLICATION_JSON)
                .header(AUTHORIZATION, String.format("Bearer %s", jwtToken))
                .exchange()
                .expectStatus()
                .isOk()
                .expectBody(CustomerDTO.class)
                .returnResult()
                .getResponseBody();


        CustomerDTO expectedCustomer = new CustomerDTO(
                id, newName, email, gender, age, List.of("ROLE_USER"), email);

        assertThat(expectedCustomer).isEqualTo(updatedCustomer);
    }
}
