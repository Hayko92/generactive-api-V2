package com.example.generative_api_v2.service;

import com.example.generative_api_v2.db.hibernate.StockItemHibernateRepository;
import com.example.generative_api_v2.model.Item;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class StockItemServiceImpl implements ItemService {

    private   StockItemHibernateRepository stockItemHibernateRepository;

    @Autowired
    public StockItemServiceImpl(StockItemHibernateRepository stockItemHibernateRepository) {
        this.stockItemHibernateRepository = stockItemHibernateRepository;
    }

    @Override
    public void save(Item item) {
        stockItemHibernateRepository.save(item);
    }

    @Override
    public List<Item> getAll() {
      return   stockItemHibernateRepository.getAll();
    }

    @Override
    public void deleteById(int id) {
        stockItemHibernateRepository.deleteById(id);
    }

    @Override
    public Item getById(int id) {
    return     stockItemHibernateRepository.getById(id);
    }

    @Override
    public void updateById(Item item) {
        stockItemHibernateRepository.updateById(item);
    }

    @Override
    public List<Item> getItemsWithPriceFromTo(int from, int to) {
     return stockItemHibernateRepository.getItemsWithPriceFromTo(from, to);
    }
}
