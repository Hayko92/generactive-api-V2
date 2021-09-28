package com.example.generative_api_v2.service;

import com.example.generative_api_v2.db.hibernate.GenerativeItemHibernateRepository;
import com.example.generative_api_v2.dto.GeneractiveDTO;
import com.example.generative_api_v2.mapper.GeneractiveItemMapper;
import com.example.generative_api_v2.model.Generative;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class GeneractiveItemServiceImpl implements GenerativeItemService {

    private GenerativeItemHibernateRepository generativeItemHibernateRepository;
    private GeneractiveItemMapper generactiveItemMapper;

    public GeneractiveItemServiceImpl() {
    }

    @Autowired
    public GeneractiveItemServiceImpl(GenerativeItemHibernateRepository generativeItemHibernateRepository, GeneractiveItemMapper generactiveItemMapper) {
        this.generativeItemHibernateRepository = generativeItemHibernateRepository;
        this.generactiveItemMapper = generactiveItemMapper;
    }

    @Override
    public Generative save(GeneractiveDTO generactiveDTO) {
        Generative generative = new Generative();
        generactiveItemMapper.map(generative, generactiveDTO);
        return generativeItemHibernateRepository.save(generative);
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

    public Generative updateById(int id, GeneractiveDTO generactiveDTO) {
        Generative generative = generativeItemHibernateRepository.getById(id);

        return generativeItemHibernateRepository.updateById(generative);
    }

    @Override
    public List<Generative> getItemsWithPriceFromTo(int from, int to) {
        return generativeItemHibernateRepository.getItemsWithPriceFromTo(from, to);
    }
}
