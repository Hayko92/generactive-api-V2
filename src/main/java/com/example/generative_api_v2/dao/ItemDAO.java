package com.example.generative_api_v2.dao;

import com.example.generative_api_v2.model.Item;

import java.util.List;

public interface ItemDAO {

    void add(Item item);

    List<Item> getAll();

    List<Item> getItemsWithPriceFromTo(int from, int to);

    Item getById(int id);

    void deleteById(int id);

    void updateById(Item item);

    Item getLastAdded();

}
