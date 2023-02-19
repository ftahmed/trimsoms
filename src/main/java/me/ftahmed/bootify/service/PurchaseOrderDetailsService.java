package me.ftahmed.bootify.service;

import org.springframework.stereotype.Service;

import me.ftahmed.bootify.domain.PurchaseOrderDetails;
import me.ftahmed.bootify.repos.PurchaseOrderDetailsRepository;
import me.ftahmed.bootify.util.NotFoundException;


@Service
public class PurchaseOrderDetailsService {

    private final PurchaseOrderDetailsRepository podRepository;

    public PurchaseOrderDetailsService(final PurchaseOrderDetailsRepository podRepository) {
        this.podRepository = podRepository;
    }

    public PurchaseOrderDetails findByPoNumber(final String poNumber) {
        return podRepository.findByPoNumber(poNumber);
    }

    public boolean poNumberExists(final String poNumber) {
        return podRepository.existsByPoNumber(poNumber);
    }

    public Long create(final PurchaseOrderDetails pod) {
        return podRepository.save(pod).getId();
    }

    public void update(final String poNumber, final PurchaseOrderDetails pod) {
        if (!poNumberExists(poNumber)) {
            throw new NotFoundException();
        }
        podRepository.save(pod);
    }
}
