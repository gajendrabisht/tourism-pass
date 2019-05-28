package com.leisure_pass.controller;

import com.leisure_pass.domain.Status;
import com.leisure_pass.entity.Pass;
import com.leisure_pass.service.PassService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;

import java.util.UUID;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class PassControllerTest {

    @Mock
    private PassService passService;

    @InjectMocks
    private PassController passController;

    @Test
    void create() {
        UUID customerId = UUID.randomUUID();
        Pass pass = mock(Pass.class);
        given(passService.createPassForCustomer(customerId, pass)).willReturn(pass);

        ResponseEntity responseEntity = passController.create(customerId, pass);

        verify(passService).createPassForCustomer(customerId, pass);
        assertThat(responseEntity.getStatusCodeValue(), is(201));
        assertThat(responseEntity.getBody(), is(pass));
    }

    @Test
    void cancel() {
        UUID customerId = UUID.randomUUID();
        UUID passId = UUID.randomUUID();
        Pass pass = mock(Pass.class);
        given(passService.getPassForCustomer(customerId, passId)).willReturn(pass);

        passController.cancel(customerId, passId);

        verify(passService).updatePassStatus(pass, Status.Inactive);
    }

    @Test
    void renew() {
        UUID customerId = UUID.randomUUID();
        UUID passId = UUID.randomUUID();
        Pass pass = mock(Pass.class);
        given(passService.getPassForCustomer(customerId, passId)).willReturn(pass);

        passController.renew(customerId, passId);

        verify(passService).updatePassStatus(pass, Status.Active);
    }
}