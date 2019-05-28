package com.leisure_pass.entity;

import com.leisure_pass.domain.Status;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.Date;
import java.util.UUID;

@Entity
public class Pass {

    @Id
    private UUID id = UUID.randomUUID();
    private String city;
    private int length;
    private Status status = Status.Active;

    public void updateStatusTo(Status updatedStatus) {
        this.status = updatedStatus;
    }

    public Pass() {
        // for jackson mapper
    }

    public Pass(UUID id, String city, int length, Status status) {
        this.id = id;
        this.city = city;
        this.length = length;
        this.status = status;
    }

    public UUID getId() {
        return id;
    }

    public String getCity() {
        return city;
    }

    public int getLength() {
        return length;
    }

    public Status getStatus() {
        return status;
    }
}
