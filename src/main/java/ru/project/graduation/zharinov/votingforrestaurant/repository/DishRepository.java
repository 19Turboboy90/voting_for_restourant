package ru.project.graduation.zharinov.votingforrestaurant.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;
import ru.project.graduation.zharinov.votingforrestaurant.model.Dish;

import java.util.List;

@Transactional(readOnly = true)
public interface DishRepository extends BaseRepository<Dish> {
    @Query("SELECT d FROM Dish d JOIN d.menu m WHERE d.id = m.id AND m.id = :menuId")
    List<Dish> getAllDishesForMenu(@Param("menuId") Integer menuId);
}
