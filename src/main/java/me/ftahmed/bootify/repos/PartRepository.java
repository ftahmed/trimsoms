package me.ftahmed.bootify.repos;

import org.springframework.data.jpa.repository.JpaRepository;

import me.ftahmed.bootify.domain.Part;

public interface PartRepository extends JpaRepository<Part, Long> {
    
}
