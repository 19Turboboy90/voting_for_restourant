package ru.project.graduation.zharinov.votingforrestaurant.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;
import ru.project.graduation.zharinov.votingforrestaurant.error.DataConflictException;
import ru.project.graduation.zharinov.votingforrestaurant.model.Dish;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Transactional(readOnly = true)
public interface DishRepository extends BaseRepository<Dish> {
    @Query("SELECT d FROM Dish d WHERE d.restaurant.id = :restaurantId ORDER BY  d.dishDate DESC, d.name")
    List<Dish> getAll(int restaurantId);

    @Query("SELECT d FROM Dish d WHERE d.id = :id and d.restaurant.id = :restaurantId")
    Optional<Dish> get(int id, int restaurantId);

    @Query("SELECT d FROM Dish d " +
            "WHERE d.restaurant.id= :restaurantId " +
            "AND (:dishDate IS NULL OR d.dishDate=:dishDate) " +
            "ORDER BY d.name")
    List<Dish> getByDishDate(int restaurantId, LocalDate dishDate);

    default Dish checkBelong(int id, int restaurantId) {
        return get(id, restaurantId).orElseThrow(
                () -> new DataConflictException("Dish id=" + id + " is not found or doesn't belong to Restaurant id=" + restaurantId));
    }
}
