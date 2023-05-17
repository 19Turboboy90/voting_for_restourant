package ru.project.graduation.zharinov.votingforrestaurant.web.vote;


import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import ru.project.graduation.zharinov.votingforrestaurant.model.Vote;
import ru.project.graduation.zharinov.votingforrestaurant.repository.VoteRepository;
import ru.project.graduation.zharinov.votingforrestaurant.util.VoteUtil;
import ru.project.graduation.zharinov.votingforrestaurant.web.AbstractControllerTest;
import ru.project.graduation.zharinov.votingforrestaurant.web.restaurant.RestaurantTestData;

import java.time.*;
import java.util.List;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static ru.project.graduation.zharinov.votingforrestaurant.util.DateTimeUtil.setClock;
import static ru.project.graduation.zharinov.votingforrestaurant.web.restaurant.RestaurantTestData.*;
import static ru.project.graduation.zharinov.votingforrestaurant.web.user.UserTestData.*;
import static ru.project.graduation.zharinov.votingforrestaurant.web.vote.VoteController.URL;
import static ru.project.graduation.zharinov.votingforrestaurant.web.vote.VoteTestData.*;

class VoteControllerTest extends AbstractControllerTest {
    private static final String URL_SLASH = URL + "/";
    private static final LocalTime BEFORE_UPDATE_VOTE_TIME_BORDER = LocalTime.of(10, 0);
    private static final LocalTime AFTER_UPDATE_VOTE_TIME_BORDER = LocalTime.of(12, 0);
    @Autowired
    private VoteRepository voteRepository;

    @Test
    @WithUserDetails(value = USER_MAIL)
    void get() throws Exception {
        perform(MockMvcRequestBuilders.get(URL_SLASH + VOTE1_ID))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(VoteTestData.VOTE_TO_MATCHER.contentJson(VoteUtil.createTo(VoteTestData.vote1)));
    }

    @Test
    @WithUserDetails(value = USER_MAIL)
    void getNotFound() throws Exception {
        perform(MockMvcRequestBuilders.get(URL_SLASH + VoteTestData.NOT_FOUND))
                .andDo(print())
                .andExpect(status().isConflict());
    }

    @Test
    void getUnauthorized() throws Exception {
        perform(MockMvcRequestBuilders.get(URL_SLASH + VOTE1_ID))
                .andExpect(status().isUnauthorized());
    }

    @Test
    @WithUserDetails(value = USER_MAIL)
    void getForbidden() throws Exception {
        perform(MockMvcRequestBuilders.get(URL_SLASH + vote2.getId()))
                .andExpect(status().isConflict());
    }

    @Test
    @WithUserDetails(value = USER_MAIL)
    void getAll() throws Exception {
        perform(MockMvcRequestBuilders.get(URL))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(VoteTestData.VOTE_TO_MATCHER.contentJson(VoteUtil.getTos(List.of(vote5, vote3, vote1))));
    }

    @Test
    @WithUserDetails(value = USER_MAIL)
    void getByDate() throws Exception {
        perform(MockMvcRequestBuilders.get(URL + "/by-date")
                .param("date", String.valueOf(LocalDate.of(2023, Month.JANUARY, 12))))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(VoteTestData.VOTE_TO_MATCHER.contentJson(VoteUtil.createTo(VoteTestData.vote5)));
    }

    @Test
    @WithUserDetails(value = USER_MAIL)
    void createWithLocation() throws Exception {
        ResultActions action = perform(MockMvcRequestBuilders.post(URL)
                .param("restaurantId", String.valueOf(RANDOM_RESTAURANT_ID)))
                .andDo(print())
                .andExpect(status().isCreated());
        Vote created = VOTE_MATCHER.readFromJson(action);
        Vote newVote = new Vote(created.getId(), randomRestaurant, user, LocalDate.now());
        VOTE_MATCHER.assertMatch(created, newVote);
        VOTE_MATCHER.assertMatch(voteRepository.getExisted(created.getId()), newVote);
    }

    @Test
    @WithUserDetails(value = ADMIN_MAIL)
    void updateVote() throws Exception {
        Instant instant = LocalDateTime.of(LocalDate.of(2023, Month.JANUARY, 12), BEFORE_UPDATE_VOTE_TIME_BORDER)
                .toInstant(OffsetDateTime.now().getOffset());
        Clock clock = Clock.fixed(instant, ZoneId.systemDefault());
        setClock(clock);
        Vote updated = new Vote(vote6);
        updated.setRestaurant(restaurant1);
        perform(MockMvcRequestBuilders.put(URL)
                .param("restaurantId", String.valueOf(RESTAURANT_ID_1)))
                .andDo(print())
                .andExpect(status().isNoContent());
        VOTE_MATCHER.assertMatch(voteRepository.getExisted(VOTE1_ID + 5), updated);
        setClock(Clock.systemDefaultZone());
    }

    @Test
    @WithUserDetails(value = ADMIN_MAIL)
    void updateAfterAllowedTime() throws Exception {
        Instant instant = LocalDateTime.of(LocalDate.of(2023, Month.JANUARY, 10), AFTER_UPDATE_VOTE_TIME_BORDER)
                .toInstant(OffsetDateTime.now().getOffset());
        Clock clock = Clock.fixed(instant, ZoneId.systemDefault());
        setClock(clock);
        perform(MockMvcRequestBuilders.put(URL)
                .param("restaurantId", String.valueOf(RESTAURANT_ID_1)))
                .andDo(print())
                .andExpect(status().isUnprocessableEntity());
        setClock(Clock.systemDefaultZone());
    }

    @Test
    @WithUserDetails(value = USER_MAIL)
    void updateNotFound() throws Exception {
        Instant instant = LocalDateTime.of(LocalDate.now(), BEFORE_UPDATE_VOTE_TIME_BORDER).toInstant(OffsetDateTime.now().getOffset());
        Clock clock = Clock.fixed(instant, ZoneId.systemDefault());
        setClock(clock);
        perform(MockMvcRequestBuilders.put(URL)
                .param("restaurantId", String.valueOf(RESTAURANT_ID_1)))
                .andDo(print())
                .andExpect(status().isConflict());
    }

    @Test
    void updateUnauthorized() throws Exception {
        Instant instant = LocalDateTime.of(LocalDate.now(), BEFORE_UPDATE_VOTE_TIME_BORDER).toInstant(OffsetDateTime.now().getOffset());
        Clock clock = Clock.fixed(instant, ZoneId.systemDefault());
        setClock(clock);
        perform(MockMvcRequestBuilders.put(URL)
                .param("restaurantId", String.valueOf(RESTAURANT_ID_1)))
                .andDo(print())
                .andExpect(status().isUnauthorized());
    }

    @Test
    @WithUserDetails(value = USER_MAIL)
    void createNotFoundRestaurant() throws Exception {
        perform(MockMvcRequestBuilders.post(URL)
                .param("restaurantId", String.valueOf(RestaurantTestData.NOT_FOUND)))
                .andDo(print())
                .andExpect(status().isConflict());
    }

    @Test
    void createUnauthorized() throws Exception {
        perform(MockMvcRequestBuilders.post(URL)
                .param("restaurantId", String.valueOf(RANDOM_RESTAURANT_ID)))
                .andDo(print())
                .andExpect(status().isUnauthorized());
    }
}