package com.technicians.clicktofix.dal;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.technicians.clicktofix.model.Technician;

@Repository
public interface TechnicianRepository extends CrudRepository<Technician,Integer>{

    boolean existsByEmail(String email);

    Optional<Technician> findByEmail(String email);
    
}
