package com.raslan.customer;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.sql.ResultSet;
import java.sql.SQLException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CustomerRowMapperTest {

    @Mock
    ResultSet resultSet;

    @Test
    void mapRow() throws SQLException {
        //given
        CustomerRowMapper customerRowMapper = new CustomerRowMapper();
        when(resultSet.getInt("id")).thenReturn(1);
        when(resultSet.getInt("age")).thenReturn(22);
        when(resultSet.getString("name")).thenReturn("ahmed");
        when(resultSet.getString("email")).thenReturn("ahmed@gmail.com");
        when(resultSet.getString("password")).thenReturn("password");
        when(resultSet.getString("gender")).thenReturn("FEMALE");


        // When
        Customer actual = customerRowMapper.mapRow(resultSet, 1);

        // Then
        Customer expected = new Customer(
                1,
                "ahmed",
                22,
                "ahmed@gmail.com",
                "password",
                Gender.FEMALE
        );
        assertThat(actual).isEqualTo(expected);
    }
}