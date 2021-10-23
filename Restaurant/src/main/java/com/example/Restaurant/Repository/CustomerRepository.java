package com.example.Restaurant.Repository;

import com.example.Restaurant.Entity.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface CustomerRepository extends JpaRepository<Customer, Long> {

    @Query(value = "select c from Customer c where c.firstName = ?1")
    List<Customer> findByFirstName(String firstName);

    @Query(value = "select c from Customer c where c.lastName = ?1")
    List<Customer> findByLastName(String lastName);

    @Query(value = "select c from Customer c where c.lastName = ?2 and c.firstName = ?1")
    List<Customer> findByFirstNameAndLastName(String firstName,String lastName);

}
