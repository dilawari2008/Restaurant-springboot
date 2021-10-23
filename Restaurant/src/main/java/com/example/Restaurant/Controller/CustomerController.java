package com.example.Restaurant.Controller;

import com.example.Restaurant.Entity.Customer;
import com.example.Restaurant.Services.CustomerService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@RestController
public class CustomerController {

    @Autowired
    private CustomerService customerService;

    @GetMapping("/customers")
    public List<Customer> getAllCustomers(){
        return customerService.getAllCustomers();
    }

    @GetMapping("/customers/{id}")
    public ResponseEntity<Optional<Customer>> getCustomerById(@PathVariable long id){
        return customerService.getCustomerById(id);
    }

    @PostMapping("/customers")
    public ResponseEntity<Customer> addCustomer(@Valid @RequestBody Customer customer){
        return customerService.addCustomer(customer);
    }

    @DeleteMapping("/customers/{id}")
    public ResponseEntity<Optional<Customer>> removeCustomer(@PathVariable long id){
        return customerService.removeCustomer(id);
    }

    @PutMapping("/customers")
    public ResponseEntity<Customer> updateCustomer(@Valid @RequestBody Customer customer){
        return customerService.updateCustomer(customer);
    }

    @GetMapping("/customers/lastname")
    public ResponseEntity<List<Customer>> findByLastName(@RequestBody String lastName){
        return customerService.findByLastName(lastName);
    }

    @GetMapping("/customers/firstname")
    public ResponseEntity<List<Customer>> findByFirstName(@RequestBody String firstName){
        return customerService.findByFirstName(firstName);
    }

    @GetMapping("/customers/name")
    public ResponseEntity<List<Customer>> findByFirstAndLastName(@RequestBody String firstName,@RequestBody String lastName){
        return customerService.findByFirstAndLastName(firstName,lastName);
    }

    @DeleteMapping("/customers/delete")
    public ResponseEntity<String> clearTables(){
        return customerService.clearTables();
    }

}
