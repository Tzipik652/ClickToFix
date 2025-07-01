package com.technicians.clicktofix.service.Technician;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.technicians.clicktofix.dal.TechnicianRepository;
import com.technicians.clicktofix.model.Technician;

@Service
public class TechnicianServiceImpl implements TechnicianService{

    @Autowired
    private TechnicianRepository technicianRep;

    @Override
    public void add(Technician t) {
        if(technicianRep.existsById(t.getId()))
            throw new RuntimeException("technician already exist!");
        technicianRep.save(t);
    }

    @Override
    public void update(Technician t) {
        if(!technicianRep.existsById(t.getId()))
            throw new RuntimeException("technician does not exist!");
        technicianRep.save(t);
    }

    @Override
    public void delete(int technician_id) {
        if(!technicianRep.existsById(technician_id))
            throw new RuntimeException("technician does not exist!");
        technicianRep.deleteById(technician_id);
    }

    @Override
    public List<Technician> getAll() {
        return (List<Technician>)technicianRep.findAll();
    }

    @Override
    public Technician getById(int technician_id) {
        try{
            return technicianRep.findById(technician_id).get();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public boolean existsById(int id) {
       return technicianRep.existsById(id);
    }

    @Override
    public boolean existsByEmail(String email) {
        return technicianRep.existsByEmail(email);
    }

    @Override
    public Technician findByEmail(String email) {
        try{
            return technicianRep.findByEmail(email).get();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
    
}
