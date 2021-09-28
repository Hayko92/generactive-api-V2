package com.example.generative_api_v2.controller;

import com.example.generative_api_v2.dto.ItemDTO;
import com.example.generative_api_v2.model.Item;
import com.example.generative_api_v2.service.ItemService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/items")
public class ItemController {
    @Autowired
    private ItemService itemService;

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Item> getAll() {
        return itemService.getAll();
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public Item add(@RequestBody String sendedItem) throws JsonProcessingException {
        Item item = new ObjectMapper().readValue(sendedItem, Item.class);
        itemService.save(item);
        return item;
    }

    @DeleteMapping("/{id}")
    public void deleteById(@PathVariable int id) {
        itemService.deleteById(id);
    }

    @GetMapping("/{id}")
    public Item getById(@PathVariable int id) {
        return itemService.getById(id);
    }

    @PutMapping("/{id}")
    public Item updateByyId(@PathVariable int id, @RequestBody String updatedItem) throws JsonProcessingException {
        Item item = itemService.getById(id);
        ItemDTO itemDTO = new ObjectMapper().readValue(updatedItem, ItemDTO.class);
        item.setCurrency(itemDTO.getCurrency());
        item.setImage_url(itemDTO.getImage_url());
        item.setParent(itemDTO.getParent());
        item.setPrice(itemDTO.getPrice());
        item.setTitle(itemDTO.getTitle());
        itemService.updateById(item);
        return item;
    }

    @GetMapping("/search")
    public List<Item> searchWithMinAndMaxPrices(@RequestParam int priceFrom, @RequestParam int priceTo) {
        return itemService.getItemsWithPriceFromTo(priceFrom, priceTo);
    }

}
