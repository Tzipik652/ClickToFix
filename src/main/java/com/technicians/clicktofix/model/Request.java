package com.technicians.clicktofix.model;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Table
@Data
public class Request  {

    @Id
    @GeneratedValue
    private int id;

    @ManyToOne
    @JoinColumn(name = "customer_id", nullable = false)
    private Customer customer;

    @ManyToOne
    @JoinColumn(name = "technician_id")
    private Technician technician;

    @Column
    private String description;

    @Column(nullable = true)
    private String address;

    @Column
    private LocalDateTime createdAt;

    @Column(nullable = false)
    private LocalDateTime assignedAt;

    @Column(nullable = false)
    private LocalDateTime estimatedArrival;

    @Enumerated(EnumType.STRING)
    private Status status;

}
