package com.technicians.clicktofix.dto;

import java.time.LocalDateTime;

import com.technicians.clicktofix.model.Status;

import lombok.Data;

@Data
public class RequestDto {

    private Integer id;

    private String description;

    private String address;

    private LocalDateTime createdAt;

    private LocalDateTime assignedAt;

    private LocalDateTime estimatedArrival;

    private Status status;

    private Integer customerId;
    private String customerName;

    private Integer technicianId;
    private String technicianName;

}
