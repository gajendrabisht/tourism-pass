package com.leisure_pass.controller;

import com.leisure_pass.repository.PassRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

import static com.leisure_pass.domain.Status.Active;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@RequestMapping(path = "/passes/{passId}/isValid", produces = APPLICATION_JSON_VALUE)
public class PassValidityController {

    @Autowired
    private PassRepository passRepository;

    @GetMapping
    public Boolean validate(@PathVariable UUID passId, @RequestHeader String vendorId) {
        return passRepository.findById(passId).get().getStatus().equals(Active);
    }

}