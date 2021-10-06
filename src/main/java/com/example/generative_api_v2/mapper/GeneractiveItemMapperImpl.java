package com.example.generative_api_v2.mapper;

import com.example.generative_api_v2.dto.GeneractiveDTO;
import com.example.generative_api_v2.model.Generative;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public final class  GeneractiveItemMapperImpl implements GeneractiveItemMapper {
    private GroupMapper groupMapper;

    @Autowired
    public GeneractiveItemMapperImpl(GroupMapper groupMapper) {
        this.groupMapper = groupMapper;
    }

    private GeneractiveItemMapperImpl() {
    }

    @Override
    public Generative mapToGeneractive(GeneractiveDTO generactiveDTO) {
        Generative generative = new Generative();
        generative.setId(generactiveDTO.getId());
        generative.setCurrency(generactiveDTO.getCurrency());
        if(generactiveDTO.getParent()!=null)  generative.setParent(groupMapper.mapToGroup(generactiveDTO.getParent()));
        generative.setTitle(generactiveDTO.getTitle());
        generative.setPrice(generactiveDTO.getPrice());
        generative.setComplexity(generactiveDTO.getComplexity());
        return generative;
    }

    @Override
    public GeneractiveDTO mapToGeneractiveDTO(Generative generative) {
        GeneractiveDTO generactiveDTO = new GeneractiveDTO();
        generactiveDTO.setId(generative.getId());
        generactiveDTO.setTitle(generative.getTitle());
        generactiveDTO.setCurrency(generative.getCurrency());
        generactiveDTO.setImage_url(generative.getImage_url());
        if(generative.getParent()!=null)  generactiveDTO.setParentId(generative.getParent().getId());
        generactiveDTO.setComplexity(generactiveDTO.getComplexity());
        return generactiveDTO;
    }
}
