package ru.project.graduation.zharinov.votingforrestaurant.service;


import ru.project.graduation.zharinov.votingforrestaurant.model.Vote;

import java.util.List;

public interface VoteService {
    Vote create(Vote vote);

    Vote update(Vote vote, int id);

    void delete(int id);

    Vote get(int id);

    List<Vote> getAll();
}
