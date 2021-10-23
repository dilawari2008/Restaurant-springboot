package com.example.Restaurant.Services;

import com.example.Restaurant.CustomExceptions.EntityNotFoundException;
import com.example.Restaurant.Entity.Customer;
import com.example.Restaurant.Entity.Orders;
import com.example.Restaurant.Repository.CustomerRepository;
import com.example.Restaurant.Repository.OrderRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

@Service
public class OrderService {
    private static Logger _logger = LoggerFactory.getLogger(OrderService.class);
    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    private CustomerRepository customerRepository;

    public Collection<Orders> getAllOrders(){
        return (List<Orders>) orderRepository.findAll();
    }

    public ResponseEntity<Optional<Orders>> getOrderById(long id){
        Optional<Orders> order = orderRepository.findById(id);
        if(order!=null)
            return ResponseEntity.ok(order);
        else
            throw new EntityNotFoundException("Order not found");
    }

    public ResponseEntity<Orders> addOrder(Orders order, long id){
        Optional<Customer> customer = customerRepository.findById(id);
        if(customer==null)
            throw new EntityNotFoundException("Customer not found");
        Customer newCustomer = customer.get();
        newCustomer.getOrder().add(order);
        order.setCustomer(newCustomer);
        //https://stackoverflow.com/questions/16577907/hibernate-onetomany-relationship-causes-infinite-loop-or-empty-entries-in-json
        //how to resolve the infinite loop problem
        orderRepository.save(order);
        _logger.info("New order Inserting....");
        _logger.info(order.toString());
        return ResponseEntity.ok(order);
    }

    public ResponseEntity<Optional<Orders>> removeOrder(long id){
        Optional<Orders> order = orderRepository.findById(id);
        if(order!=null){
            orderRepository.deleteById(id);
            return ResponseEntity.ok(order);
        }
        else
            throw new EntityNotFoundException("Order not found");
    }

    public ResponseEntity<Orders> updateOrder(Orders order){
        Optional<Orders> oldOrder = orderRepository.findById(order.getId());
        if(oldOrder!=null){
            order.setCustomer(customerRepository.findById(order.getCustomer().getId()).get());
            orderRepository.save(order);
            return ResponseEntity.ok(order);
        }
        else
            throw new EntityNotFoundException("Order not found");
    }

    public ResponseEntity<Customer> customerGivenOrder(long id){
        Optional<Orders> oldOrder = orderRepository.findById(id);
        if(oldOrder==null)
            throw new EntityNotFoundException("Order not found");
        Orders order = oldOrder.get();
        return ResponseEntity.ok(order.getCustomer());
    }

    public ResponseEntity<List<Orders>> ordersGivenCustomer(long id){
        Optional<Customer> oldCustomer = customerRepository.findById(id);
        if(oldCustomer==null)
            throw new EntityNotFoundException("Cutomer not found");
        Customer customer = oldCustomer.get();
        List<Orders> ordersList = orderRepository.findOrdersByCustomer(customer);
        return ResponseEntity.ok(ordersList);
    }

    public ResponseEntity<Long> totalCostOfACustomer(long id){
        Optional<Customer> oldCustomer = customerRepository.findById(id);
        if(oldCustomer==null)
            throw new EntityNotFoundException("Customer not found");
        Customer customer = oldCustomer.get();
        Long cost = orderRepository.billACustomer(customer);
        return ResponseEntity.ok(cost);
    }

    public HashMap<Customer,Long> totalCostOfEachCustomer(){
        List<Customer> customerList = customerRepository.findAll();
        HashMap<Customer,Long> costList = new HashMap<>();
        for( Customer customer: customerList)
        {
            Long cost = orderRepository.billACustomer(customer);
            costList.put(customer,cost);
        }
        return costList;
    }

    public Long sales(){
        List<Customer> customerList = customerRepository.findAll();
        HashMap<Customer,Long> costList = new HashMap<>();
        long sales = 0;
        for( Customer customer: customerList)
        {
            Long cost = orderRepository.billACustomer(customer);
            if(cost!=null)
            sales = sales + (long)cost;
        }
        return sales;
    }

    public ResponseEntity<String> clearTables()
    {
        orderRepository.deleteAll();
        return ResponseEntity.ok("Order table cleared.");
    }
}
