package com.technicians.clicktofix.service.Request;

import java.util.List;

import com.technicians.clicktofix.dto.RequestDto;
import com.technicians.clicktofix.model.Status;

public interface RequestService {
    RequestDto add(RequestDto sr);
    void update(RequestDto sr);
    void updateStatus(int id, Status newStatus, int technicianId);
    void delete(int service_request_id);
    List<RequestDto> getAll();
    RequestDto getById(int service_request_id);
    boolean existsById(int service_request_id);
    List<String> getAllDescriptionRequestsByTechnicianID(int technician_id);
    List<String> getAllDescriptionRequestsByCustomerID(int customer_id);
    List<RequestDto> getRequestsByTechnicianId(int technician_id);
    List<RequestDto> getRequestsByCustomerId(int customerId);

}
