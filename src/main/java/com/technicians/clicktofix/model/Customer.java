package com.technicians.clicktofix.model;

import java.time.LocalDateTime;

import org.springframework.data.annotation.Id;

import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Table
@Data
public class Customer {
    
    @Id
    @GeneratedValue
    private int id;

     @Column
    private String name;

    @Column
    private String passwordHash;

    @Column(unique = true)
    private String email;

    @Column(unique = true)
    private String phone;

    @Column
    private String address;

    @Embedded
    @Column
    private Location location;

    @Column
    private LocalDateTime createdAt;


}
