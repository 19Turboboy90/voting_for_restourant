package ru.project.graduation.zharinov.votingforrestaurant.web.restaurant;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Sort;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.project.graduation.zharinov.votingforrestaurant.model.Restaurant;
import ru.project.graduation.zharinov.votingforrestaurant.repository.RestaurantRepository;

import java.util.List;

@RestController
@Slf4j
@AllArgsConstructor
@RequestMapping(value = RestaurantController.URL, produces = MediaType.APPLICATION_JSON_VALUE)
public class RestaurantController {
    static final String URL = "/api/restaurants";

    private final RestaurantRepository repository;

    @GetMapping("/{id}")
    public ResponseEntity<Restaurant> get(@PathVariable int id) {
        log.info("get restaurant {}", id);
        return ResponseEntity.of(repository.findById(id));
    }

    @GetMapping("/{id}/with-dish")
    public ResponseEntity<Restaurant> getWithMenu(@PathVariable int id) {
        log.info("get restaurant {} with dish", id);
        return ResponseEntity.of(repository.getRestaurantWithMenu(id));
    }

    @GetMapping("/with-dish")
    @Cacheable("restaurantsWithDish")
    public List<Restaurant> getAllWithMenu() {
        log.info("get restaurants with dish");
        return repository.getAllRestaurantsWithMenu();
    }

    @GetMapping
    public List<Restaurant> getAll() {
        log.info("get all restaurants");
        return repository.findAll(Sort.by(Sort.Direction.ASC, "id"));
    }
}
