package me.ftahmed.bootify.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;
import me.ftahmed.bootify.domain.Part;
import me.ftahmed.bootify.repos.PartRepository;


@Transactional
@Service
public class PartService {

    @Autowired
    private PartRepository partRepository;

    public List<Part> findAll() {
        return partRepository.findAll(Sort.by("partName"));
    }


}
