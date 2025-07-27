package com.technicians.clicktofix.dto;

import com.technicians.clicktofix.model.Location;


import lombok.Data;

@Data
public class TechnicianDto {

    private int id;

    private String name;

    private String password;

    private String email;

    private String phone;

    private String[] expertise;

    private Location location;
    
}
