package me.ftahmed.bootify.repos;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import me.ftahmed.bootify.domain.Order;

public interface OrderRepository extends JpaRepository<Order, Long> {
    
    List<Order> findByBrandAndSeason(String brand, String season);
    Optional<Order> findByPoNumber(String poNumber);
}
