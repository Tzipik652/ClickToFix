package com.technicians.clicktofix.service.Request;

import java.lang.reflect.Type;
import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.technicians.clicktofix.dal.CustomerRepository;
import com.technicians.clicktofix.dal.RequestRepository;
import com.technicians.clicktofix.dal.TechnicianRepository;
import com.technicians.clicktofix.dto.RequestDto;
import com.technicians.clicktofix.model.Customer;
import com.technicians.clicktofix.model.Request;
import com.technicians.clicktofix.model.Technician;

import jakarta.annotation.PostConstruct;
import org.modelmapper.PropertyMap;
import org.modelmapper.TypeMap;

@Service
public class RequestServiceImpl implements RequestService{

    @Autowired
    private RequestRepository serviceRequestRep;
    @Autowired
    private TechnicianRepository technicianRep;
    @Autowired
    private CustomerRepository customerRep;
    @Autowired
    private ModelMapper mapper;
// @PostConstruct
// public void initMapper() {
//     mapper.getConfiguration().setAmbiguityIgnored(true); // 🔴 חובה

//     TypeMap<Request, RequestDto> typeMap = mapper.createTypeMap(Request.class, RequestDto.class);

//     typeMap.addMappings(mapper -> {
//         mapper.map(Request::getCustomerId, RequestDto::setCustomerId);
//         mapper.map(Request::getTechnicianId, RequestDto::setTechnicianId);
//         mapper.map(src -> src.getCustomer() != null ? src.getCustomer().getName() : null,
//                    RequestDto::setCustomerName);
//         mapper.map(src -> src.getTechnician() != null ? src.getTechnician().getName() : null,
//                    RequestDto::setTechnicianName);
//     });
    
// }


    @Override
    public void add(RequestDto r) {
        if(serviceRequestRep.existsById(r.getId()))
            throw new RuntimeException("Request already exist!");
        serviceRequestRep.save(mapper.map(r,Request.class));
    }

    @Override
    public void update(RequestDto r) {
        if(!serviceRequestRep.existsById(r.getId()))
            throw new RuntimeException("Request does not exist!");
        serviceRequestRep.save(mapper.map(r,Request.class));
    }

    @Override
    public void delete(int request_id) {
        if(!serviceRequestRep.existsById(request_id))
            throw new RuntimeException("Request does not exist!");
        serviceRequestRep.deleteById(request_id);
    }

    @Override
    public List<RequestDto> getAll() {
        Type t = new TypeToken<List<RequestDto>>(){}.getType();
        return mapper.map((List<Request>)serviceRequestRep.findAll(),t);
    }

    @Override
    public RequestDto getById(int request_id) {
        try {
            return mapper.map(serviceRequestRep.findById(request_id).get(),RequestDto.class);
            
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public boolean existsById(int id) {
       return serviceRequestRep.existsById(id);
    }
    @Override
    public List<RequestDto> getRequestsByCustomerId(int customerId) {
        Customer c = customerRep.findById(customerId).orElseThrow();
        return c.getRequests().stream()
            .map(r -> mapper.map(r, RequestDto.class))
            .toList();
    }
    @Override
    public List<RequestDto> getRequestsByTechnicianId(int technicianId) {
        Technician t = technicianRep.findById(technicianId).orElseThrow();
        return t.getRequests().stream()
            .map(r -> mapper.map(r, RequestDto.class))
            .toList();
    }

   @Override
    public List<String> getAllDescriptionRequestsByTechnicianID(int technician_id) {
        return technicianRep.findById(technician_id)
            .orElseThrow(() -> new RuntimeException("Technician not found"))
            .getRequests()
            .stream()
            .map(Request::getDescription)
            .toList();    
    }

    @Override
    public List<String> getAllDescriptionRequestsByCustomerID(int customer_id) {
        return customerRep.findById(customer_id)
            .orElseThrow(() -> new RuntimeException("Customer not found"))
            .getRequests()
            .stream()
            .map(Request::getDescription)
            .toList();    
    }

    
} 