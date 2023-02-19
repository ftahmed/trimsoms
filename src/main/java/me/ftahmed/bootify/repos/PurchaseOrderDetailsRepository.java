package me.ftahmed.bootify.repos;

import me.ftahmed.bootify.domain.PurchaseOrderDetails;
import org.springframework.data.jpa.repository.JpaRepository;


public interface PurchaseOrderDetailsRepository extends JpaRepository<PurchaseOrderDetails, Long> {

    PurchaseOrderDetails findByPoNumber(String poNumber);
    boolean existsByPoNumber(String poNumber);

}
