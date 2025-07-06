package com.technicians.clicktofix.model;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Table
@Data
public class Request  {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column
    private String description;

    @Column(nullable = true)
    private String address;

    @Column
    private LocalDateTime createdAt;

    @Column(nullable = true)
    private LocalDateTime assignedAt;

    @Column(nullable = true)
    private LocalDateTime estimatedArrival;

    @Enumerated(EnumType.STRING)
    private Status status= Status.PENDING;

    @Column(name = "customer_id", insertable = true,updatable = true)
    private Integer customerId;

    @ManyToOne
    @JoinColumn(name = "customer_id",insertable = false,updatable = false)
    private Customer customerRef;

    @Column(name = "technician_id", insertable = true,updatable = true)
    private Integer technicianId;

    @ManyToOne
    @JoinColumn(name = "technician_id",insertable = false,updatable = false)
    private Technician technicianRef;

    @PrePersist
    protected void onCreate() {
        this.createdAt = LocalDateTime.now();
    }
    
}
