package com.example.generative_api_v2.service;

import com.example.generative_api_v2.dto.ItemDTO;
import com.example.generative_api_v2.model.Item;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public interface ItemService {

    Item save(ItemDTO itemDTO);

    List<Item> getAll();

    List<Item> getAll(int offset, int limit , String sortBy);

    void deleteById(int id);

    Item getById(int id);

    Item updateById(int id, ItemDTO itemDTO);

    List<Item> getItemsWithPriceFromTo(int from, int to);

}
