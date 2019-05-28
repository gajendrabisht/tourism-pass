package com.leisure_pass.service;

import com.leisure_pass.entity.Customer;
import com.leisure_pass.exception.CustomerNotFoundException;
import com.leisure_pass.repository.CustomerRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class CustomerServiceTest {

    @Mock
    private CustomerRepository customerRepository;

    @InjectMocks
    CustomerService customerService;

    @Test
    void shouldReturnAllCustomers() {
        List<Customer> expectedCustomers = Arrays.asList(new Customer(), new Customer());
        given(customerRepository.findAll()).willReturn(expectedCustomers);
        List<Customer> actualCustomers = customerService.getAll();
        assertThat(actualCustomers, is(expectedCustomers));
    }

    @Test
    void shouldThrowExceptionWhenCustomerNotFound() {
        UUID customerId = UUID.randomUUID();
        given(customerRepository.findById(customerId)).willReturn(Optional.empty());
        assertThrows(CustomerNotFoundException.class, () -> customerService.get(customerId));
    }

    @Test
    void shouldReturnCustomer() {
        UUID customerId = UUID.randomUUID();
        Customer customer = new Customer(customerId, "John", "London");
        given(customerRepository.findById(customerId)).willReturn(Optional.of(customer));
        assertThat(customerService.get(customerId).getName(), is("John"));
    }

    @Test
    void shouldSaveCustomer() {
        Customer customer = mock(Customer.class);
        customerService.save(customer);
        verify(customerRepository).saveAndFlush(customer);
    }
}