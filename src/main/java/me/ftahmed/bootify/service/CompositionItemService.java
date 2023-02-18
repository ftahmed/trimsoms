package me.ftahmed.bootify.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;
import me.ftahmed.bootify.domain.CompositionItem;
import me.ftahmed.bootify.repos.CompositionItemRepository;


@Transactional
@Service
public class CompositionItemService {

    @Autowired
    private CompositionItemRepository compositionItemRepository;

    public List<CompositionItem> findAll() {
        return compositionItemRepository.findAll(Sort.by("ciName"));
    }


}
