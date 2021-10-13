package com.example.generative_api_v2.mapper;

import com.example.generative_api_v2.dto.GeneractiveDTO;
import com.example.generative_api_v2.model.Generative;
import com.example.generative_api_v2.model.Group;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public final class GeneractiveItemMapperImpl implements GeneractiveItemMapper {
    private GroupMapper groupMapper;

    @Autowired
    public GeneractiveItemMapperImpl(GroupMapper groupMapper) {
        this.groupMapper = groupMapper;
    }

    private GeneractiveItemMapperImpl() {
    }

    @Override
    public Generative mapToGeneractive(GeneractiveDTO generactiveDTO, Generative generative) {
        generative.setId(generactiveDTO.getId());
        if (generactiveDTO.getCurrency() != null) generative.setCurrency(generactiveDTO.getCurrency());
        if (generactiveDTO.getParent() != null)
            generative.setParent(groupMapper.mapToGroup(generactiveDTO.getParent(), new Group()));
        if (generactiveDTO.getTitle() != null) generative.setTitle(generactiveDTO.getTitle());
        if (generactiveDTO.getPrice() != 0) generative.setPrice(generactiveDTO.getPrice());
        generative.setComplexity(generactiveDTO.getComplexity());
        if (generactiveDTO.getUpdatedAt() != null) generative.setUpdatedAt(generactiveDTO.getUpdatedAt());
        if (generactiveDTO.getCreatedAt() != null) generative.setCreatedAt(generactiveDTO.getCreatedAt());
        if (generactiveDTO.getCreatedBy() != null) generative.setCreatedBy(generactiveDTO.getCreatedBy());
        return generative;
    }

    @Override
    public GeneractiveDTO mapToGeneractiveDTO(Generative generative) {
        GeneractiveDTO generactiveDTO = new GeneractiveDTO();
        generactiveDTO.setId(generative.getId());
        generactiveDTO.setTitle(generative.getTitle());
        generactiveDTO.setCurrency(generative.getCurrency());
        generactiveDTO.setImage_url(generative.getImage_url());
        if (generative.getParent() != null) generactiveDTO.setParentId(generative.getParent().getId());
        generactiveDTO.setComplexity(generative.getComplexity());
        generactiveDTO.setUpdatedAt(generative.getUpdatedAt());
        generactiveDTO.setCreatedAt(generative.getCreatedAt());
        generactiveDTO.setCreatedBy(generative.getCreatedBy());
        return generactiveDTO;
    }
}
