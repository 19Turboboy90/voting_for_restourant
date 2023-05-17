package ru.project.graduation.zharinov.votingforrestaurant.web.restaurant;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import ru.project.graduation.zharinov.votingforrestaurant.model.Restaurant;
import ru.project.graduation.zharinov.votingforrestaurant.service.RestaurantServiceImpl;


@RestController
@Slf4j
@RequestMapping(value = RestaurantController.REST_URL, produces = MediaType.APPLICATION_JSON_VALUE)
@PreAuthorize("hasRole('ROLE_ADMIN')")
public class RestaurantController {
    public static final String REST_URL = "/api/restaurants";

    private final RestaurantServiceImpl restaurantService;

    @Autowired
    public RestaurantController(RestaurantServiceImpl restaurantService) {
        this.restaurantService = restaurantService;
    }

    @GetMapping
    public ResponseEntity<Iterable<Restaurant>> getAll() {
        log.info("Get all restaurants");
        return new ResponseEntity<>(restaurantService.getAll(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Restaurant> getRestaurantById(@PathVariable("id") Integer id) {
        log.info("Get restaurant by id: {}", id);
        Restaurant restaurant = restaurantService.get(id);
        if (restaurant != null) {
            return new ResponseEntity<>(restaurant, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("/restaurants")
    public ResponseEntity<Restaurant> createRestaurant(@RequestBody Restaurant restaurant) {
        log.info("Create restaurant: {}", restaurant);
        return new ResponseEntity<>(restaurantService.create(restaurant), HttpStatus.CREATED);
    }


    @DeleteMapping(path = "/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable int id) {
        log.info("Delete restaurant with id: {}", id);
        restaurantService.delete(id);
    }

    @PutMapping("/restaurants/{id}")
    public ResponseEntity<Restaurant> updateRestaurant(@PathVariable("id") Integer id, @RequestBody Restaurant restaurant) {
        log.info("Update restaurant with id: {}, {}", id, restaurant);
        Restaurant updatedRestaurant = restaurantService.update(restaurant, id);
        if (updatedRestaurant != null) {
            return new ResponseEntity<>(updatedRestaurant, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
