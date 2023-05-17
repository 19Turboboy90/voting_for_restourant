package ru.javaops.topjava2.web.restaurant;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import ru.zharinov.votingforrestaurant.model.Dish;
import ru.zharinov.votingforrestaurant.service.DishServiceImpl;

import java.util.List;

@RestController
@Slf4j
@RequestMapping(value = DishController.REST_URL, produces = MediaType.APPLICATION_JSON_VALUE)
@PreAuthorize("hasRole('ROLE_ADMIN')")
public class DishController {
    public static final String REST_URL = "/api/dishes";

    private final DishServiceImpl dishService;

    @Autowired
    public DishController(DishServiceImpl dishService) {
        this.dishService = dishService;
    }

    @GetMapping
    public ResponseEntity<Iterable<Dish>> getAll() {
        log.info("Get all dishes");
        return new ResponseEntity<>(dishService.getAll(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Dish> getDishById(@PathVariable("id") Integer id) {
        log.info("Get dish by id: {}", id);
        Dish dish = dishService.get(id);
        if (dish != null) {
            return new ResponseEntity<>(dish, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("/dishes")
    public ResponseEntity<Dish> createDish(@RequestBody Dish dish) {
        log.info("Create dish: {}", dish);
        Dish createdDish = dishService.create(dish);
        return new ResponseEntity<>(createdDish, HttpStatus.CREATED);
    }

    @PutMapping("/dishes/{id}")
    public ResponseEntity<Dish> updateDish(@PathVariable("id") Integer id, @RequestBody Dish dish) {
        log.info("Update dish with id: {}, {}", id, dish);
        Dish updatedDish = dishService.update(dish, id);
        if (updatedDish != null) {
            return new ResponseEntity<>(updatedDish, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping(path = "/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable int id) {
        log.info("Delete dish with id: {}", id);
        dishService.delete(id);
    }

    @GetMapping("/menus/{id}")
    public ResponseEntity<List<Dish>> getAllDishesForMenu(@PathVariable("menuId") Integer menuId) {
        log.info("Get all dishes for menu with id: {}", menuId);
        List<Dish> dishes = dishService.getAllDishesForMenu(menuId);
        if (!dishes.isEmpty()) {
            return new ResponseEntity<>(dishes, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}