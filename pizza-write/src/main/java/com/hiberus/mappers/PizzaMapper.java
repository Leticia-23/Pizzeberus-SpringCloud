package com.hiberus.mappers;

import com.hiberus.dto.PizzaWDto;
import com.hiberus.models.Pizza;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface PizzaMapper {
    @Mapping(target = "id", ignore = true)
    Pizza dtoToModel(PizzaWDto pizza);
}
