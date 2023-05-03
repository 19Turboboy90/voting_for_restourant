package ru.project.graduation.zharinov.votingforrestaurant.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.time.LocalDate;

@Entity
@NoArgsConstructor
@Setter
@Getter
@Table(name = "vote", uniqueConstraints = {@UniqueConstraint(columnNames = {"user_id", "date_vote"}, name = "vote_unique_user_date")})
public class Vote extends BaseEntity {
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "restaurant_id", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Restaurant restaurant;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private User user;

    @Column(name = "date_vote", nullable = false)
    @NotNull
    private LocalDate dateOfVoting;

    public Vote(Integer id, Restaurant restaurant, User user, LocalDate dateOfVoting) {
        super(id);
        this.restaurant = restaurant;
        this.user = user;
        this.dateOfVoting = dateOfVoting;
    }

    public Vote(Vote vote) {
        this(vote.getId(), vote.getRestaurant(), vote.user, vote.getDateOfVoting());
    }
}
