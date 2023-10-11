package com.raslan.auth;

import com.raslan.customer.Customer;
import com.raslan.dto.CustomerDTO;
import com.raslan.dto.CustomerTokenResponseDTO;
import com.raslan.dto.LoginRequestDTO;
import com.raslan.jwt.JWTUtil;
import com.raslan.mapper.CustomerMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;


@ExtendWith(MockitoExtension.class)
class LoginServiceTest {

    @Mock
    private AuthenticationManager authenticationManager;
    @Mock
    private CustomerMapper customerMapper;
    @Mock
    private JWTUtil jwtUtil;

    @InjectMocks
    private LoginService underTest;

    @Test
    void login() {
        LoginRequestDTO request = mock(LoginRequestDTO.class) ;
        Authentication authentication = mock(Authentication.class) ;
        Customer customer = mock(Customer.class) ;
        CustomerDTO customerDTO = mock(CustomerDTO.class) ;
        String token = "5%$%kjfjk-66" ;

        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken =
                new UsernamePasswordAuthenticationToken(request.username(), request.password());

        when(authenticationManager.authenticate(usernamePasswordAuthenticationToken)).thenReturn(authentication) ;

        when(authentication.getPrincipal()).thenReturn(customer) ;

        when(customerMapper.customerToCustomerDto(customer)).thenReturn(customerDTO);

        when(jwtUtil.issueToken(customerDTO.username(), customerDTO.rules()))
                .thenReturn(token);

        CustomerTokenResponseDTO response = underTest.login(request) ;

        assertThat(response).isNotNull();
        assertThat(token).isEqualTo(response.token()) ;
        assertThat(customerDTO).isEqualTo(response.customer());

        verify(authenticationManager).authenticate(usernamePasswordAuthenticationToken) ;
        verify(authentication).getPrincipal();
        verify(customerMapper).customerToCustomerDto(customer);
        verify(jwtUtil).issueToken(customerDTO.username(), customerDTO.rules()) ;
    }

}