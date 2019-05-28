package com.leisure_pass.entity;

import com.fasterxml.jackson.annotation.JsonInclude;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@JsonInclude
@Entity
public class Customer {

    @Id
    private UUID id = UUID.randomUUID();
    private String name;
    private String city;
    @OneToMany
    private List<Pass> passes = new ArrayList<>();

    public void addPass(Pass pass) {
        passes.add(pass);
    }

    public Customer() {
        // for jackson mapper
    }

    public Customer(UUID id, String name, String city) {
        this.id = id;
        this.name = name;
        this.city = city;
    }

    public UUID getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getCity() {
        return city;
    }

    public List<Pass> getPasses() {
        return passes;
    }

}
