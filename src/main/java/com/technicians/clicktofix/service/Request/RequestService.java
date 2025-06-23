package com.technicians.clicktofix.service.Request;

import java.util.List;

import com.technicians.clicktofix.model.Request;

public interface RequestService {
    void add(Request sr);
    void update(Request sr);
    void delete(int service_request_id);
    List<Request> getAll();
    Request getById(int service_request_id);
    boolean existsById(int service_request_id);
    
}
