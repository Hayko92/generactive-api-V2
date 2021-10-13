package com.example.generative_api_v2.service;

import com.example.generative_api_v2.dto.ItemDTO;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public interface ItemService {

    ItemDTO save(ItemDTO itemDTO);

    List<ItemDTO> getAll(int offset, int limit, String sortBy);

    void deleteById(int id);

    ItemDTO getById(int id);

    ItemDTO updateById(int id, ItemDTO itemDTO);

    List<ItemDTO> getItemsWithPriceFromTo(int from, int to);

    ItemDTO setCreatingDate(ItemDTO itemDTO);

    ItemDTO setUpdatingDate(ItemDTO itemDTO);

    ItemDTO setCreatingUserName(ItemDTO itemDTO);

}
