package ru.project.graduation.zharinov.votingforrestaurant.to;

import jakarta.validation.constraints.NotNull;
import lombok.EqualsAndHashCode;
import lombok.Value;

import java.time.LocalDate;

@Value
@EqualsAndHashCode(callSuper = true)
public class VoteTo extends BaseTo {
    @NotNull
    LocalDate dateOfVoting;

    @NotNull
    Integer restaurantId;

    public VoteTo(Integer id, LocalDate dateOfVoting, Integer restaurantId) {
        super(id);
        this.dateOfVoting = dateOfVoting;
        this.restaurantId = restaurantId;
    }
}
