package ru.project.graduation.zharinov.votingforrestaurant.service;


import ru.project.graduation.zharinov.votingforrestaurant.model.Menu;

import java.util.List;

public interface MenuService {
    Menu create(Menu menu);

    Menu update(Menu menu, int id);

    void delete(int id);

    Menu get(int id);

    List<Menu> getAll();
}
