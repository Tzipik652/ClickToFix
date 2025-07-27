package com.technicians.clicktofix.service.Technician;

import java.util.List;

import com.technicians.clicktofix.dto.TechnicianDto;
import com.technicians.clicktofix.model.Technician;

public interface TechnicianService {
    TechnicianDto add(TechnicianDto t);
    void update(TechnicianDto t);
    void delete(int technician_id);
    List<TechnicianDto> getAll();
    TechnicianDto getById(int technician_id);
    boolean existsById(int id);
    boolean existsByEmail(String email);
    Technician findByEmail(String email);
    
}
