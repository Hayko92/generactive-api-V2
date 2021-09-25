package com.example.generative_api_v2.service;

import com.example.generative_api_v2.model.Generative;
import com.example.generative_api_v2.model.Item;
import org.springframework.stereotype.Component;

import java.util.List;
@Component
public interface GenerativeItemService {
    void save(Item item);
    List<Generative> getAll();
    void deleteById(int id);
    Generative getById(int id);
    List<Generative> getItemsWithPriceFromTo(int from, int to);
}
