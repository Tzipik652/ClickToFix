package com.technicians.clicktofix.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import com.technicians.clicktofix.dto.RequestDto;
import com.technicians.clicktofix.service.Request.RequestService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.GetMapping;





@RestController
@RequestMapping("/api/requests")
public class RequestController {
    @Autowired
    private RequestService rs;

    @PostMapping
    public ResponseEntity<?> addRequest(@RequestBody RequestDto request) {
        try {
            rs.add(request);
            return ResponseEntity.status(HttpStatus.CREATED).body("Request created successfully");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Failed to create request: " + e.getMessage());
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateRequest(@PathVariable int id, @RequestBody RequestDto Request) {
        if (!rs.existsById(id)) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Request not found");
        }

        try {
            Request.setId(id);
            rs.update(Request);
            return ResponseEntity.ok("Request updated successfully");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Failed to update request: " + e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteRequest(@PathVariable int id) {
        if (!rs.existsById(id)) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Request not found");
        }

        try {
            rs.delete(id);
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Failed to delete request: " + e.getMessage());
        }
    }

    @GetMapping
    public ResponseEntity<?> getAllRequests() {
        try {
            List<RequestDto> list = rs.getAll();
            return ResponseEntity.ok(list);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Failed to fetch requests: " + e.getMessage());
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getRequestById(@PathVariable int id) {
        RequestDto tech = rs.getById(id);
        if (tech == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Request not found");
        }
        return ResponseEntity.ok(tech);
    }
   

}