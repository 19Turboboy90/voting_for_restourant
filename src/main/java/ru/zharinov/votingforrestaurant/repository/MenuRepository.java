package ru.zharinov.votingforrestaurant.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;
import ru.zharinov.votingforrestaurant.model.Menu;

import java.util.List;

@Transactional(readOnly = true)
public interface MenuRepository extends BaseRepository<Menu> {
    @Query("SELECT m FROM Menu m JOIN m.dishes d WHERE m.restaurant.id = :restaurantId")
    List<Menu> getAllMenuForRestaurant(@Param("restaurantId") Integer restaurantId);
}