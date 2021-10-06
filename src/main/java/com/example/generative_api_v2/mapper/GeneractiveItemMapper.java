package com.example.generative_api_v2.mapper;

import com.example.generative_api_v2.dto.GeneractiveDTO;
import com.example.generative_api_v2.model.Generative;

public interface GeneractiveItemMapper {
    Generative mapToGeneractive(GeneractiveDTO generactiveDTO);
    GeneractiveDTO mapToGeneractiveDTO(Generative generative);
}
