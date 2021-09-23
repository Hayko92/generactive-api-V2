package com.example.generative_api_v2.service;

import com.example.generative_api_v2.db.hibernate.GenerativeItemHibernateRepository;
import com.example.generative_api_v2.db.hibernate.StockItemHibernateRepository;
import com.example.generative_api_v2.model.Generative;
import com.example.generative_api_v2.model.Item;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class GeneractiveItemServiceImpl implements GenerativeItemService {

    private GenerativeItemHibernateRepository generativeItemHibernateRepository;

    @Autowired
    public GeneractiveItemServiceImpl(GenerativeItemHibernateRepository generativeItemHibernateRepository) {
        this.generativeItemHibernateRepository = generativeItemHibernateRepository;
    }

    @Override
    public void save(Item item) {
        generativeItemHibernateRepository.save((Generative) item);
    }

    @Override
    public List<Generative> getAll() {
        return generativeItemHibernateRepository.getAll();
    }

    @Override
    public void deleteById(int id) {
        generativeItemHibernateRepository.deleteById(id);
    }

    @Override
    public Generative getById(int id) {
        return generativeItemHibernateRepository.getById(id);
    }

    public void updateById(Generative item) {
        generativeItemHibernateRepository.updateById(item);
    }

    @Override
    public List<Generative> getItemsWithPriceFromTo(int from, int to) {
        return generativeItemHibernateRepository.getItemsWithPriceFromTo(from, to);
    }
}
