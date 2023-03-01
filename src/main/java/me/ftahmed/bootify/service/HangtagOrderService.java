package me.ftahmed.bootify.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;
import me.ftahmed.bootify.domain.HangtagOrder;
import me.ftahmed.bootify.repos.HangtagOrderRepository;


@Transactional
@Service
public class HangtagOrderService {

    @Autowired
    private HangtagOrderRepository htOrderRepository;

    public List<HangtagOrder> findAll() {
        return htOrderRepository.findAll(Sort.by("id"));
    }

    public HangtagOrder findById(String id) {
        return htOrderRepository.findById(id);
    }

    public List<HangtagOrder> findByBrandAndSeason(String brand, String season) {
        return htOrderRepository.findByBrandAndSeasonOrderById(brand, season);
    }

    public List<HangtagOrder> findByPoNumber(String poNumber) {
        return htOrderRepository.findByPoNumberOrderById(poNumber);
    }

    public Long create(final HangtagOrder order) {
        return htOrderRepository.save(order).getId();
    }

    public void update(final HangtagOrder order) {
        htOrderRepository.save(order);
    }
}
