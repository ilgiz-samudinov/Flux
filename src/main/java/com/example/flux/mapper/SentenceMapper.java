package com.example.flux.mapper;

import com.example.flux.dto.SentenceResponse;
import com.example.flux.model.Sentence;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface SentenceMapper {

    SentenceResponse toResponse(Sentence sentence);
}
