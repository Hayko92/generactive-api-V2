package com.example.generative_api_v2.mapper;

import com.example.generative_api_v2.dto.ItemDTO;
import com.example.generative_api_v2.model.Group;
import com.example.generative_api_v2.model.Item;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public final class ItemMapperImpl implements ItemMapper{
    private   GroupMapper groupMapper;
    private ItemMapperImpl() {
    }

    @Autowired
    public ItemMapperImpl(GroupMapper groupMapper) {
        this.groupMapper = groupMapper;
    }

    @Override
    public Item mapToItem(  ItemDTO itemDTO, Item item) {

        item.setId(itemDTO.getId());
        item.setCurrency(itemDTO.getCurrency());
       if(itemDTO.getParent()!=null) item.setParent(groupMapper.mapToGroup(itemDTO.getParent(), new Group()));
        item.setTitle(itemDTO.getTitle());
        item.setPrice(itemDTO.getPrice());
        item.setImage_url(itemDTO.getImage_url());
        if (itemDTO.getUpdatedAt() != null) item.setUpdatedAt(itemDTO.getUpdatedAt());
        if (itemDTO.getCreatedAt() != null) item.setCreatedAt(itemDTO.getCreatedAt());
        if (itemDTO.getCreatedBy() != null) item.setCreatedBy(itemDTO.getCreatedBy());
        return  item;
    }

    @Override
    public ItemDTO mapToItemDTO(Item item) {
        ItemDTO itemDTO = new ItemDTO();
        itemDTO.setId(item.getId());
        if(item.getParent()!=null)  itemDTO.setParentId(item.getParent().getId());
        itemDTO.setCurrency(item.getCurrency());
        itemDTO.setPrice(item.getPrice());
        itemDTO.setImage_url(item.getImage_url());
        itemDTO.setTitle(item.getTitle());
        itemDTO.setUpdatedAt(item.getUpdatedAt());
        itemDTO.setCreatedAt(item.getCreatedAt());
        itemDTO.setCreatedBy(item.getCreatedBy());
        return itemDTO;

    }
}
