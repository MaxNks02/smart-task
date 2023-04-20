package com.example.smartbank_task_webflux.dao.mapper;

import com.example.smartbank_task_webflux.dao.dto.BaseDto;
import com.example.smartbank_task_webflux.dao.model.BaseModel;

import java.util.List;


public interface BaseMapper<E extends BaseModel, D extends BaseDto> {

    D toDto(E entity);

    E toEntity(D dto);

    List<D> convertFromEntityList(List<E> entityList);

    List<E> convertFromDtoList(List<D> dtoList);


}
