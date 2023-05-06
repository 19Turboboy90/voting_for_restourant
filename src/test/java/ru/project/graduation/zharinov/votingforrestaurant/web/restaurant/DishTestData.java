package ru.project.graduation.zharinov.votingforrestaurant.web.restaurant;

import ru.project.graduation.zharinov.votingforrestaurant.model.Dish;
import ru.project.graduation.zharinov.votingforrestaurant.web.MatcherFactory;

import java.time.LocalDate;
import java.time.Month;
import java.util.List;

public class DishTestData {
    public static final MatcherFactory.Matcher<Dish> MENU_MATCHER = MatcherFactory.usingIgnoringFieldsComparator(Dish.class, "restaurant");

    public static final int MENU_ID = 1;
    public static final int NOT_FOUND = 1000;

    public static final Dish dish1 = new Dish(MENU_ID, "Garlic Bread", 3.50, LocalDate.of(2023, Month.JANUARY, 10));
    public static final Dish dish2 = new Dish(MENU_ID + 1, "Soup of the day", 4.99, LocalDate.of(2023, Month.JANUARY, 10));
    public static final Dish dish3 = new Dish(MENU_ID + 2, "Olives", 3.99, LocalDate.of(2023, Month.JANUARY, 10));
    public static final Dish dish4 = new Dish(MENU_ID + 3, "Prawn salad", 4.99, LocalDate.of(2023, Month.JANUARY, 10));
    public static final Dish dish5 = new Dish(MENU_ID + 4, "Mozzarella salad", 3.99, LocalDate.of(2023, Month.JANUARY, 11));
    public static final Dish dish6 = new Dish(MENU_ID + 5, "Roast chicken salad", 3.4, LocalDate.of(2023, Month.JANUARY, 11));
    public static final Dish dish7 = new Dish(MENU_ID + 6, "Chicken pizza", 4.55, LocalDate.of(2023, Month.JANUARY, 11));
    public static final Dish dish8 = new Dish(MENU_ID + 7, "Margherita pizza", 5.0, LocalDate.of(2023, Month.JANUARY, 11));

    public static final Dish dish9 = new Dish(MENU_ID + 8, "Meat pizza", 4.47,LocalDate.of(2023, Month.JANUARY, 12));
    public static final List<Dish> menuRestaurant = List.of(dish7, dish8, dish1, dish2);
    public static final Dish dish10 = new Dish(MENU_ID + 9, "Ice cream", 1.99, LocalDate.of(2023, Month.JANUARY, 12));
    public static final Dish dish11 = new Dish(MENU_ID + 10, "Banana cake", 2.39, LocalDate.of(2023, Month.JANUARY, 12));
    public static final Dish dish12 = new Dish(MENU_ID + 11, "Fruit cake", 2.29, LocalDate.of(2023, Month.JANUARY, 12));
//    public static final Dish dish13 = new Dish(MENU_ID + 12, "paella", 30.0, LocalDate.of(2023, Month.JANUARY, 10));

    public static Dish getNew() {
        return new Dish(null, "New Menu", 10.0, LocalDate.now());
    }

    public static Dish getUpdated() {
        Dish updated = new Dish(dish1);
        updated.setName("UpdatedName");
        updated.setPrice(10.0);
        updated.setDishDate(LocalDate.now());
        return updated;
    }
}
