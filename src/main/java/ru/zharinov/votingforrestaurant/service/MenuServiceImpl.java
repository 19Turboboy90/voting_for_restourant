package ru.zharinov.votingforrestaurant.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.zharinov.votingforrestaurant.model.Menu;
import ru.zharinov.votingforrestaurant.repository.MenuRepository;
import ru.zharinov.votingforrestaurant.repository.RestaurantRepository;
import ru.zharinov.votingforrestaurant.util.validation.ValidationUtil;

import java.util.List;

@Service
@Transactional
@Slf4j
public class MenuServiceImpl implements MenuService {
    private final MenuRepository menuRepository;
    private final RestaurantRepository restaurantRepository;

    public MenuServiceImpl(MenuRepository menuRepository, RestaurantRepository restaurantRepository) {
        this.menuRepository = menuRepository;
        this.restaurantRepository = restaurantRepository;
    }

    @Override
    public Menu create(Menu menu) {
        log.info("create menu ={}", menu);
        ValidationUtil.checkNew(menu);
        return menuRepository.save(menu);
    }

    @Override
    public Menu update(Menu menu, int id) {
        log.info("update menu = {}, id = {}", menu, id);
        ValidationUtil.assureIdConsistent(menu, menu.id());
        ValidationUtil.checkNotFound(restaurantRepository.existsById(id),
                "Restaurant with id=" + id + " not found");
        menu.setRestaurant(restaurantRepository.getOne(id));
        return menuRepository.save(menu);
    }

    @Override
    public void delete(int id) {
        log.info("delete menu by id = {}", id);
        menuRepository.deleteExisted(id);
    }

    @Override
    public Menu get(int id) {
        log.info("get menu by id = {}", id);
        return menuRepository.getExisted(id);
    }

    @Override
    public List<Menu> getAll() {
        log.info("get all menu");
        return menuRepository.findAll();
    }

    public List<Menu> getAllMenuForRestaurant(Integer restaurantId) {
        return menuRepository.getAllMenuForRestaurant(restaurantId);
    }
}