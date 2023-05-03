package ru.project.graduation.zharinov.votingforrestaurant.service;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.project.graduation.zharinov.votingforrestaurant.model.Dish;
import ru.project.graduation.zharinov.votingforrestaurant.repository.DishRepository;
import ru.project.graduation.zharinov.votingforrestaurant.repository.RestaurantRepository;

@Service
@AllArgsConstructor
@Slf4j
public class DishService {
    private final DishRepository dishRepository;
    private final RestaurantRepository restaurantRepository;

    @Transactional
    @CacheEvict(value = "restaurantsWithDish", allEntries = true)
    public Dish save(Dish dish, int restaurantId) {
        log.info("Save dish = {}, restaurantId = {}", dish, restaurantId);
        dish.setRestaurant(restaurantRepository.getReferenceById(restaurantId));
        return dishRepository.save(dish);
    }
}
