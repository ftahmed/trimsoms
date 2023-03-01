package me.ftahmed.bootify.repos;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import me.ftahmed.bootify.domain.HangtagOrder;

public interface HangtagOrderRepository extends JpaRepository<HangtagOrder, Long> {
    
    HangtagOrder findById(String id);
    List<HangtagOrder> findByBrandAndSeasonOrderById(String brand, String season);
    List<HangtagOrder> findByPoNumberOrderById(String poNumber);

}
