package com.example.Restaurant.Controller;

import com.example.Restaurant.Entity.Customer;
import com.example.Restaurant.Entity.Orders;
import com.example.Restaurant.Repository.CustomerRepository;
import com.example.Restaurant.Services.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

@RestController
public class OrderController {
    @Autowired
    private OrderService orderService;
    @Autowired
    private CustomerRepository customerRepository;

    @GetMapping("/orders")
    public Collection<Orders> getAllOrders(){
        return orderService.getAllOrders();
    }

    @GetMapping("/orders/{id}")
    public ResponseEntity<Optional<Orders>> getOrderById(@PathVariable long id){
        return orderService.getOrderById(id);
    }

    @PostMapping("/customers/{id}")
    public ResponseEntity<Orders> addOrder(@Valid @RequestBody Orders order, @PathVariable long id){
        return orderService.addOrder(order,id);
    }

    @DeleteMapping("/orders/{id}")
    public ResponseEntity<Optional<Orders>> removeOrder(@PathVariable long id){
        return orderService.removeOrder(id);
    }

    @PutMapping("/orders")
    public ResponseEntity<Orders> updateOrder(@Valid @RequestBody Orders order){
        return orderService.updateOrder(order);
    }

    @GetMapping("/orders/customer/{id}")
    public ResponseEntity<Customer> customerGivenOrder(@PathVariable long id){
        return orderService.customerGivenOrder(id);
    }

    @GetMapping("/customers/orders/{id}")
    public ResponseEntity<List<Orders>> ordersGivenCustomer(@PathVariable long id){
        return orderService.ordersGivenCustomer(id);
    }

    @GetMapping("/customers/bill/{id}")
    public ResponseEntity<Long> totalCostOfACustomer(@PathVariable long id){
        return orderService.totalCostOfACustomer(id);
    }

    @GetMapping("/customers/bill")
    public HashMap<Customer, Long> totalCostOfEachCustomer(){
        return orderService.totalCostOfEachCustomer();
    }

    @GetMapping("/sales")
    public Long sales(){
        return orderService.sales();
    }

    @DeleteMapping("/orders/delete")
    public ResponseEntity<String> clearTables(){
        return orderService.clearTables();
    }
}
