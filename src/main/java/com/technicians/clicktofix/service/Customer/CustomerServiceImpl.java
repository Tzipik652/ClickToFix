package com.technicians.clicktofix.service.Customer;

import java.lang.reflect.Type;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.technicians.clicktofix.dal.CustomerRepository;
import com.technicians.clicktofix.dto.CustomerDto;
import com.technicians.clicktofix.model.Customer;

@Service
public class CustomerServiceImpl implements CustomerService {
    @Autowired
    private CustomerRepository customerRep;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private ModelMapper mapper;

    @Override
    public void add(CustomerDto c) {
        if(customerRep.existsById(c.getId()))
            throw new RuntimeException("customer already exist!");
        String hashedPassword = passwordEncoder.encode(c.getPassword());
        c.setPassword(hashedPassword);        
        customerRep.save(mapper.map(c, Customer.class));
    }

    @Override
    public void update(Customer c) {
        if(!customerRep.existsById(c.getId()))
            throw new RuntimeException("customer does not exist!");
        customerRep.save(c);
    }

     @Override
    public void delete(int customer_id) {
        if(!customerRep.existsById(customer_id))
            throw new RuntimeException("customer does not exist!");
        customerRep.deleteById(customer_id);
    }

    @Override
    public List<CustomerDto> getAll() {
        Type listType = new TypeToken<List<CustomerDto>>(){}.getType();
        return mapper.map((List<Customer>)customerRep.findAll(), listType);
    }

    @Override
    public CustomerDto getById(int customer_id) {
        try{
            return mapper.map(customerRep.findById(customer_id).get(), CustomerDto.class);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public boolean existsById(int id) {
       return customerRep.existsById(id);
    }

    @Override
    public boolean existsByEmail(String email) {
        return customerRep.existsByEmail(email);
    }

    @Override
    public Customer findByEmail(String email) {
        try{
            return customerRep.findByEmail(email).get();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
    
} 
