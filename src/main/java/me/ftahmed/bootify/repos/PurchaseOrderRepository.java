package me.ftahmed.bootify.repos;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import me.ftahmed.bootify.domain.PurchaseOrder;


public interface PurchaseOrderRepository extends JpaRepository<PurchaseOrder, Long> {

    PurchaseOrder findByProductAndPoNumber(String product, String poNumber);
    PurchaseOrder findByProductAndReferenceOrder(String product, String referenceOrder);

    List<PurchaseOrder> findByProductOrderByPoNumber(String product);
    List<PurchaseOrder> findByProductAndVendorCodeOrderByPoNumber(String product, String vendorCode);

    boolean existsByProductAndPoNumber(String product, String poNumber);
    boolean existsByOrderOriginalFile(String orderOriginalFile);

}
