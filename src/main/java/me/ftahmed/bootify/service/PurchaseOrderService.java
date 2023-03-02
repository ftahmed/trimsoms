package me.ftahmed.bootify.service;

import java.util.List;

import org.springframework.stereotype.Service;

import me.ftahmed.bootify.domain.PurchaseOrder;
import me.ftahmed.bootify.repos.PurchaseOrderRepository;
import me.ftahmed.bootify.util.NotFoundException;


@Service
public class PurchaseOrderService {

    private final PurchaseOrderRepository poRepository;

    public PurchaseOrderService(final PurchaseOrderRepository poRepository) {
        this.poRepository = poRepository;
    }

    public List<PurchaseOrder> findAll() {
        return poRepository.findAll();
    }

    public List<PurchaseOrder> findByProduct(final String product) {
        return poRepository.findByProduct(product);
    }
    
    public List<PurchaseOrder> findByVendorCode(final String vendorCode) {
        return poRepository.findByVendorCode(vendorCode);
    }

    public List<PurchaseOrder> findByProductAndVendorCode(final String product, final String vendorCode) {
        return poRepository.findByProductAndVendorCode(product, vendorCode);
    }
    
    public PurchaseOrder findByPoNumber(final String poNumber) {
        return poRepository.findByPoNumber(poNumber);
    }

    public boolean poNumberExists(final String poNumber) {
        return poRepository.existsByPoNumber(poNumber);
    }

    public boolean orderOriginalFileExists(final String orderOriginalFile) {
        return poRepository.existsByOrderOriginalFile(orderOriginalFile);
    }

    public Long create(final PurchaseOrder po) {
        return poRepository.save(po).getId();
    }

    public void update(final String poNumber, final PurchaseOrder po) {
        if (!poNumberExists(poNumber)) {
            throw new NotFoundException();
        }
        poRepository.save(po);
    }
}
