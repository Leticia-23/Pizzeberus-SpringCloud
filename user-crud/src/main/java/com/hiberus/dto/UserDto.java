package com.hiberus.dto;

import lombok.Data;
import java.util.List;

@Data
public class UserDto {
    private String dni;
    private String name;
    private List<Integer> favPizzas;
}
