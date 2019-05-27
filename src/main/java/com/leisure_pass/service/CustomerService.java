package com.leisure_pass.service;

import com.leisure_pass.entity.Customer;
import com.leisure_pass.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class CustomerService {

    @Autowired
    private CustomerRepository customerRepository;

    public List<Customer> getAll() {
        return customerRepository.findAll();
    }

    public Customer get(UUID customerId) {
        return customerRepository.findById(customerId).get();
    }

    public Customer save(Customer customer) {
        return customerRepository.saveAndFlush(customer);
    }


}
