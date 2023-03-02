package me.ftahmed.bootify.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;
import me.ftahmed.bootify.domain.Ticket;
import me.ftahmed.bootify.repos.TicketRepository;


@Transactional
@Service
public class TicketService {

    @Autowired
    private TicketRepository ticketRepository;

    public List<Ticket> findAll() {
        return ticketRepository.findAll(Sort.by("TicketName"));
    }

    public Optional<Ticket> findByCode(String ticketCode) {
        return ticketRepository.findByCode(ticketCode);
    }
}
