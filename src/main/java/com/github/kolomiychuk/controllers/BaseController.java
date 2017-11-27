package com.github.kolomiychuk.controllers;

import com.github.kolomiychuk.dto.CreateResponse;
import com.github.kolomiychuk.entities.Customer;
import com.github.kolomiychuk.repositories.CustomerRepository;
import org.omg.CORBA.OBJ_ADAPTER;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;


@RestController
public class BaseController {
    public static final String ID = "id";
    public static final String NAME = "name";
    public static final String PHONE = "phone";
    public static final String ADDRESS = "address";
    @Autowired
    CustomerRepository repository;

    @GetMapping(value = "/")
    public String index() {
        return "Welcome to Customer Management API";
    }

    @RequestMapping(value = "customers/add", headers = "Accept=application/json", method = RequestMethod.POST)
    public ResponseEntity add(@RequestBody Map<String, String> data) {
        Customer savedCustomer = repository.save(new Customer(data.get(NAME), data.get(PHONE), data.get(ADDRESS)));
        CreateResponse cr = new CreateResponse();
        cr.setId(savedCustomer.getId());
        cr.setStatus("New customer has been added successfully");
        return new ResponseEntity<CreateResponse>(cr, HttpStatus.CREATED);
    }

    @RequestMapping(value = "customers/findall", method = RequestMethod.GET)
    public ResponseEntity findAll() {

        Set<Map<String,Object>> resulSet = new HashSet<>();
        for (Customer cust : repository.findAll()) {
            Map<String, Object> result = new HashMap<>();
            result.put(ID, cust.getId());
            result.put(NAME, cust.getName());
            result.put(PHONE, cust.getPhone());
            result.put(ADDRESS, cust.getAddress());
            resulSet.add(result);
        }
        return ResponseEntity.ok(resulSet);
    }
    @RequestMapping(value = "customers/findbyid", method = RequestMethod.GET)
    public ResponseEntity findById(@RequestParam("id") long id) {
        Customer result = repository.findOne(id);
        return new ResponseEntity<Customer>(result, HttpStatus.OK);
    }

    @RequestMapping(value = "customers/findbyname", method = RequestMethod.GET)
    public ResponseEntity fetchDataByName(@RequestParam(NAME) String name) {
        Set<Map<String, Object>> resultSet = new HashSet<>();
        for (Customer cust : repository.findByName(name)) {
            Map<String, Object> result = new HashMap<>();
            result.put(ID, cust.getId());
            result.put(NAME, cust.getName());
            result.put(PHONE, cust.getPhone());
            result.put(ADDRESS, cust.getAddress());
            resultSet.add(result);
        }

        return ResponseEntity.ok(resultSet);
    }

    @RequestMapping(value = "customers_edit/update", headers = "Accept=application/json", method = RequestMethod.PUT)
    public ResponseEntity update (@RequestBody Map<String, Object> data) {
        Customer existingCustomer = repository.findOne( Long.valueOf(data.get("id").toString()));
        if (data.containsKey(NAME)){
            existingCustomer.setName(data.get(NAME).toString());
        }if (data.containsKey(PHONE)){
            existingCustomer.setPhone(data.get(PHONE).toString());
        }if (data.containsKey(ADDRESS)){
            existingCustomer.setAddress(data.get(ADDRESS).toString());
        }

       repository.save(existingCustomer);
        CreateResponse cr = new CreateResponse();
        cr.setId(existingCustomer.getId());
        cr.setStatus("Customer with id " +existingCustomer.getId()+ " has been updated successfully");
        return new ResponseEntity<CreateResponse>(cr, HttpStatus.CREATED);
    }
}