package ru.project.graduation.zharinov.votingforrestaurant.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.project.graduation.zharinov.votingforrestaurant.model.Vote;
import ru.project.graduation.zharinov.votingforrestaurant.repository.UserRepository;
import ru.project.graduation.zharinov.votingforrestaurant.repository.VoteRepository;
import ru.project.graduation.zharinov.votingforrestaurant.util.validation.ValidationUtil;

import java.util.List;

@Service
@Transactional
@Slf4j
public class VoteServiceImpl implements VoteService {
    private final VoteRepository voteRepository;
    private final UserRepository userRepository;

    public VoteServiceImpl(VoteRepository voteRepository, UserRepository userRepository) {
        this.voteRepository = voteRepository;
        this.userRepository = userRepository;
    }

    @Override
    public Vote create(Vote vote) {
        log.info("create vote ={}", vote);
        ValidationUtil.checkNew(vote);
        return voteRepository.save(vote);
    }

    @Override
    public Vote update(Vote vote, int userId) {
        log.info("update vote = {}, id = {}", vote, userId);
        ValidationUtil.assureIdConsistent(vote, vote.id());
        ValidationUtil.checkNotFound(userRepository.existsById(userId), "User with id=" + userId + " not found");
        vote.setUser(userRepository.getOne(userId));
        return voteRepository.save(vote);
    }

    @Override
    public void delete(int id) {
        log.info("delete vote by id = {}", id);
        voteRepository.deleteExisted(id);
    }

    @Override
    public Vote get(int id) {
        log.info("get vote by id = {}", id);
        return voteRepository.getExisted(id);
    }

    @Override
    public List<Vote> getAll() {
        log.info("get all votes");
        return voteRepository.findAll();
    }

  public   List<Vote> getAllVotesForUser(Integer userId) {
        return voteRepository.getAllVotesForUser(userId);
    }

  public   List<Vote> getAllVotesForUserAndRestaurant(Long userId, Long restaurantId) {
        return voteRepository.getAllVotesForUserAndRestaurant(userId, restaurantId);
    }
}