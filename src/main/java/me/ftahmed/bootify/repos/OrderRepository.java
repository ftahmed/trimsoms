package me.ftahmed.bootify.repos;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import me.ftahmed.bootify.domain.Order;

public interface OrderRepository extends JpaRepository<Order, Long> {
    
    Order findById(String id);
    List<Order> findByBrandAndSeasonOrderById(String brand, String season);
    List<Order> findByPoNumberOrderById(String poNumber);

}
