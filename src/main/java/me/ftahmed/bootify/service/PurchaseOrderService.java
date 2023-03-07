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

    public PurchaseOrder findByProductAndPoNumber(final String product, final String poNumber) {
        return poRepository.findByProductAndPoNumber(product, poNumber);
    }

    public PurchaseOrder findByProductAndReferenceOrder(final String product, final String referenceOrder) {
        return poRepository.findByProductAndReferenceOrder(product, referenceOrder);
    }
    
    public List<PurchaseOrder> findByProduct(final String product) {
        return poRepository.findByProductOrderByPoNumber(product);
    }
    
    public List<PurchaseOrder> findByProductAndVendorCode(final String product, final String vendorCode) {
        return poRepository.findByProductAndVendorCodeOrderByPoNumber(product, vendorCode);
    }
    
    public boolean poNumberExists(final String product, final String poNumber) {
        return poRepository.existsByProductAndPoNumber(product, poNumber);
    }

    public boolean orderOriginalFileExists(final String orderOriginalFile) {
        return poRepository.existsByOrderOriginalFile(orderOriginalFile);
    }

    public Long create(final PurchaseOrder po) {
        return poRepository.save(po).getId();
    }

    public void update(final String poNumber, final PurchaseOrder po) {
        if (!poNumberExists(po.getProduct(), poNumber)) {
            throw new NotFoundException();
        }
        poRepository.save(po);
    }
}
