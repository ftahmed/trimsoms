package me.ftahmed.bootify.repos;

import org.springframework.data.jpa.repository.JpaRepository;

import me.ftahmed.bootify.domain.TrimsItem;

public interface TrimsItemRepository extends JpaRepository<TrimsItem, Long> {
    
}
