package com.example.generative_api_v2.dao;

import com.example.generative_api_v2.model.Generative;
import java.util.List;

public interface GenerativeItemDAO {

    void add(Generative item);

    List<Generative> getAll();

    List<Generative> getItemsWithPriceFromTo(int from, int to);

    Generative getById(int id);

    void deleteById(int id);

    void updateById(Generative item);

    Generative getLastAdded();

}
