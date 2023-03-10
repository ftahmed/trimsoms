package me.ftahmed.bootify.repos;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import me.ftahmed.bootify.domain.TrimsOrder;

public interface TrimsOrderRepository extends JpaRepository<TrimsOrder, Long> {
    
    TrimsOrder findById(String id);
    List<TrimsOrder> findByBrandOrderById(String brand);
    List<TrimsOrder> findByPoNumberOrderById(String poNumber);
    List<TrimsOrder> findByReferenceorderOrderById(String referenceorder);

}
