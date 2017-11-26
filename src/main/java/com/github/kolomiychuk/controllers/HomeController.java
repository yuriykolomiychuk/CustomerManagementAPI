package com.github.kolomiychuk.controllers;

import com.github.kolomiychuk.dto.CreateResponse;
import com.github.kolomiychuk.entities.Customer;
import com.github.kolomiychuk.repositories.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;


@RestController
public class HomeController {
    @Autowired
    CustomerRepository repository;

    @GetMapping(value = "/")
    public String index() {
        return "Hello world";
    }

    @GetMapping(value = "/private")
    public String privateArea() {
        return "Private area";
    }


    @RequestMapping(value = "/add", headers = "Accept=application/json", method = RequestMethod.POST)
    public ResponseEntity process(@RequestBody Map<String, String> data) {
        Customer savedCustomer = repository.save(new Customer(data.get("name"), data.get("phone"), data.get("address")));
        CreateResponse cr = new CreateResponse();
        cr.setId(savedCustomer.getId());
        cr.setStatus("New customer has been added successfully");
        return new ResponseEntity<CreateResponse>(cr, HttpStatus.CREATED);
    }

    @RequestMapping(value = "/findall", method = RequestMethod.GET)
    public ResponseEntity findAll() {

        Set<Map<String, String>> resulSet = new HashSet<>();
        for (Customer cust : repository.findAll()) {
            Map<String, String> result = new HashMap<>();
            result.put("name", cust.getName());
            result.put("phone", cust.getPhone());
            result.put("address", cust.getAddress());
            resulSet.add(result);
        }
        return ResponseEntity.ok(resulSet);
    }

    @RequestMapping(value = "/findbyid", method = RequestMethod.GET)
    public ResponseEntity findById(@RequestParam("id") long id) {
        Customer result = repository.findOne(id);
        return new ResponseEntity<Customer>(result, HttpStatus.OK);
    }

    @RequestMapping(value = "/findbyname", method = RequestMethod.GET)
    public ResponseEntity fetchDataByName(@RequestParam("name") String name) {
        Set<Map<String, String>> resultSet = new HashSet<>();
        for (Customer cust : repository.findByName(name)) {
            Map<String, String> result = new HashMap<>();
            result.put("name", cust.getName());
            result.put("phone", cust.getPhone());
            result.put("address", cust.getAddress());
            resultSet.add(result);
        }

        return ResponseEntity.ok(resultSet);
    }
}