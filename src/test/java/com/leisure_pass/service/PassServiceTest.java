package com.leisure_pass.service;

import com.leisure_pass.domain.Status;
import com.leisure_pass.entity.Customer;
import com.leisure_pass.entity.Pass;
import com.leisure_pass.exception.PassNotFoundException;
import com.leisure_pass.repository.PassRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;
import java.util.UUID;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class PassServiceTest {

    @Mock
    private CustomerService customerService;

    @Mock
    private PassRepository passRepository;

    @InjectMocks
    PassService passService;

    @Test
    void shouldThrowPassNotFoundExceptionForGetPass() {
        UUID passId = UUID.randomUUID();
        given(passRepository.findById(passId)).willReturn(Optional.empty());

        assertThrows(PassNotFoundException.class, () -> passService.getPass(passId));
    }

    @Test
    void shouldThrowPassNotFoundExceptionForGetPassForCustomer() {
        UUID customerId = UUID.randomUUID();
        Customer customer = new Customer(customerId, "John", "London");
        given(customerService.get(customerId)).willReturn(customer);

        assertThrows(PassNotFoundException.class, () -> passService.getPassForCustomer(customerId, UUID.randomUUID()));
    }

    @Test
    void shouldGetPass() {
        UUID passId = UUID.randomUUID();
        Pass expectedPass = new Pass(passId, "London", 7, Status.Active);
        given(passRepository.findById(passId)).willReturn(Optional.of(expectedPass));

        Pass actualPass = passService.getPass(passId);

        assertThat(actualPass, is(expectedPass));
    }

    @Test
    void shouldGetPassForCustomer() {
        UUID customerId = UUID.randomUUID();
        UUID passId = UUID.randomUUID();
        Customer customer = new Customer(customerId, "John", "London");
        customer.addPass(new Pass(passId, "London", 7, Status.Active));
        given(customerService.get(customerId)).willReturn(customer);

        Pass pass = passService.getPassForCustomer(customerId, passId);

        assertThat(pass.getId(), is(passId));
    }

    @Test
    void shouldCreatePassForCustomer() {
        UUID customerId = UUID.randomUUID();
        UUID passId = UUID.randomUUID();
        Customer customer = new Customer(customerId, "John", "London");
        given(customerService.get(customerId)).willReturn(customer);

        Pass pass = new Pass(passId, "London", 7, Status.Active);
        passService.createPassForCustomer(customerId, pass);

        verify(passRepository).saveAndFlush(pass);
        verify(customerService).save(customer);
    }

    @Test
    void shouldUpdatePassStatus() {
        UUID passId = UUID.randomUUID();
        Pass pass = new Pass(passId, "London", 7, Status.Active);
        passService.updatePassStatus(pass, Status.Inactive);
        verify(passRepository).saveAndFlush(pass);
    }

}