package com.ppaw.deliveryApp.Menu;

import com.ppaw.deliveryApp.Configuration.Audit;
import com.ppaw.deliveryApp.Ingredient.Ingredient;
import com.ppaw.deliveryApp.OrderClient.OrderClient;
import com.ppaw.deliveryApp.User.Users;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.Where;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "menu")
@ToString
@Builder(toBuilder = true)
@AllArgsConstructor
@NoArgsConstructor
@Where(clause = "is_deleted = false")
@Data
public class Menu extends Audit implements Serializable {

    @Id
    @GeneratedValue(generator = "UUID")
    @Column(name = "id", nullable = false)
    private UUID id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "restaurant_id", nullable = false)
    private Users restaurant;

    @Column(name = "name")
    private String name;

    @Column(name = "photo")
    private String photo;

    @Column(name = "price")
    private Float price;

    @Column(name = "is_available")
    private Boolean isAvailable;


    @ManyToMany
    @JoinTable(
            name = "menu_ingredient",
            joinColumns = @JoinColumn(name = "menu_id"),
            inverseJoinColumns = @JoinColumn(name = "ingredient_id")
    )
    private List<Ingredient> ingredients = new ArrayList<>();
   }


