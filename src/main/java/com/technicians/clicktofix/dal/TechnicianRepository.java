package com.technicians.clicktofix.dal;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.technicians.clicktofix.model.Technician;

@Repository
public interface TechnicianRepository extends CrudRepository<Technician,Integer>{
    
}
