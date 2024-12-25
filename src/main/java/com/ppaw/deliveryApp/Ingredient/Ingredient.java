package com.ppaw.deliveryApp.Ingredient;

import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;
import java.util.UUID;

@Entity
@Table(name = "ingredient")
@ToString
@Builder(toBuilder = true)
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Ingredient implements Serializable {

    @Id
    @GeneratedValue(generator = "UUID")
    @Column(name = "id", nullable = false)
    private UUID id;

    @Column(name = "name")
    private String name;

}
