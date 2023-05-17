package ru.zharinov.votingforrestaurant.service;


import ru.zharinov.votingforrestaurant.model.Dish;

import java.util.List;

public interface DishService {
    Dish create(Dish dish);

    Dish update(Dish dish, int id);

    void delete(int id);

    Dish get(int id);

    List<Dish> getAll();
}