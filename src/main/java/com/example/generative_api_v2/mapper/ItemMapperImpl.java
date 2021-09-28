package com.example.generative_api_v2.mapper;

import com.example.generative_api_v2.dto.ItemDTO;
import com.example.generative_api_v2.model.Item;
import org.springframework.stereotype.Component;

@Component
public final class ItemMapperImpl implements ItemMapper{
    private ItemMapperImpl() {
    }

    @Override
    public Item map(Item item,ItemDTO itemDTO) {
        item.setCurrency(itemDTO.getCurrency());
        item.setParent(itemDTO.getParent());
        item.setTitle(itemDTO.getTitle());
        item.setPrice(itemDTO.getPrice());
        return  item;
    }
}
