package com.hiberus.models;

import lombok.*;
import javax.persistence.*;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "usuarios")
@Entity
@Getter
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Integer id;
    @Setter
    @Column(name = "nombre")
    private String nombre;
    @Setter
    @Column(name = "apellidos")
    private String apellidos;
    @Setter
    @Column(name = "email")
    private String email;

}