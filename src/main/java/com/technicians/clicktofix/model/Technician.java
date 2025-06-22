package com.technicians.clicktofix.model;

import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Table
@Data
public class Technician {

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
    private String[] expertise;

    @Embedded
    @Column
    private Location location;

}
