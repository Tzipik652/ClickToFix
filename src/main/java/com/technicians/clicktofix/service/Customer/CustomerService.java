package com.technicians.clicktofix.service.Customer;

import java.util.List;

import com.technicians.clicktofix.model.Customer;


public interface CustomerService {
    void add(Customer t);
    void update(Customer t);
    void delete(int customer_id);
    List<Customer> getAll();
    Customer getById(int customer_id);
    boolean existsById(int id);
    boolean existsByEmail(String email);
    Customer findByEmail(String email);
}
