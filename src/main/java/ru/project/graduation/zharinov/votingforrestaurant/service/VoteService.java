package ru.project.graduation.zharinov.votingforrestaurant.service;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.project.graduation.zharinov.votingforrestaurant.model.Vote;
import ru.project.graduation.zharinov.votingforrestaurant.repository.RestaurantRepository;
import ru.project.graduation.zharinov.votingforrestaurant.repository.VoteRepository;

@Service
@AllArgsConstructor
@Slf4j
public class VoteService {
    private final VoteRepository voteRepository;
    private final RestaurantRepository restaurantRepository;

    @Transactional
    public Vote save(Vote vote, int restaurantId){
        log.info("Save vote = {}, restaurantId = {}", vote, restaurantId);
        vote.setRestaurant(restaurantRepository.getReferenceById(restaurantId));
        return voteRepository.save(vote);
    }
}
