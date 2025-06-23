package com.technicians.clicktofix.dal;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.technicians.clicktofix.model.Request;

@Repository
public interface RequestRepository extends CrudRepository<Request,Integer>{
    
}
