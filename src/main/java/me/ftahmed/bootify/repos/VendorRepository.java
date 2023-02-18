package me.ftahmed.bootify.repos;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import me.ftahmed.bootify.domain.Vendor;

public interface VendorRepository extends JpaRepository<Vendor, Long> {
    
    Optional<Vendor> findByVendorCode(String vendorCode);
}
