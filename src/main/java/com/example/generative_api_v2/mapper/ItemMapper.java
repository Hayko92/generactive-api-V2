package com.example.generative_api_v2.mapper;

import com.example.generative_api_v2.dto.ItemDTO;
import com.example.generative_api_v2.model.Item;

public interface ItemMapper {
    Item map(Item item,ItemDTO itemDTO);
}