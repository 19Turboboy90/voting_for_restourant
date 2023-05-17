package ru.project.graduation.zharinov.votingforrestaurant.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.hibernate.validator.constraints.Range;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "dish")
public class Dish extends NamedEntity {
    @Column(name = "price")
    private Double price;

    @Column(name = "calories", nullable = false)
    @NotNull
    @Range(min = 10, max = 5000)
    private Integer calories;

    @ManyToOne(fetch = FetchType.LAZY)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Menu menu;
}
