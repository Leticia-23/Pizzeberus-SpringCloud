package com.hiberus.mappers;

import com.hiberus.dto.UserDto;
import com.hiberus.dto.UserInDto;
import com.hiberus.models.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface UserMapper {
    UserDto modelToDto(User user);
    @Mapping(target = "favPizzas", ignore = true)
    User dtoToUser(UserInDto user);
}
