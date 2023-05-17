package ru.project.graduation.zharinov.votingforrestaurant.web.vote;

import ru.project.graduation.zharinov.votingforrestaurant.model.Vote;
import ru.project.graduation.zharinov.votingforrestaurant.to.VoteTo;
import ru.project.graduation.zharinov.votingforrestaurant.web.MatcherFactory;
import ru.project.graduation.zharinov.votingforrestaurant.web.restaurant.RestaurantTestData;

import java.time.LocalDate;
import java.time.Month;

import static ru.project.graduation.zharinov.votingforrestaurant.web.user.UserTestData.admin;
import static ru.project.graduation.zharinov.votingforrestaurant.web.user.UserTestData.user;

public class VoteTestData {
    public static final MatcherFactory.Matcher<VoteTo> VOTE_TO_MATCHER = MatcherFactory.usingEqualsComparator(VoteTo.class);
    public static final MatcherFactory.Matcher<Vote> VOTE_MATCHER = MatcherFactory.usingEqualsComparator(Vote.class);
    public static final int VOTE1_ID = 1;
    public static final int NOT_FOUND = 1000;

    public static final Vote vote1 = new Vote(VOTE1_ID, RestaurantTestData.restaurant2, user, LocalDate.of(2023, Month.JANUARY, 10));
    public static final Vote vote2 = new Vote(VOTE1_ID + 1, RestaurantTestData.restaurant1, admin, LocalDate.of(2023, Month.JANUARY, 10));
    public static final Vote vote3 = new Vote(VOTE1_ID + 2, RestaurantTestData.randomRestaurant, user, LocalDate.of(2023, Month.JANUARY, 11));
    public static final Vote vote4 = new Vote(VOTE1_ID + 3, RestaurantTestData.restaurant2, admin, LocalDate.of(2023, Month.JANUARY, 11));
    public static final Vote vote5 = new Vote(VOTE1_ID + 4, RestaurantTestData.restaurant1, user, LocalDate.of(2023, Month.JANUARY, 12));
    public static final Vote vote6 = new Vote(VOTE1_ID + 5, RestaurantTestData.randomRestaurant, admin, LocalDate.of(2023, Month.JANUARY, 12));
}
