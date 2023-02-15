package me.ftahmed.bootify.repos;

import org.springframework.data.jpa.repository.JpaRepository;

import me.ftahmed.bootify.domain.CompositionItem;

public interface CompositionItemRepository extends JpaRepository<CompositionItem, Long> {
    
}
