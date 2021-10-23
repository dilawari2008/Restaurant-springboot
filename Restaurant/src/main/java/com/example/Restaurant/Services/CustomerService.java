package com.example.Restaurant.Services;

import com.example.Restaurant.Controller.CustomerController;
import com.example.Restaurant.CustomExceptions.EntityNotFoundException;
import com.example.Restaurant.Entity.Customer;
import com.example.Restaurant.Repository.CustomerRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CustomerService {
    private static Logger _logger = LoggerFactory.getLogger(CustomerController.class);
    @Autowired
    private CustomerRepository repository;

    public List<Customer> getAllCustomers(){
        return (List<Customer>) repository.findAll();
    }

    public ResponseEntity<Optional<Customer>> getCustomerById(long id){
        Optional<Customer> customer = repository.findById(id);
        if(customer!=null)
            return ResponseEntity.ok(customer);
        else
            throw new EntityNotFoundException("Customer not found");
    }


    public ResponseEntity<Customer> addCustomer(Customer customer){
        repository.save(customer);
        _logger.info("New Customer Inserted");
        _logger.info(customer.toString());
        return ResponseEntity.ok(customer);
    }

    public ResponseEntity<Optional<Customer>> removeCustomer(long id){
        Optional<Customer> customer = repository.findById(id);
        if(customer!=null){
            repository.deleteById(id);
            return ResponseEntity.ok(customer);
        }
        else
            throw new EntityNotFoundException("Customer not found");
    }

    public ResponseEntity<Customer> updateCustomer(Customer customer){
        Optional<Customer> oldCustomer = repository.findById(customer.getId());
        if(oldCustomer!=null){
            repository.save(customer);
            return ResponseEntity.ok(customer);
        }
        else
            throw new EntityNotFoundException("Customer not found");
    }

    public ResponseEntity<List<Customer> >findByLastName(String lastName){
        List<Customer> lastNameList= repository.findByLastName(lastName);
        if(lastNameList.size()==0)
            throw new EntityNotFoundException("EmptyList");
        return ResponseEntity.ok(lastNameList);
    }

    public ResponseEntity<List<Customer> >findByFirstName(String firstName){
        List<Customer> firstNameList= repository.findByFirstName(firstName);
        if(firstNameList.size()==0)
            throw new EntityNotFoundException("EmptyList");
        return ResponseEntity.ok(firstNameList);
    }

    public ResponseEntity<List<Customer> >findByFirstAndLastName(String firstName,String lastName){
        List<Customer> nameList= repository.findByFirstNameAndLastName(firstName,lastName);
        if(nameList.size()==0)
            throw new EntityNotFoundException("EmptyList");
        return ResponseEntity.ok(nameList);
    }

    public ResponseEntity<String> clearTables()
    {
        repository.deleteAll();
        return ResponseEntity.ok("Customer table cleared.");
    }
}
