package com.leisure_pass.controller;

import com.leisure_pass.domain.Status;
import com.leisure_pass.entity.Pass;
import com.leisure_pass.service.PassService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.UUID;

import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
class PassValidityControllerTest {

    @Mock
    private PassService passService;

    @InjectMocks
    PassValidityController passValidityController;

    @Test
    public void shouldValidateToActiveTrue() {
        UUID passId = UUID.randomUUID();
        given(passService.getPass(passId)).willReturn(new Pass(passId, "London", 7, Status.Active));

        assertTrue(passValidityController.validate(passId, "someVendorId"));
    }

    @Test
    public void shouldValidateToInactiveFalse() {
        UUID passId = UUID.randomUUID();
        given(passService.getPass(passId)).willReturn(new Pass(passId, "London", 7, Status.Inactive));

        assertFalse(passValidityController.validate(passId, "someVendorId"));
    }

}