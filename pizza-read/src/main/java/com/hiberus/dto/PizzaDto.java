package com.hiberus.dto;

import lombok.*;
@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PizzaDto {
    private Integer id;
    private String nombre;
    private String talla;
    private String color;
    private Integer idUsuario;
}