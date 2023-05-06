package ru.project.graduation.zharinov.votingforrestaurant.web.restaurant;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.Nullable;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import ru.project.graduation.zharinov.votingforrestaurant.model.Dish;
import ru.project.graduation.zharinov.votingforrestaurant.repository.DishRepository;
import ru.project.graduation.zharinov.votingforrestaurant.service.DishService;

import java.net.URI;
import java.time.LocalDate;
import java.util.List;

import static ru.project.graduation.zharinov.votingforrestaurant.util.validation.ValidationUtil.assureIdConsistent;
import static ru.project.graduation.zharinov.votingforrestaurant.util.validation.ValidationUtil.checkNew;

@RestController
@Slf4j
@AllArgsConstructor
@RequestMapping(value = AdminDishController.URL, produces = MediaType.APPLICATION_JSON_VALUE)
public class AdminDishController {
    static final String URL = "/api/admin/restaurants";
    private final DishRepository repository;

    private final DishService service;

    @GetMapping("/{restaurantId}/dish/{id}")
    public ResponseEntity<Dish> get(@PathVariable int restaurantId, @PathVariable int id) {
        log.info("get dish {} from restaurant {}", id, restaurantId);
        return ResponseEntity.of(repository.get(id, restaurantId));
    }

    @DeleteMapping("/{restaurantId}/dish/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @CacheEvict(value = "restaurantsWithDish", allEntries = true)
    public void delete(@PathVariable int restaurantId, @PathVariable int id) {
        log.info("delete dish {}", id);
        Dish dish = repository.checkBelong(id, restaurantId);
        repository.delete(dish);
    }

    @GetMapping("/{restaurantId}/dish")
    public List<Dish> getAll(@PathVariable int restaurantId) {
        log.info("getAll dishes for restaurant {}", restaurantId);
        return repository.getAll(restaurantId);
    }

    @PutMapping(value = "/{restaurantId}/dish/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Transactional
    @CacheEvict(value = "restaurantsWithDish", allEntries = true)
    public void update(@Valid @RequestBody Dish dish, @PathVariable int restaurantId, @PathVariable int id) {
        log.info("update dish {} for restaurant {}", id, restaurantId);
        assureIdConsistent(dish, id);
        repository.checkBelong(id, restaurantId);
        service.save(dish, restaurantId);
    }

    @PostMapping(value = "/{restaurantId}/dish", consumes = MediaType.APPLICATION_JSON_VALUE)
    @CacheEvict(value = "restaurantsWithDish", allEntries = true)
    public ResponseEntity<Dish> createWithLocation(@Valid @RequestBody Dish dish, @PathVariable int restaurantId) {
        log.info("create new dish for restaurant {}", restaurantId);
        checkNew(dish);
        Dish created = service.save(dish, restaurantId);
        URI uriOfNewResource = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path(URL + "/{id}")
                .buildAndExpand(created.getId()).toUri();
        return ResponseEntity.created(uriOfNewResource).body(created);
    }

    @GetMapping("/{restaurantId}/dish/by-date")
    public List<Dish> getByDate(@PathVariable Integer restaurantId,
                                @RequestParam @Nullable @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate menuDate) {
        log.info("getAll dishes for restaurant {} for date {}", restaurantId, menuDate);
        return repository.getByDishDate(restaurantId, menuDate);
    }
}
