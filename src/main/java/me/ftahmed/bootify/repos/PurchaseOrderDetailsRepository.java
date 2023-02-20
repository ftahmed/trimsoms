package me.ftahmed.bootify.repos;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import me.ftahmed.bootify.domain.PurchaseOrderDetails;


public interface PurchaseOrderDetailsRepository extends JpaRepository<PurchaseOrderDetails, Long> {

    PurchaseOrderDetails findByPoNumber(String poNumber);

    List<PurchaseOrderDetails> findByVendorCode(String vendorCode);

    boolean existsByPoNumber(String poNumber);

}
