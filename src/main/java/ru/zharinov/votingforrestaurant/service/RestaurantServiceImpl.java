package ru.zharinov.votingforrestaurant.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.zharinov.votingforrestaurant.model.Restaurant;
import ru.zharinov.votingforrestaurant.repository.RestaurantRepository;
import ru.zharinov.votingforrestaurant.util.validation.ValidationUtil;

import java.util.List;

@Service
@Transactional
@Slf4j
public class RestaurantServiceImpl implements RestaurantService {
    private final RestaurantRepository restaurantRepository;

    public RestaurantServiceImpl(RestaurantRepository restaurantRepository) {
        this.restaurantRepository = restaurantRepository;
    }

    @Override
    public Restaurant create(Restaurant restaurant) {
        log.info("create restaurant ={}", restaurant);
        ValidationUtil.checkNew(restaurant);
        return restaurantRepository.save(restaurant);
    }

    @Override
    public Restaurant update(Restaurant restaurant, int id) {
        log.info("update restaurant = {}, id = {}", restaurant, id);
        ValidationUtil.assureIdConsistent(restaurant, restaurant.id());
        ValidationUtil.checkNotFound(restaurantRepository.existsById(id),
                "Restaurant with id=" + id + " not found");
        return restaurantRepository.save(restaurant);
    }

    @Override
    public void delete(int id) {
        log.info("delete restaurant by id = {}", id);
        restaurantRepository.deleteExisted(id);
    }

    @Override
    public Restaurant get(int id) {
        log.info("get restaurant by id = {}", id);
        return restaurantRepository.getExisted(id);
    }

    @Override
    public List<Restaurant> getAll() {
        log.info("get all restaurants");
        return restaurantRepository.findAll();
    }
}