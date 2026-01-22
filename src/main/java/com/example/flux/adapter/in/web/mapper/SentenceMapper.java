package com.example.flux.adapter.in.web.mapper;

import com.example.flux.adapter.in.web.dto.SentenceResponse;
import com.example.flux.domain.model.Sentence;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface SentenceMapper {

    SentenceResponse toResponse(Sentence sentence);
}
