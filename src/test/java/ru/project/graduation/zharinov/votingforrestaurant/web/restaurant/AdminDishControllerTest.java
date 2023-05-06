package ru.project.graduation.zharinov.votingforrestaurant.web.restaurant;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import ru.project.graduation.zharinov.votingforrestaurant.model.Dish;
import ru.project.graduation.zharinov.votingforrestaurant.repository.DishRepository;
import ru.project.graduation.zharinov.votingforrestaurant.util.JsonUtil;
import ru.project.graduation.zharinov.votingforrestaurant.web.AbstractControllerTest;

import java.time.LocalDate;
import java.time.Month;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static ru.project.graduation.zharinov.votingforrestaurant.web.restaurant.AdminDishController.URL;
import static ru.project.graduation.zharinov.votingforrestaurant.web.restaurant.DishTestData.*;
import static ru.project.graduation.zharinov.votingforrestaurant.web.restaurant.RestaurantTestData.RESTAURANT_ID_1;
import static ru.project.graduation.zharinov.votingforrestaurant.web.restaurant.RestaurantTestData.RESTAURANT_ID_2;
import static ru.project.graduation.zharinov.votingforrestaurant.web.user.UserTestData.ADMIN_MAIL;
import static ru.project.graduation.zharinov.votingforrestaurant.web.user.UserTestData.USER_MAIL;

class AdminDishControllerTest extends AbstractControllerTest {
    private static final String URL_SLASH = URL + "/";
    public static final String URL_RESTAURANT_1 = URL_SLASH + RESTAURANT_ID_1 + "/dish";
    public static final String URL_RESTAURANT_1_DISH_1 = URL_RESTAURANT_1 + "/" + MENU_ID;
    public static final String URL_NOT_FOUND = URL_SLASH + RestaurantTestData.NOT_FOUND + "/dish";
    @Autowired
    private DishRepository dishRepository;

    @Test
    @WithUserDetails(value = ADMIN_MAIL)
    void get() throws Exception {
        perform(MockMvcRequestBuilders.get(URL_RESTAURANT_1_DISH_1))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(MENU_MATCHER.contentJson(dish1));
    }

    @Test
    @WithUserDetails(value = ADMIN_MAIL)
    void getAll() throws Exception {
        perform(MockMvcRequestBuilders.get(URL_RESTAURANT_1))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(MENU_MATCHER.contentJson(menuRestaurant));
    }

    @Test
    @WithUserDetails(value = ADMIN_MAIL)
    void getByDate() throws Exception {
        perform(MockMvcRequestBuilders.get(URL_RESTAURANT_1 + "/by-date")
                .param("menuDate", String.valueOf(LocalDate.of(2023, Month.JANUARY, 11))))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(MENU_MATCHER.contentJson(List.of(dish7, dish8)));
    }

    @Test
    @WithUserDetails(value = ADMIN_MAIL)
    void getNotFound() throws Exception {
        perform(MockMvcRequestBuilders.get(URL_RESTAURANT_1 + "/" + NOT_FOUND))
                .andDo(print())
                .andExpect(status().isNotFound());
    }

    @Test
    void getUnauthorized() throws Exception {
        perform(MockMvcRequestBuilders.get(URL_RESTAURANT_1_DISH_1))
                .andExpect(status().isUnauthorized());
    }

    @Test
    @WithUserDetails(value = USER_MAIL)
    void getForbidden() throws Exception {
        perform(MockMvcRequestBuilders.get(URL_RESTAURANT_1_DISH_1))
                .andExpect(status().isForbidden());
    }

    @Test
    @WithUserDetails(value = ADMIN_MAIL)
    void delete() throws Exception {
        perform(MockMvcRequestBuilders.delete(URL_RESTAURANT_1_DISH_1))
                .andExpect(status().isNoContent());
        assertFalse(dishRepository.findById(MENU_ID).isPresent());
    }

    @Test
    @WithUserDetails(value = ADMIN_MAIL)
    void deleteNotFound() throws Exception {
        perform(MockMvcRequestBuilders.delete(URL_RESTAURANT_1 + "/" + NOT_FOUND))
                .andExpect(status().isConflict());
    }

    @Test
    void deleteUnauthorized() throws Exception {
        perform(MockMvcRequestBuilders.delete(URL_RESTAURANT_1_DISH_1))
                .andExpect(status().isUnauthorized());
    }

    @Test
    @WithUserDetails(value = USER_MAIL)
    void deleteForbidden() throws Exception {
        perform(MockMvcRequestBuilders.delete(URL_RESTAURANT_1_DISH_1))
                .andExpect(status().isForbidden());
    }

    @Test
    @WithUserDetails(value = ADMIN_MAIL)
    void deleteInvalidRestaurant() throws Exception {
        perform(MockMvcRequestBuilders.delete(URL_SLASH + RESTAURANT_ID_2 + "/dish/" + MENU_ID))
                .andDo(print())
                .andExpect(status().isConflict());
    }

    @Test
    @WithUserDetails(value = ADMIN_MAIL)
    void update() throws Exception {
        Dish updated = getUpdated();
        perform(MockMvcRequestBuilders.put(URL_RESTAURANT_1_DISH_1)
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(updated)))
                .andExpect(status().isNoContent());

        MENU_MATCHER.assertMatch(dishRepository.getExisted(MENU_ID), updated);
    }

    @Test
    void updateUnauthorized() throws Exception {
        perform(MockMvcRequestBuilders.put(URL_RESTAURANT_1_DISH_1)
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(getUpdated())))
                .andExpect(status().isUnauthorized());
    }

    @Test
    @WithUserDetails(value = USER_MAIL)
    void updateForbidden() throws Exception {
        perform(MockMvcRequestBuilders.put(URL_RESTAURANT_1_DISH_1)
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(getUpdated())))
                .andExpect(status().isForbidden());
    }

    @Test
    @WithUserDetails(value = ADMIN_MAIL)
    void createWithLocation() throws Exception {
        Dish newRestaurant = getNew();
        ResultActions action = perform(MockMvcRequestBuilders.post(URL_RESTAURANT_1)
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(newRestaurant)))
                .andExpect(status().isCreated());
        Dish created = MENU_MATCHER.readFromJson(action);
        int newId = created.id();
        newRestaurant.setId(newId);
        MENU_MATCHER.assertMatch(created, newRestaurant);
        MENU_MATCHER.assertMatch(dishRepository.getExisted(newId), newRestaurant);
    }

    @Test
    @WithUserDetails(value = ADMIN_MAIL)
    void createNotFoundRestaurant() throws Exception {
        Dish newRestaurant = getNew();
        perform(MockMvcRequestBuilders.post(URL_NOT_FOUND)
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(newRestaurant)))
                .andExpect(status().isConflict());
    }

    @Test
    void createWithLocationUnauthorized() throws Exception {
        perform(MockMvcRequestBuilders.post(URL_RESTAURANT_1)
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(getNew())))
                .andExpect(status().isUnauthorized());
    }

    @Test
    @WithUserDetails(value = USER_MAIL)
    void createWithLocationForbidden() throws Exception {
        perform(MockMvcRequestBuilders.post(URL_RESTAURANT_1)
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(getNew())))
                .andExpect(status().isForbidden());
    }

    @Test
    @WithUserDetails(value = ADMIN_MAIL)
    void createInvalid() throws Exception {
        Dish invalid = new Dish(null, null, 0.0, null);
        perform(MockMvcRequestBuilders.post(URL_RESTAURANT_1)
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(invalid)))
                .andDo(print())
                .andExpect(status().isUnprocessableEntity());
    }

    @Test
    @WithUserDetails(value = ADMIN_MAIL)
    void createDuplicate() throws Exception {
        Dish invalid = new Dish(null, dish1.getName(), 10.0, dish1.getDishDate());
        perform(MockMvcRequestBuilders.post(URL_RESTAURANT_1)
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(invalid)))
                .andDo(print())
                .andExpect(status().isConflict());
    }

    @Test
    @WithUserDetails(value = ADMIN_MAIL)
    void updateInvalid() throws Exception {
        Dish invalid = new Dish(dish1);
        invalid.setName("");
        perform(MockMvcRequestBuilders.put(URL_RESTAURANT_1_DISH_1)
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(invalid)))
                .andDo(print())
                .andExpect(status().isUnprocessableEntity());
    }

    @Test
    @WithUserDetails(value = ADMIN_MAIL)
    void updateHtmlUnsafe() throws Exception {
        Dish invalid = new Dish(dish1);
        invalid.setName("<script>alert(123)</script>");
        perform(MockMvcRequestBuilders.put(URL_RESTAURANT_1_DISH_1)
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(invalid)))
                .andDo(print())
                .andExpect(status().isUnprocessableEntity());
    }

    @Test
    @Transactional(propagation = Propagation.NEVER)
    @WithUserDetails(value = ADMIN_MAIL)
    void updateDuplicate() throws Exception {
        Dish invalid = new Dish(dish1);
        invalid.setName(dish2.getName());
        invalid.setDishDate(dish2.getDishDate());
        perform(MockMvcRequestBuilders.put(URL_RESTAURANT_1_DISH_1)
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(invalid)))
                .andDo(print())
                .andExpect(status().isConflict());
    }

    @Test
    @WithUserDetails(value = ADMIN_MAIL)
    void updateInvalidRestaurant() throws Exception {
        Dish invalid = new Dish(dish1);
        perform(MockMvcRequestBuilders.put(URL_SLASH + RESTAURANT_ID_2 + "/dish/" + MENU_ID)
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(invalid)))
                .andDo(print())
                .andExpect(status().isConflict());
    }
}