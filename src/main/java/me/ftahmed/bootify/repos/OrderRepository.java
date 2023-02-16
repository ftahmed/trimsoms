package me.ftahmed.bootify.repos;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import me.ftahmed.bootify.domain.Order;
import me.ftahmed.bootify.domain.PurchaseOrder;

public interface OrderRepository extends JpaRepository<Order, Long> {
    
    List<Order> findByBrandAndSeason(String brand, String season);
    List<Order> findByPoNumber(String poNumber);

    
    @Query("SELECT DISTINCT NEW me.ftahmed.bootify.domain.PurchaseOrder(orderType, orderStatus, brand, season, poNumber, articleNumber, vendorId, factoryName1) FROM Order o")
    List<PurchaseOrder> findAllDistinctPurchaseOrders();

    @Query("SELECT DISTINCT NEW me.ftahmed.bootify.domain.PurchaseOrder(orderType, orderStatus, brand, season, poNumber, articleNumber, vendorId, factoryName1) FROM Order o where o.poNumber = :poNumber")
    List<PurchaseOrder> findDistinctPurchaseOrdersByPoNumber(String poNumber);
}
