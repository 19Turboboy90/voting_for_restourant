package ru.project.graduation.zharinov.votingforrestaurant.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@Entity
@NoArgsConstructor
@Table(name = "dish", uniqueConstraints = {@UniqueConstraint(columnNames = {"restaurant_id","dish_date","name"}, name = "dish_unique_restaurant_name_dish_date")})
public class Dish extends NamedEntity {
    @Column(name = "price", nullable = false)
    private double price;

    @Column(name = "dish_date", nullable = false)
    private LocalDate dishDate;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "restaurant_id", nullable = false)
    @JsonIgnore
    private Restaurant restaurant;

    public Dish(Integer id, String name, Double price, LocalDate dishDate) {
        super(id, name);
        this.price = price;
        this.dishDate = dishDate;
    }

    public Dish(Dish dish) {
        this(dish.id, dish.name, dish.price, dish.dishDate);
    }
}
