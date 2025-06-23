package com.technicians.clicktofix.service.Request;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.technicians.clicktofix.dal.RequestRepository;
import com.technicians.clicktofix.model.Request;

@Service
public class RequestServiceImpl implements RequestService{

    @Autowired
    private RequestRepository serviceRequestRep;

    @Override
    public void add(Request r) {
        if(serviceRequestRep.existsById(r.getId()))
            throw new RuntimeException("Request already exist!");
        serviceRequestRep.save(r);
    }

    @Override
    public void update(Request r) {
        if(!serviceRequestRep.existsById(r.getId()))
            throw new RuntimeException("Request does not exist!");
        serviceRequestRep.save(r);
    }

    @Override
    public void delete(int request_id) {
        if(!serviceRequestRep.existsById(request_id))
            throw new RuntimeException("Request does not exist!");
        serviceRequestRep.deleteById(request_id);
    }

    @Override
    public List<Request> getAll() {
        return (List<Request>)serviceRequestRep.findAll();
    }

    @Override
    public Request getById(int request_id) {
        return serviceRequestRep.findById(request_id).get();
    }

    @Override
    public boolean existsById(int id) {
       return serviceRequestRep.existsById(id);
    }
    
} 