package com.example.generative_api_v2.controller;

import com.example.generative_api_v2.dto.GeneractiveDTO;
import com.example.generative_api_v2.dto.GroupDTO;
import com.example.generative_api_v2.mapper.GeneractiveItemMapper;
import com.example.generative_api_v2.mapper.GroupMapper;
import com.example.generative_api_v2.model.Generative;
import com.example.generative_api_v2.service.GenerativeItemService;
import com.example.generative_api_v2.service.GroupService;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/generative-items")
public class GenerativeItemController {

    private final GenerativeItemService generativeItemService;
    private final GroupService groupService;
    private final GeneractiveItemMapper generactiveItemMapper;
    private final GroupMapper groupMapper;

    @Autowired
    public GenerativeItemController(GenerativeItemService generativeItemService, GroupService groupService, GeneractiveItemMapper generactiveItemMapper, GroupMapper groupMapper) {
        this.generativeItemService = generativeItemService;
        this.groupService = groupService;
        this.generactiveItemMapper = generactiveItemMapper;
        this.groupMapper = groupMapper;
    }

    @GetMapping
    public List<Generative> getAll() {
        return generativeItemService.getAll();
    }

    @PostMapping
    public GeneractiveDTO add(@RequestBody GeneractiveDTO sendedItem) throws JsonProcessingException {
        return generativeItemService.save(sendedItem);
    }

    @DeleteMapping("/{id}")
    public void deleteById(@PathVariable int id) {
        generativeItemService.deleteById(id);
    }

    @GetMapping("/{id}")
    public GeneractiveDTO getById(@PathVariable int id) {
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

    @PutMapping("/{id}/{parentId}")
    public GeneractiveDTO updateByyId(@PathVariable int id, @PathVariable int parentId) {
        GeneractiveDTO generative = generativeItemService.getById(id);
        GroupDTO groupDTO = groupService.getById(parentId);
        generative.setParent(groupDTO);
        return generativeItemService.save(generative);
    }
}
