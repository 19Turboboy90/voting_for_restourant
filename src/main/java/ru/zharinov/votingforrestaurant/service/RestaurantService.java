package ru.zharinov.votingforrestaurant.service;


import ru.zharinov.votingforrestaurant.model.Restaurant;

import java.util.List;

public interface RestaurantService {
    Restaurant create(Restaurant restaurant);

    Restaurant update(Restaurant restaurant, int id);

    void delete(int id);

    Restaurant get(int id);

    List<Restaurant> getAll();
}