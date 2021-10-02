package com.example.generative_api_v2.service;

import com.example.generative_api_v2.db.jpaRepositories.ItemRepository;
import com.example.generative_api_v2.dto.ItemDTO;
import com.example.generative_api_v2.mapper.ItemMapper;
import com.example.generative_api_v2.model.Item;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.swing.text.html.parser.Entity;
import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Component
public class StockItemServiceImpl implements ItemService {

    @PersistenceContext
    private EntityManager entityManager;
    private ItemRepository itemRepository;
    private ItemMapper itemMapper;

    public StockItemServiceImpl() {
    }

    @Autowired
    public StockItemServiceImpl(ItemRepository itemRepository, ItemMapper itemMapper) {
        this.itemRepository = itemRepository;
        this.itemMapper = itemMapper;
    }

    @Transactional
    @Override
    public Item save(ItemDTO itemDTO) {
        Item item = new Item();
        item = itemMapper.map(item, itemDTO);
        return itemRepository.save(item);
    }

    @Transactional
    @Override
    public List<Item> getAll() {
        return itemRepository.findAll();
    }

    //this works but...
//    @Override
//    public List<Item> getAll(int offset, int limit) {
//        Pageable pageable = PageRequest.of(offset / limit, limit);
//        Page<Item> page = itemRepository.findAll(pageable);
//        return page.getContent();
//    }
    @Override
    public List<Item> getAll(int offset, int limit) {
        List<Item> items = entityManager.createQuery("select i FROM Item i", Item.class)
                .setFirstResult(offset)
                .setMaxResults(limit)
                .getResultList();
        return items;
    }

    @Transactional
    @Override
    public void deleteById(int id) {
        itemRepository.deleteById(id);
    }

    @Transactional
    @Override
    public Item getById(int id) {
        Optional<Item> finded = itemRepository.findById(id);
        return finded.orElse(null);
    }

    @Transactional
    @Override
    public Item updateById(int id, ItemDTO itemDTO) {
        Item item = itemRepository.findById(id).orElse(null);
        if (item != null) {
            item = itemMapper.map(item, itemDTO);
            return itemRepository.save(item);
        }
        return null;
    }

    @Transactional
    @Override
    public List<Item> getItemsWithPriceFromTo(int from, int to) {
        return itemRepository.findByPriceBetween(from, to);
    }


}
