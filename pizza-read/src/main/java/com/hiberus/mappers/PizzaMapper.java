package com.hiberus.mappers;

import com.hiberus.dto.PizzaDto;
import com.hiberus.models.Pizza;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface PizzaMapper {
    PizzaDto modelToDto(Pizza pizza);
}