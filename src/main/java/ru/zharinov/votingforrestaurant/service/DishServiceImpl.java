package ru.zharinov.votingforrestaurant.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.zharinov.votingforrestaurant.model.Dish;
import ru.zharinov.votingforrestaurant.repository.DishRepository;
import ru.zharinov.votingforrestaurant.util.validation.ValidationUtil;

import java.util.List;

@Service
@Transactional
@Slf4j
public class DishServiceImpl implements DishService {
    private final DishRepository dishRepository;

    public DishServiceImpl(DishRepository dishRepository) {
        this.dishRepository = dishRepository;
    }

    @Override
    public Dish create(Dish dish) {
        log.info("create dish ={}", dish);
        ValidationUtil.checkNew(dish);
        return dishRepository.save(dish);
    }

    @Override
    public Dish update(Dish dish, int id) {
        log.info("update dish = {}, id = {}", dish, id);
        ValidationUtil.assureIdConsistent(dish, id);
        Dish existingDish = dishRepository.getExisted(id);
        dish.setId(existingDish.getId());
        return dishRepository.save(dish);
    }

    @Override
    public void delete(int id) {
        log.info("delete dish by id = {}", id);
        ValidationUtil.checkNotFound(dishRepository.existsById(id), "Dish with id=" + id + " not found");
        dishRepository.deleteById(id);
    }

    @Override
    public Dish get(int id) {
        log.info("get dish by id = {}", id);
        return ValidationUtil.checkNotFound(dishRepository.findById(id).orElse(null),
                "Dish with id=" + id + " not found");
    }

    @Override
    public List<Dish> getAll() {
        log.info("get all dishes");
        return dishRepository.findAll();
    }

    public List<Dish> getAllDishesForMenu(Integer menuId) {
        return dishRepository.getAllDishesForMenu(menuId);
    }
}