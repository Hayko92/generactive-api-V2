package com.example.generative_api_v2.service;

import com.example.generative_api_v2.db.hibernate.StockItemHibernateRepository;
import com.example.generative_api_v2.dto.ItemDTO;
import com.example.generative_api_v2.mapper.ItemMapper;
import com.example.generative_api_v2.model.Item;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class StockItemServiceImpl implements ItemService {

    private StockItemHibernateRepository stockItemHibernateRepository;
    private ItemMapper itemMapper;

    public StockItemServiceImpl() {
    }

    @Autowired
    public StockItemServiceImpl(StockItemHibernateRepository stockItemHibernateRepository, ItemMapper itemMapper) {
        this.stockItemHibernateRepository = stockItemHibernateRepository;
        this.itemMapper = itemMapper;
    }

    @Override
    public Item save(ItemDTO itemDTO) {
        Item item = new Item();
        item = itemMapper.map(item, itemDTO);
        return stockItemHibernateRepository.save(item);
    }

    @Override
    public List<Item> getAll() {
        return stockItemHibernateRepository.getAll();
    }

    @Override
    public void deleteById(int id) {
        stockItemHibernateRepository.deleteById(id);
    }

    @Override
    public Item getById(int id) {
        return stockItemHibernateRepository.getById(id);
    }

    @Override
    public Item updateById(int id, ItemDTO itemDTO) {
        Item item = stockItemHibernateRepository.getById(id);
        item = itemMapper.map(item, itemDTO);
        return stockItemHibernateRepository.updateById(item);
    }

    @Override
    public List<Item> getItemsWithPriceFromTo(int from, int to) {
        return stockItemHibernateRepository.getItemsWithPriceFromTo(from, to);
    }

}
