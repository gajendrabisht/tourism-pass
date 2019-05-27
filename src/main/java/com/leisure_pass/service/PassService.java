package com.leisure_pass.service;

import com.leisure_pass.domain.Status;
import com.leisure_pass.entity.Customer;
import com.leisure_pass.entity.Pass;
import com.leisure_pass.repository.PassRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.UUID;

@Service
public class PassService {

    @Autowired
    private CustomerService customerService;

    @Autowired
    private PassRepository passRepository;

    public Pass getPassForCustomer(UUID customerId, UUID passId) {
        return customerService.get(customerId).getPasses().stream().filter(p -> p.getId().equals(passId)).findFirst().get();
    }

    public Pass createPassForCustomer(UUID customerId, @RequestBody Pass passRequest) {
        Pass pass = passRepository.saveAndFlush(passRequest);
        Customer customer = customerService.get(customerId);
        customer.addPass(pass);
        customerService.save(customer);
        return pass;
    }

    public void updatePassStatus(Pass pass, Status status) {
        pass.updateStatusTo(status);
        passRepository.saveAndFlush(pass);
    }

}
