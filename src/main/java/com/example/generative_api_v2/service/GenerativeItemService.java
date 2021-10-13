package com.example.generative_api_v2.service;

import com.example.generative_api_v2.dto.GeneractiveDTO;
import com.example.generative_api_v2.dto.ItemDTO;
import com.example.generative_api_v2.model.Generative;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public interface GenerativeItemService {

    GeneractiveDTO save(GeneractiveDTO item);

    List<Generative> getAll();

    void deleteById(int id);

    GeneractiveDTO getById(int id);

    List<Generative> getItemsWithPriceFromTo(int from, int to);

    Generative updateById(int id, GeneractiveDTO generativeDTO);

    GeneractiveDTO setCreatingDate(GeneractiveDTO itemDTO);

    GeneractiveDTO setUpdatingDate(GeneractiveDTO itemDTO);

    GeneractiveDTO setCreatingUserName(GeneractiveDTO itemDTO);

}
