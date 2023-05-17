package ru.project.graduation.zharinov.votingforrestaurant.model;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "menu", indexes = {@Index(name = "idx_menu_restaurant_id", columnList = "restaurant_id")})
public class Menu extends BaseEntity {
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "restaurant_id", nullable = false)
    private Restaurant restaurant;

    @OnDelete(action = OnDeleteAction.CASCADE)
    @Schema(hidden = true)
    @OneToMany(mappedBy = "menu", cascade = CascadeType.ALL)
    private List<Dish> dishes;
}
