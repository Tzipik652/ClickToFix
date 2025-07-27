package com.technicians.clicktofix.dto;

import com.technicians.clicktofix.model.Location;

import lombok.Data;

@Data
public class CustomerDto {
    
    private int id;

    private String name;

    private String password;

    private String email;

    private String phone;

    private String address;

    private Location location;


}
