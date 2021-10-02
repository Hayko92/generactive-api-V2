package com.example.generative_api_v2.service;

import com.example.generative_api_v2.db.hibernate.GenerativeItemHibernateRepository;
import com.example.generative_api_v2.db.jpaRepositories.GeneractiveItemrepository;
import com.example.generative_api_v2.dto.GeneractiveDTO;
import com.example.generative_api_v2.mapper.GeneractiveItemMapper;
import com.example.generative_api_v2.model.Generative;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;
import java.util.List;

@Component
public class GeneractiveItemServiceImpl implements GenerativeItemService {

    private GeneractiveItemrepository generativeJPARepository;
    private GeneractiveItemMapper generactiveItemMapper;

    public GeneractiveItemServiceImpl() {
    }

    @Autowired
    public GeneractiveItemServiceImpl(GeneractiveItemrepository generativeJPARepository, GeneractiveItemMapper generactiveItemMapper) {
        this.generativeJPARepository = generativeJPARepository;
        this.generactiveItemMapper = generactiveItemMapper;
    }

    @Transactional
    @Override
    public Generative save(GeneractiveDTO generactiveDTO) {
        Generative generative = new Generative();
        generactiveItemMapper.map(generative, generactiveDTO);
        return generativeJPARepository.save(generative);
    }

    @Transactional
    @Override
    public List<Generative> getAll() {
        return generativeJPARepository.findAll();
    }

    @Transactional
    @Override
    public void deleteById(int id) {
        generativeJPARepository.deleteById(id);
    }

    @Transactional
    @Override
    public Generative getById(int id) {
        return generativeJPARepository.findById(id);
    }

    @Transactional
    public Generative updateById(int id, GeneractiveDTO generactiveDTO) {
        Generative generative = generativeJPARepository.findById(id);

        return generativeJPARepository.save(generative);
    }

    @Transactional
    @Override
    public List<Generative> getItemsWithPriceFromTo(int from, int to) {
        return generativeJPARepository.findByPriceBetween(from, to);
    }
}
