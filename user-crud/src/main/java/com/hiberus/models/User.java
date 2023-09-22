package com.hiberus.models;

import lombok.*;
import javax.persistence.*;
import java.util.List;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "clients")
@Entity
@Getter
public class User {

    @Id
    @Column(name = "dni")
    private String dni;
    @Setter
    @Column(name = "name")
    private String name;
    @Setter
    @Column(name = "favPizzas")
    @ElementCollection  // Declare and persist the list
    private List<Integer> favPizzas;
}