package me.ftahmed.bootify.repos;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import me.ftahmed.bootify.domain.Ticket;

public interface TicketRepository extends JpaRepository<Ticket, Long> {
    
    Optional<Ticket> findByCode(String code);
}
