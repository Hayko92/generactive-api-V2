package com.example.generative_api_v2.controller;

import com.example.generative_api_v2.dto.GeneractiveDTO;
import com.example.generative_api_v2.model.Generative;
import com.example.generative_api_v2.model.Item;
import com.example.generative_api_v2.service.GenerativeItemService;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/generative-items")
public class GenerativeItemController {


    private final GenerativeItemService generativeItemService;

    @Autowired
    public GenerativeItemController(GenerativeItemService generativeItemService) {
        this.generativeItemService = generativeItemService;
    }

    @GetMapping
    public List<Generative> getAll() {
        return generativeItemService.getAll();
    }

    @PostMapping
    public Generative add(@RequestBody GeneractiveDTO sendedItem) throws JsonProcessingException {
        return generativeItemService.save(sendedItem);
    }

    @DeleteMapping("/{id}")
    public void deleteById(@PathVariable int id) {
        generativeItemService.deleteById(id);
    }

    @GetMapping("/{id}")
    public Item getById(@PathVariable int id) {
        return generativeItemService.getById(id);
    }

    @PutMapping("/{id}")
    public Generative updateByyId(@PathVariable int id, @RequestBody GeneractiveDTO updatedItem) {
        return generativeItemService.updateById(id, updatedItem);
    }

    @GetMapping("/search")
    public List<Generative> searchWithMinAndMaxPrices(@RequestParam int priceFrom, @RequestParam int priceTo) {
        return generativeItemService.getItemsWithPriceFromTo(priceFrom, priceTo);
    }
}
