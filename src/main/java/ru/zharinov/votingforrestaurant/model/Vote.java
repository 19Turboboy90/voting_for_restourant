package ru.zharinov.votingforrestaurant.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "vote",
        indexes = {
                @Index(name = "idx_vote_user_id", columnList = "user_id"),
                @Index(name = "idx_vote_restaurant_id", columnList = "restaurant_id")
        })
public class Vote extends BaseEntity {
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "restaurant_id", nullable = false)
    private Restaurant restaurant;

    @NotNull
    @Column(name = "vote_date", nullable = false)
    private LocalDate voteDate;
}