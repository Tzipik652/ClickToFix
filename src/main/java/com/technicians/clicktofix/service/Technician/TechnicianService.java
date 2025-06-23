package com.technicians.clicktofix.service.Technician;

import java.util.List;

import com.technicians.clicktofix.model.Technician;

public interface TechnicianService {
    void add(Technician t);
    void update(Technician t);
    void delete(int technician_id);
    List<Technician> getAll();
    Technician getById(int technician_id);
    boolean existsById(int id);
    
}
