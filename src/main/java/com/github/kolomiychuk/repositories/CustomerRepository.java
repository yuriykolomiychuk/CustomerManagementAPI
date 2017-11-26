package com.github.kolomiychuk.repositories;

import com.github.kolomiychuk.entities.Customer;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface CustomerRepository extends CrudRepository<Customer, Long>{
    List<Customer> findByName(String name);
}