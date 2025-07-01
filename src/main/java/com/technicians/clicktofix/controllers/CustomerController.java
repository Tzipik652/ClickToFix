package com.technicians.clicktofix.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import com.technicians.clicktofix.dto.LoginRequest;
import com.technicians.clicktofix.dto.RequestDto;
import com.technicians.clicktofix.model.Customer;
import com.technicians.clicktofix.service.Customer.CustomerService;
import com.technicians.clicktofix.service.Request.RequestService;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.GetMapping;





@RestController
@RequestMapping("/api/customers")
public class CustomerController {
    @Autowired
    private CustomerService cs;
    @Autowired
    private RequestService rs;

    // @PostMapping
    // public ResponseEntity<?> addCustomer(@RequestBody Customer customer) {
    //     try {
    //         cs.add(customer);
    //         return ResponseEntity.status(HttpStatus.CREATED).body("Customer created successfully");
    //     } catch (Exception e) {
    //         return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
    //                 .body("Failed to create customer: " + e.getMessage());
    //     }
    // }
    @PostMapping("/register")
    public ResponseEntity<?> registerCustomer(@RequestBody Customer customer) {
        try {
            if (cs.existsByEmail(customer.getEmail())) {
                return ResponseEntity.status(HttpStatus.CONFLICT).body("Email already in use");
            }
            cs.add(customer);
            return ResponseEntity.status(HttpStatus.CREATED).body("Customer registered successfully");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Failed to register customer: " + e.getMessage());
        }
    }

    @PostMapping("/login")
    public ResponseEntity<?> loginCustomer(@RequestBody LoginRequest loginRequest) {
        try {
            Customer customer = cs.findByEmail(loginRequest.getEmail());
            if (customer == null || !customer.getPasswordHash().equals(loginRequest.getPassword())) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid email or password");
            }
            return ResponseEntity.ok(customer); // אפשר גם להחזיר טוקן JWT במקום פרטי לקוח
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Failed to login: " + e.getMessage());
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateCustomer(@PathVariable int id, @RequestBody Customer customer) {
        if (!cs.existsById(id)) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Customer not found");
        }

        try {
            customer.setId(id);
            cs.update(customer);
            return ResponseEntity.ok("Customer updated successfully");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Failed to update customer: " + e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteCustomer(@PathVariable int id) {
        if (!cs.existsById(id)) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Customer not found");
        }

        try {
            cs.delete(id);
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Failed to delete customer: " + e.getMessage());
        }
    }

    @GetMapping
    public ResponseEntity<?> getAllCustomers() {
        try {
            List<Customer> list = cs.getAll();
            return ResponseEntity.ok(list);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Failed to fetch customers: " + e.getMessage());
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getCustomerById(@PathVariable int id) {
        Customer tech = cs.getById(id);
        if (tech == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Customer not found");
        }
        return ResponseEntity.ok(tech);
    }
    @GetMapping("{id}/requests")
    public ResponseEntity<?> getRequestsByCustomerId(@PathVariable int id) {
        List<RequestDto> tech = rs.getRequestsByCustomerId(id);
        if (tech.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Request not found");
        }
        return ResponseEntity.ok(tech);
    }
    @GetMapping("{id}/requests/descriptions")
    public ResponseEntity<?> getAllDescriptionRequestsByCustomerID(@PathVariable int id) {
        List<String> tech = rs.getAllDescriptionRequestsByCustomerID(id);
        if (tech.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Request not found");
        }
        return ResponseEntity.ok(tech);
    }
}