package ru.zharinov.votingforrestaurant.repository;

import org.springframework.transaction.annotation.Transactional;
import ru.zharinov.votingforrestaurant.model.Restaurant;


@Transactional(readOnly = true)
public interface RestaurantRepository extends BaseRepository<Restaurant> {

}