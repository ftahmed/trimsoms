package me.ftahmed.bootify.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;
import me.ftahmed.bootify.domain.Order;
import me.ftahmed.bootify.repos.OrderRepository;


@Transactional
@Service
public class OrderService {

    @Autowired
    private OrderRepository orderRepository;

    public Long create(final Order order) {
        return orderRepository.save(order).getId();
    }

}
