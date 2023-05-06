package ru.project.graduation.zharinov.votingforrestaurant.web.restaurant;

import ru.project.graduation.zharinov.votingforrestaurant.model.Restaurant;
import ru.project.graduation.zharinov.votingforrestaurant.web.MatcherFactory;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class RestaurantTestData {
    public static final MatcherFactory.Matcher<Restaurant> RESTAURANT_MATCHER = MatcherFactory.usingIgnoringFieldsComparator(Restaurant.class, "menu");
    public static final MatcherFactory.Matcher<Restaurant> RESTAURANT_WITH_MENU_MATCHER = MatcherFactory.usingAssertions(Restaurant.class,
            (a, e) -> assertThat(a).usingRecursiveComparison()
                    .ignoringFields("menu").isEqualTo(e),
            (a, e) -> assertThat(a).usingRecursiveFieldByFieldElementComparatorIgnoringFields("menu").isEqualTo(e)
    );

    public static final int RESTAURANT_ID_1 = 1;
    public static final int RESTAURANT_ID_2 = 2;
    public static final int RANDOM_RESTAURANT_ID = 3;

    public static final int NOT_FOUND = 1000;

    public static final Restaurant restaurant1 = new Restaurant(RESTAURANT_ID_1, "Restaurant1", "restaurant-1.ru", "+7(999)999-99-99");
    public static final Restaurant restaurant2 = new Restaurant(RESTAURANT_ID_2, "Restaurant2", "restaurant-2.ru", "+7(999)111-11-11");
    public static final Restaurant randomRestaurant = new Restaurant(RANDOM_RESTAURANT_ID, "Random_Restaurant", "Random.ru", "+7(999)555-55-55");

    static {
        restaurant2.setMenu(List.of(DishTestData.dish9, DishTestData.dish10, DishTestData.dish3, DishTestData.dish4));
        randomRestaurant.setMenu(List.of(DishTestData.dish12, DishTestData.dish12));
    }

    public static Restaurant getNew() {
        return new Restaurant(null, "New Restaurant", "New WebSite", "New Phone");
    }

    public static Restaurant getUpdated() {
        Restaurant updated = new Restaurant(restaurant1);
        updated.setName("UpdatedName");
        updated.setWebsite("Updated Contact Info Value");
        return updated;
    }
}
