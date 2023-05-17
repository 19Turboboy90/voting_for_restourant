package ru.project.graduation.zharinov.votingforrestaurant.web.restaurant;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import ru.project.graduation.zharinov.votingforrestaurant.model.Menu;
import ru.project.graduation.zharinov.votingforrestaurant.service.MenuServiceImpl;

import java.util.List;

@RestController
@Slf4j
@RequestMapping(value = MenuController.REST_URL, produces = MediaType.APPLICATION_JSON_VALUE)
@PreAuthorize("hasRole('ROLE_ADMIN')")
public class MenuController {

    public static final String REST_URL = "/api/menu";
    private final MenuServiceImpl menuService;

    @Autowired
    public MenuController(MenuServiceImpl menuService) {
        this.menuService = menuService;
    }

    @GetMapping
    public ResponseEntity<Iterable<Menu>> getAll() {
        log.info("Get all menu");
        return new ResponseEntity<>(menuService.getAll(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Menu> getMenuById(@PathVariable("id") Integer id) {
        log.info("Get menu by id: {}", id);
        Menu menu = menuService.get(id);
        if (menu != null) {
            return new ResponseEntity<>(menu, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping("/menu/{id}")
    public ResponseEntity<Menu> updateMenu(@PathVariable("id") Integer id, @RequestBody Menu menu) {
        log.info("Update menu with id: {}, {}", id, menu);
        Menu updatedMenu = menuService.update(menu, id);
        if (updatedMenu != null) {
            return new ResponseEntity<>(updatedMenu, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping(path = "/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable int id) {
        log.info("Delete menu with id: {}", id);
        menuService.delete(id);
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    @Transactional
    public ResponseEntity<Menu> createMenu(@RequestBody Menu menu) {
        log.info("Create menu: {}", menu);
        return new ResponseEntity<>(menuService.create(menu), HttpStatus.CREATED);
    }

    @GetMapping("/restaurants/{id}")
    public ResponseEntity<List<Menu>> getAllMenuForRestaurant(@PathVariable("restaurantId") Integer restaurantId) {
        log.info("Get all menu for restaurant with id: {}", restaurantId);
        List<Menu> menus = menuService.getAllMenuForRestaurant(restaurantId);
        if (!menus.isEmpty()) {
            return new ResponseEntity<>(menus, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
