package com.techelevator.model;

import org.springframework.data.jpa.repository.JpaRepository;

import javax.persistence.*;

public class Image {

    private Long id;

    private byte[] data;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public byte[] getData() {
        return data;
    }

    public void setData(byte[] data) {
        this.data = data;
    }
}


