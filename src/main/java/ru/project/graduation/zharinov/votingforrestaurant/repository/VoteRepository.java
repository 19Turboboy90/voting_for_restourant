package ru.zharinov.votingforrestaurant.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;
import ru.zharinov.votingforrestaurant.model.Vote;

import java.util.List;


@Transactional(readOnly = true)
public interface VoteRepository extends BaseRepository<Vote> {
    @Query("SELECT v FROM Vote v WHERE v.user.id = :userId")
    List<Vote> getAllVotesForUser(@Param("userId") Integer userId);

    @Query("SELECT v FROM Vote v WHERE v.user.id = :userId AND v.restaurant.id = :restaurantId")
    List<Vote> getAllVotesForUserAndRestaurant(@Param("userId") Long userId, @Param("restaurantId") Long restaurantId);
}