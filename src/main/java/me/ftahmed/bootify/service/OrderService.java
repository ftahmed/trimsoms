package me.ftahmed.bootify.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;
import me.ftahmed.bootify.domain.Order;
import me.ftahmed.bootify.domain.PurchaseOrder;
import me.ftahmed.bootify.repos.OrderRepository;


@Transactional
@Service
public class OrderService {

    @Autowired
    private OrderRepository orderRepository;

    public List<Order> findAll() {
        return orderRepository.findAll(Sort.by("id"));
    }

    public List<PurchaseOrder> findAllDistinctPurchaseOrders() {
        return orderRepository.findAllDistinctPurchaseOrders();
    }

    public Long create(final Order order) {
        return orderRepository.save(order).getId();
    }

}
