package com.example.generative_api_v2.service;

import com.example.generative_api_v2.model.Item;
import org.springframework.stereotype.Component;

import java.util.List;
@Component
public interface ItemService {
    void save(Item item);
    List<Item> getAll();
    void deleteById(int id);
    Item getById(int id);
    void updateById(Item item);
    List<Item> getItemsWithPriceFromTo(int from, int to);
}
