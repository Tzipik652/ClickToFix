
package com.technicians.clicktofix.dal;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.technicians.clicktofix.model.Customer;

@Repository
public interface CustomerRepository extends CrudRepository<Customer, Integer> {

    
}