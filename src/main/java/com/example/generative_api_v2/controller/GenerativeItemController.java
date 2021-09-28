package com.example.generative_api_v2.controller;

import com.example.generative_api_v2.dto.GeneractiveDTO;
import com.example.generative_api_v2.model.Generative;
import com.example.generative_api_v2.model.Item;
import com.example.generative_api_v2.service.GenerativeItemService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/generative-items")
public class GenerativeItemController {

    @Autowired
    private GenerativeItemService generativeItemService;
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Generative> getAll() {
        return generativeItemService.getAll();
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public Generative add(@RequestBody String sendedItem) throws JsonProcessingException {
        Generative item = new ObjectMapper().readValue(sendedItem, Generative.class);
        generativeItemService.save(item);
        return item;
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
    public Generative updateByyId(@PathVariable int id, @RequestBody String updatedItem) throws JsonProcessingException {
        Generative item = generativeItemService.getById(id);
        GeneractiveDTO itemDTO = new ObjectMapper().readValue(updatedItem, GeneractiveDTO.class);
        item.setCurrency(itemDTO.getCurrency());
        item.setImage_url(itemDTO.getImage_url());
        item.setParent(itemDTO.getParent());
        item.setPrice(itemDTO.getPrice());
        item.setTitle(itemDTO.getTitle());
        item.setComplexity(itemDTO.getComplexity());
        generativeItemService.updateById(item);
        return item;
    }

    @GetMapping("/search")
    public List<Generative> searchWithMinAndMaxPrices(@RequestParam int priceFrom, @RequestParam int priceTo) {
        return generativeItemService.getItemsWithPriceFromTo(priceFrom, priceTo);
    }
}
