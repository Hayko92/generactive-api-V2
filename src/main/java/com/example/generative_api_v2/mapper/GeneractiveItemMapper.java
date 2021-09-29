package com.example.generative_api_v2.mapper;

import com.example.generative_api_v2.dto.GeneractiveDTO;
import com.example.generative_api_v2.model.Generative;
import com.example.generative_api_v2.model.Item;

public interface GeneractiveItemMapper {
    Item map(Generative generative, GeneractiveDTO generactiveDTO);
}
