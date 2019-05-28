package com.leisure_pass.controller;

import com.leisure_pass.entity.Pass;
import com.leisure_pass.service.PassService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

import static com.leisure_pass.domain.Status.Active;
import static com.leisure_pass.domain.Status.Inactive;
import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@RequestMapping(path = "/customers/{customerId}/passes", produces = APPLICATION_JSON_VALUE, consumes = APPLICATION_JSON_VALUE)
public class PassController {

    @Autowired
    private PassService passService;

    @PostMapping
    public ResponseEntity create(@PathVariable UUID customerId, @RequestBody Pass passRequest) {
        Pass pass = passService.createPassForCustomer(customerId, passRequest);
        return new ResponseEntity(pass, CREATED);
    }

    @PutMapping(path = "/{passId}/cancel")
    public void cancel(@PathVariable UUID customerId, @PathVariable UUID passId) {
        passService.updatePassStatus(passService.getPassForCustomer(customerId, passId), Inactive);
    }

    @PutMapping(path = "/{passId}/renew")
    public void renew(@PathVariable UUID customerId, @PathVariable UUID passId) {
        passService.updatePassStatus(passService.getPassForCustomer(customerId, passId), Active);
    }

}
