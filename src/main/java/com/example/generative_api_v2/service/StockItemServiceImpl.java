package com.example.generative_api_v2.service;

import com.example.generative_api_v2.db.jpaRepositories.ItemRepository;
import com.example.generative_api_v2.dto.ItemDTO;
import com.example.generative_api_v2.mapper.GeneractiveItemMapper;
import com.example.generative_api_v2.mapper.GroupMapper;
import com.example.generative_api_v2.mapper.ItemMapper;
import com.example.generative_api_v2.model.Item;
import com.example.generative_api_v2.pagination.CustomPageable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
public class StockItemServiceImpl implements ItemService {

    private ItemRepository itemRepository;
    private ItemMapper itemMapper;
    private GroupMapper groupMapper;
    private GeneractiveItemMapper generactiveItemMapper;

    public StockItemServiceImpl() {
    }

    @Autowired
    public StockItemServiceImpl(ItemRepository itemRepository, ItemMapper itemMapper, GroupMapper groupMapper, GeneractiveItemMapper generactiveItemMapper) {
        this.itemRepository = itemRepository;
        this.itemMapper = itemMapper;
        this.groupMapper = groupMapper;
        this.generactiveItemMapper = generactiveItemMapper;
    }

    @Transactional
    @Override
    public ItemDTO save(ItemDTO itemDTO) {
        Item item = itemMapper.mapToItem(itemDTO, new Item());
        item = itemRepository.save(item);
        return itemMapper.mapToItemDTO(item);
    }



    @Override
    public List<ItemDTO> getAll(int offset, int limit, String sortBy) {
        CustomPageable customPageable;
        Sort sort;
        if (sortBy != null) {
            sort = Sort.by(sortBy);
        } else sort = Sort.unsorted();
        customPageable = new CustomPageable(offset, limit, sort);
        Page<Item> items = itemRepository.findAll(customPageable);
        return items.getContent()
                .stream()
                .map(i->itemMapper.mapToItemDTO(i))
                .collect(Collectors.toList());
    }

    @Transactional
    @Override
    public void deleteById(int id) {
        itemRepository.deleteById(id);
    }

    @Override
    public ItemDTO getById(int id) {
        Optional<Item> finded = itemRepository.findById(id);
        return finded.map(item -> itemMapper.mapToItemDTO(item))
                .orElse(null);
    }

    @Transactional
    @Override
    public ItemDTO updateById(int id, ItemDTO itemDTO) {
        Item item = itemRepository.findById(id).orElse(null);
        if (item != null) {
            itemDTO.setId(id);
            item = itemMapper.mapToItem(itemDTO, item);
            item = itemRepository.save(item);
            return itemMapper.mapToItemDTO(item);
        }
        return null;
    }

    @Override
    public List<ItemDTO> getItemsWithPriceFromTo(int from, int to) {
        return itemRepository.findByPriceBetween(from, to)
                .stream()
                .map(i->itemMapper.mapToItemDTO(i))
                .collect(Collectors.toList());
    }

}
