package com.example.generative_api_v2.controller;

import com.example.generative_api_v2.dto.ItemDTO;
import com.example.generative_api_v2.model.Item;
import com.example.generative_api_v2.service.ItemService;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/items")
public class ItemController {

    private final ItemService itemService;

    @Autowired
    public ItemController(ItemService itemService) {
        this.itemService = itemService;
    }

    @GetMapping
    public List<Item> getAll() {
        return itemService.getAll();
    }

    @PostMapping
    public Item add(@RequestBody ItemDTO sendedItem) throws JsonProcessingException {
        return itemService.save(sendedItem);
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
    public Item updateByyId(@PathVariable int id, @RequestBody ItemDTO updatedItem) {
        return itemService.updateById(id, updatedItem);
    }

    @GetMapping("/search")
    public List<Item> searchWithMinAndMaxPrices(@RequestParam int priceFrom, @RequestParam int priceTo) {
        return itemService.getItemsWithPriceFromTo(priceFrom, priceTo);
    }

}
