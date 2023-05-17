package ru.project.graduation.zharinov.votingforrestaurant.repository;

import org.springframework.transaction.annotation.Transactional;
import ru.project.graduation.zharinov.votingforrestaurant.model.Restaurant;


@Transactional(readOnly = true)
public interface RestaurantRepository extends BaseRepository<Restaurant> {

}
