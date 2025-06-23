package com.technicians.clicktofix.service.Customer;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.technicians.clicktofix.dal.CustomerRepository;
import com.technicians.clicktofix.model.Customer;

@Service
public class CustomerServiceImpl implements CustomerService {
    @Autowired
    private CustomerRepository customerRep;

    @Override
    public void add(Customer t) {
        if(customerRep.existsById(t.getId()))
            throw new RuntimeException("customer already exist!");
        customerRep.save(t);
    }

    @Override
    public void update(Customer t) {
        if(!customerRep.existsById(t.getId()))
            throw new RuntimeException("customer does not exist!");
        customerRep.save(t);
    }

     @Override
    public void delete(int customer_id) {
        if(!customerRep.existsById(customer_id))
            throw new RuntimeException("customer does not exist!");
        customerRep.deleteById(customer_id);
    }

    @Override
    public List<Customer> getAll() {
        return (List<Customer>)customerRep.findAll();
    }

    @Override
    public Customer getById(int customer_id) {
        return customerRep.findById(customer_id).get();
    }

    @Override
    public boolean existsById(int id) {
       return customerRep.existsById(id);
    }

    
} 
