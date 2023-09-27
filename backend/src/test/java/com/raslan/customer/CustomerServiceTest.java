package com.raslan.customer;

import com.raslan.exception.DuplicatedRowException;
import com.raslan.exception.RequestValidationException;
import com.raslan.exception.ResourceNotFoundException;
import org.apache.catalina.util.CustomObjectInputStream;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.*;

// this replace (autoCloseable = MockitoAnnotations.openMocks(this)) and (autoCloseable.close();)
@ExtendWith(MockitoExtension.class)
class CustomerServiceTest {

    private CustomerService underTest;

    @Mock
    private CustomerDAO customerDAO;

    @BeforeEach
    void setUp() {
        underTest = new CustomerService(customerDAO);
    }


    @Test
    void getAllCustomers() {
        underTest.getAllCustomers();
        verify(customerDAO).getCustomers();
    }

    @Test
    void getCustomer() {
        int id = 1;
        Customer customer = new Customer(
                id,
                "Ahmed Raslan",
                22,
                "ahmedraslan28@gmail.com",
                Gender.MALE
        );
        when(customerDAO.getCustomer(id)).thenReturn(Optional.of(customer));

        Customer actual = underTest.getCustomer(id);

        assertThat(actual).isEqualTo(customer);
    }

    @Test
    void throwExceptionWhenGetCustomerIsEmpty() {
        int id = -1;
        when(customerDAO.getCustomer(id)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> underTest.getCustomer(id))
                .isInstanceOf(ResourceNotFoundException.class)
                .hasMessage("no customer with given id");
    }

    @Test
    void createCustomer() {
        CustomerRegistrationRequest request = new CustomerRegistrationRequest("ahmed raslan",
                "test@test.com",
                22,
                Gender.MALE);
        when(customerDAO.existCustomerWithEmail(request.email())).thenReturn(false);

        underTest.createCustomer(request);

        ArgumentCaptor<Customer> argumentCaptor = ArgumentCaptor.forClass(Customer.class);

        verify(customerDAO).createCustomer(argumentCaptor.capture());

        Customer customer = argumentCaptor.getValue();

        assertThat(customer.getId()).isNull();
        assertThat(customer.getName()).isEqualTo(request.name());
        assertThat(customer.getAge()).isEqualTo(request.age());
        assertThat(customer.getEmail()).isEqualTo(request.email());
        assertThat(customer.getGender()).isEqualTo(request.gender());
    }

    @Test
    void throwExceptionWhenCreateCustomerWithDuplicateEmail() {
        CustomerRegistrationRequest request = new CustomerRegistrationRequest("ahmed raslan",
                "test@test.com",
                22, Gender.MALE);
        when(customerDAO.existCustomerWithEmail(request.email())).thenReturn(true);
        assertThatThrownBy(() -> underTest.createCustomer(request))
                .isInstanceOf(DuplicatedRowException.class)
                .hasMessage("the email already exists!!");

        verify(customerDAO, never()).createCustomer(any());
    }

    @Test
    void deleteCustomer() {
        int id = 1;
        when(customerDAO.existCustomerWithId(id)).thenReturn(true);
        underTest.deleteCustomer(id);
        verify(customerDAO).deleteCustomer(id);
    }

    @Test
    void throwExceptionWhenDeleteCustomerWithInvalidId() {
        int id = 1;
        when(customerDAO.existCustomerWithId(id)).thenReturn(false);

        assertThatThrownBy(() -> underTest
                .deleteCustomer(id))
                .isInstanceOf(ResourceNotFoundException.class)
                .hasMessage("no customer with given id !!");

        verify(customerDAO, never()).deleteCustomer(id);

    }

    @Test
    void updateAllCustomerProperties() {
        //given
        int id = 1;
        Customer customer = new Customer(id, "ahmed raslan", 22, "ahmedraslan28@gmail.com", Gender.MALE);

        when(customerDAO.getCustomer(id)).thenReturn(Optional.of(customer));

        CustomerUpdateRequest updated = new CustomerUpdateRequest(
                "ahmed raslan updated",
                "youssef@gmail.com",
                23
        );

        when(customerDAO.existCustomerWithEmail(updated.email())).thenReturn(false);

        //when
        underTest.updateCustomer(id, updated);

        //then
        ArgumentCaptor<Customer> customerArgumentCaptor = ArgumentCaptor.forClass(Customer.class);

        verify(customerDAO).updateCustomer(customerArgumentCaptor.capture());

        Customer capturedCustomer = customerArgumentCaptor.getValue();

        assertThat(capturedCustomer.getId()).isEqualTo(customer.getId());
        assertThat(capturedCustomer.getName()).isEqualTo(updated.name());
        assertThat(capturedCustomer.getEmail()).isEqualTo(updated.email());
        assertThat(capturedCustomer.getAge()).isEqualTo(updated.age());
        assertThat(capturedCustomer.getGender()).isEqualTo(customer.getGender());
    }


    @Test
    void canUpdateOnlyCustomerName() {
        //given
        int id = 1;
        Customer customer = new Customer(id, "ahmed", 22, "test@gmail.com", Gender.MALE);

        when(customerDAO.getCustomer(id)).thenReturn(Optional.of(customer));

        CustomerUpdateRequest updated = new CustomerUpdateRequest("new name", null, null);

        //when
        underTest.updateCustomer(id, updated);

        //then
        ArgumentCaptor<Customer> customerArgumentCaptor = ArgumentCaptor.forClass(Customer.class);

        verify(customerDAO).updateCustomer(customerArgumentCaptor.capture());

        Customer capturedCustomer = customerArgumentCaptor.getValue();

        assertThat(capturedCustomer.getId()).isEqualTo(customer.getId());
        assertThat(capturedCustomer.getName()).isEqualTo(updated.name());
        assertThat(capturedCustomer.getEmail()).isEqualTo(customer.getEmail());
        assertThat(capturedCustomer.getAge()).isEqualTo(customer.getAge());
        assertThat(capturedCustomer.getGender()).isEqualTo(customer.getGender());


    }

    @Test
    void canUpdateOnlyCustomerAge() {
        //given
        int id = 1;
        Customer customer = new Customer(id, "ahmed", 22, "test@gmail.com", Gender.MALE);

        when(customerDAO.getCustomer(id)).thenReturn(Optional.of(customer));

        CustomerUpdateRequest updated = new CustomerUpdateRequest(null, null, 13);

        //when
        underTest.updateCustomer(id, updated);

        //then
        ArgumentCaptor<Customer> customerArgumentCaptor = ArgumentCaptor.forClass(Customer.class);

        verify(customerDAO).updateCustomer(customerArgumentCaptor.capture());

        Customer capturedCustomer = customerArgumentCaptor.getValue();

        assertThat(capturedCustomer.getId()).isEqualTo(customer.getId());
        assertThat(capturedCustomer.getName()).isEqualTo(customer.getName());
        assertThat(capturedCustomer.getEmail()).isEqualTo(customer.getEmail());
        assertThat(capturedCustomer.getAge()).isEqualTo(updated.age());
        assertThat(capturedCustomer.getGender()).isEqualTo(customer.getGender());

    }

    @Test
    void canUpdateOnlyCustomerEmail() {
        //given
        int id = 1;
        Customer customer = new Customer(id, "ahmed", 22, "test@gmail.com", Gender.MALE);

        when(customerDAO.getCustomer(id)).thenReturn(Optional.of(customer));

        String newEmail = "newEmail@test.com";

        CustomerUpdateRequest updated = new CustomerUpdateRequest(null, newEmail, null);

        when(customerDAO.existCustomerWithEmail(newEmail)).thenReturn(false);

        //when
        underTest.updateCustomer(id, updated);

        //then
        ArgumentCaptor<Customer> customerArgumentCaptor = ArgumentCaptor.forClass(Customer.class);

        verify(customerDAO).updateCustomer(customerArgumentCaptor.capture());
        Customer capturedCustomer = customerArgumentCaptor.getValue();

        assertThat(capturedCustomer.getId()).isEqualTo(customer.getId());
        assertThat(capturedCustomer.getName()).isEqualTo(customer.getName());
        assertThat(capturedCustomer.getEmail()).isEqualTo(updated.email());
        assertThat(capturedCustomer.getAge()).isEqualTo(customer.getAge());
        assertThat(capturedCustomer.getGender()).isEqualTo(customer.getGender());
    }

    @Test
    void willThrowWhenTryingToUpdateCustomerEmailWhenAlreadyTaken() {
        //given
        int id = 1;
        Customer customer = new Customer(id, "ahmed", 22, "test@gmail.com", Gender.MALE);

        when(customerDAO.getCustomer(id)).thenReturn(Optional.of(customer));

        String newEmail = "newEmail@test.com";

        CustomerUpdateRequest updated = new CustomerUpdateRequest(null, newEmail, null);

        when(customerDAO.existCustomerWithEmail(newEmail)).thenReturn(true);

        //when
        assertThatThrownBy(() -> underTest.updateCustomer(id, updated))
                .isInstanceOf(DuplicatedRowException.class)
                .hasMessage("the email already exists!!");

        //then
        verify(customerDAO, never()).updateCustomer(any());
    }

    @Test
    void willThrowWhenCustomerUpdateHasNoChanges() {
        //given
        int id = 1;
        Customer customer = new Customer(id, "ahmed", 22, "test@gmail.com", Gender.MALE);

        when(customerDAO.getCustomer(id)).thenReturn(Optional.of(customer));

        CustomerUpdateRequest updated = new CustomerUpdateRequest(customer.getName(), customer.getEmail(), customer.getAge());

        //when
        assertThatThrownBy(() -> underTest.updateCustomer(id, updated))
                .isInstanceOf(RequestValidationException.class)
                .hasMessage("No changes found");

        //then
        verify(customerDAO, never()).updateCustomer(any());
    }
}