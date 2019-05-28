package com.leisure_pass.controller;

import com.leisure_pass.entity.Customer;
import com.leisure_pass.service.CustomerService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;

import java.util.Arrays;
import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class CustomerControllerTest {

    @Mock
    private CustomerService customerService;

    @InjectMocks
    CustomerController customerController;

    @Test
    void shouldGetAllCustomer() {
        List<Customer> customers = Arrays.asList(new Customer());
        given(customerService.getAll()).willReturn(customers);
        assertThat(customerController.getAll(), is(customers));
    }

    @Test
    void shouldCreateCustomer() {
        Customer customer = mock(Customer.class);
        given(customerService.save(customer)).willReturn(customer);

        ResponseEntity responseEntity = customerController.create(customer);

        verify(customerService).save(customer);
        assertThat(responseEntity.getStatusCodeValue(), is(201));
        assertThat(responseEntity.getBody(), is(customer));
    }

}