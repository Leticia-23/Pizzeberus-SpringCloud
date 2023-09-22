package com.hiberus.mappers;

import com.hiberus.dto.UserDto;
import com.hiberus.models.User;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserMapper {
    // UserDto modelToDto(User user);
    User dtoToUser(UserDto user);
}
