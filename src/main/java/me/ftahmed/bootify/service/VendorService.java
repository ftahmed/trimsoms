package me.ftahmed.bootify.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;
import me.ftahmed.bootify.domain.Vendor;
import me.ftahmed.bootify.repos.VendorRepository;


@Transactional
@Service
public class VendorService {

    @Autowired
    private VendorRepository vendorRepository;

    public List<Vendor> findAll() {
        return vendorRepository.findAll(Sort.by("VendorName"));
    }

    public Optional<Vendor> findByVendorCode(String vendorCode) {
        return vendorRepository.findByVendorCode(vendorCode);
    }
}
