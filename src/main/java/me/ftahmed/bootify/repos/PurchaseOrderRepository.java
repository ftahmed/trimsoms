package me.ftahmed.bootify.repos;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import me.ftahmed.bootify.domain.PurchaseOrder;


public interface PurchaseOrderRepository extends JpaRepository<PurchaseOrder, Long> {

    PurchaseOrder findByPoNumber(String poNumber);

    List<PurchaseOrder> findByVendorCode(String vendorCode);

    boolean existsByPoNumber(String poNumber);
    boolean existsByOrderOriginalFile(String orderOriginalFile);

}
