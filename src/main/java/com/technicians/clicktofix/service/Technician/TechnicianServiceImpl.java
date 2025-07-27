package com.technicians.clicktofix.service.Technician;

import java.lang.reflect.Type;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.technicians.clicktofix.dal.TechnicianRepository;
import com.technicians.clicktofix.dto.TechnicianDto;
import com.technicians.clicktofix.model.Technician;

@Service
public class TechnicianServiceImpl implements TechnicianService{

    @Autowired
    private TechnicianRepository technicianRep;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private ModelMapper mapper;
    @Override
    public TechnicianDto add(TechnicianDto t) {
        if(technicianRep.existsById(t.getId()))
            throw new RuntimeException("technician already exist!");
        String hashedPassword = passwordEncoder.encode(t.getPassword());
        t.setPassword(hashedPassword);
        Technician technician = mapper.map(t, Technician.class);
        return mapper.map(technicianRep.save(technician), TechnicianDto.class);
    }

    @Override
    public void update(TechnicianDto t) {
        if(!technicianRep.existsById(t.getId()))
            throw new RuntimeException("technician does not exist!");
        technicianRep.save(mapper.map(t, Technician.class));
    }

    @Override
    public void delete(int technician_id) {
        if(!technicianRep.existsById(technician_id))
            throw new RuntimeException("technician does not exist!");
        technicianRep.deleteById(technician_id);
    }

    @Override
    public List<TechnicianDto> getAll() {
        Type listType = new TypeToken<List<TechnicianDto>>(){}.getType();
        return mapper.map((List<Technician>)technicianRep.findAll(), listType);
    }

    @Override
    public TechnicianDto getById(int technician_id) {
        try{
            return mapper.map(technicianRep.findById(technician_id).get(), TechnicianDto.class);
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
