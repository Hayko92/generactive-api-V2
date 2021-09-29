package com.example.generative_api_v2.mapper;

import com.example.generative_api_v2.dto.GeneractiveDTO;
import com.example.generative_api_v2.model.Generative;
import com.example.generative_api_v2.model.Item;
import org.springframework.stereotype.Component;

@Component
public final class GeneractiveItemMapperImpl implements GeneractiveItemMapper{

    private GeneractiveItemMapperImpl() {
    }

    @Override
    public Item map(Generative generative, GeneractiveDTO generactiveDTO) {
        generative.setCurrency(generactiveDTO.getCurrency());
        generative.setParent(generactiveDTO.getParent());
        generative.setTitle(generactiveDTO.getTitle());
        generative.setPrice(generactiveDTO.getPrice());
        generative.setComplexity(generactiveDTO.getComplexity());
        return  generative;
    }
}
