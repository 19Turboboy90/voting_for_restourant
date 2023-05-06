package ru.project.graduation.zharinov.votingforrestaurant.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;
import ru.project.graduation.zharinov.votingforrestaurant.model.Restaurant;

import java.util.List;
import java.util.Optional;

@Transactional(readOnly = true)
public interface RestaurantRepository extends BaseRepository<Restaurant> {
    @Query("SELECT r FROM Restaurant r JOIN FETCH r.menu m WHERE r.id = :restaurantId AND r.id = m.restaurant.id")
    Optional<Restaurant> getRestaurantWithMenu(int restaurantId);

    @Query("SELECT r FROM Restaurant r JOIN FETCH r.menu m WHERE r.id = m.restaurant.id")
    List<Restaurant> getAllRestaurantsWithMenu();
}
