package com.technicians.clicktofix.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import com.technicians.clicktofix.dto.LoginRequest;
import com.technicians.clicktofix.dto.RequestDto;
import com.technicians.clicktofix.model.Technician;
import com.technicians.clicktofix.service.Request.RequestService;
import com.technicians.clicktofix.service.Technician.TechnicianService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.GetMapping;





@RestController
@RequestMapping("/api/technicians")
public class TechnicianController {
    @Autowired
    private TechnicianService ts;
    @Autowired
    private RequestService rs;

    // @PostMapping
    public ResponseEntity<?> addTechnician(@RequestBody Technician technician) {
        try {
            ts.add(technician);
            return ResponseEntity.status(HttpStatus.CREATED).body("Technician created successfully");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Failed to create technician: " + e.getMessage());
        }
    }
    @PostMapping("/register")
    public ResponseEntity<?> registerTechnician(@RequestBody Technician technician) {
        try {
            if (ts.existsByEmail(technician.getEmail())) {
                return ResponseEntity.status(HttpStatus.CONFLICT).body("Email already in use");
            }
            ts.add(technician);
            return ResponseEntity.status(HttpStatus.CREATED).body("Technician registered successfully");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Failed to register technician: " + e.getMessage());
        }
    }

    @PostMapping("/login")
    public ResponseEntity<?> loginTechnician(@RequestBody LoginRequest loginRequest) {
        try {
            Technician technician = ts.findByEmail(loginRequest.getEmail());
            if (technician == null || !technician.getPasswordHash().equals(loginRequest.getPassword())) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid email or password");
            }
            return ResponseEntity.ok(technician); // אפשר גם להחזיר טוקן JWT במקום פרטי לקוח
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Failed to login: " + e.getMessage());
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateTechnician(@PathVariable int id, @RequestBody Technician technician) {
        if (!ts.existsById(id)) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Technician not found");
        }

        try {
            technician.setId(id);
            ts.update(technician);
            return ResponseEntity.ok("Technician updated successfully");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Failed to update technician: " + e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteTechnician(@PathVariable int id) {
        if (!ts.existsById(id)) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Technician not found");
        }

        try {
            ts.delete(id);
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Failed to delete technician: " + e.getMessage());
        }
    }

    @GetMapping
    public ResponseEntity<?> getAllTechnicians() {
        try {
            List<Technician> list = ts.getAll();
            return ResponseEntity.ok(list);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Failed to fetch technicians: " + e.getMessage());
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getTechnicianById(@PathVariable int id) {
        Technician tech = ts.getById(id);
        if (tech == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Technician not found");
        }
        return ResponseEntity.ok(tech);
    }
    @GetMapping("{id}/requests")
    public ResponseEntity<?> getRequestsByTechnicianId(@PathVariable int id) {
        List<RequestDto> tech = rs.getRequestsByTechnicianId(id);
        if (tech.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Request not found");
        }
        return ResponseEntity.ok(tech);
    }
    @GetMapping("{id}/requests/descriptions")
    public ResponseEntity<?> getAllDescriptionRequestsByTechnicianID(@PathVariable int id) {
        List<String> tech = rs.getAllDescriptionRequestsByTechnicianID(id);
        if (tech.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Request not found");
        }
        return ResponseEntity.ok(tech);
    }
}