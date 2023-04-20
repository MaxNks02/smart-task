package com.example.smartbank_task_webflux.dao.mapper;

import com.example.smartbank_task_webflux.dao.dto.CityDto;
import com.example.smartbank_task_webflux.dao.model.City;
import org.mapstruct.Mapper;
import org.mapstruct.NullValueCheckStrategy;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.springframework.stereotype.Component;

@Component
@Mapper(componentModel = "spring",
        nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS,
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface CityMapper extends BaseMapper<City, CityDto> {
}