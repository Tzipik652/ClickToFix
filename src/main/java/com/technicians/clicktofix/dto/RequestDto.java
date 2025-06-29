package com.technicians.clicktofix.dto;

import java.time.LocalDateTime;

import com.technicians.clicktofix.model.Status;

import lombok.Data;

@Data
public class RequestDto {

    private int id;

    private String description;

    private String address;

    private LocalDateTime createdAt;

    private LocalDateTime assignedAt;

    private LocalDateTime estimatedArrival;

    private Status status;

    private int customerId;
    private String customerName;

    private int technicianId;
    private String technicianName;

}
