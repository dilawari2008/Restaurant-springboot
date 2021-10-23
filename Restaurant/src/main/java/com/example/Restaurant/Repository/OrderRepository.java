package com.example.Restaurant.Repository;

import com.example.Restaurant.Entity.Customer;
import com.example.Restaurant.Entity.Orders;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface OrderRepository extends JpaRepository<Orders, Long> {
    @Query(value = "select o from Orders o where o.customer = ?1")
    List<Orders> findOrdersByCustomer(Customer customer);

    @Query(value = "select sum(price) from Orders o where o.customer = ?1")
    Long billACustomer(Customer customer);
}
