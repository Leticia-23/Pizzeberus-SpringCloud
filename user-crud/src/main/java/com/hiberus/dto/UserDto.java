package com.hiberus.dto;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Data
@Getter
@Setter
public class UserDto {
    private String dni;
    private String name;
    private List<Integer> favPizzas;
}
