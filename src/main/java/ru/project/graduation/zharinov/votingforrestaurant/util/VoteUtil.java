package ru.project.graduation.zharinov.votingforrestaurant.util;

import lombok.experimental.UtilityClass;
import ru.project.graduation.zharinov.votingforrestaurant.model.Vote;
import ru.project.graduation.zharinov.votingforrestaurant.to.VoteTo;
import java.util.List;
import java.util.stream.Collectors;

@UtilityClass
public class VoteUtil {
    public static VoteTo createTo(Vote vote) {
        return new VoteTo(vote.id(), vote.getDateOfVoting(), vote.getRestaurant().getId());
    }

    public static List<VoteTo> getTos(List<Vote> votes) {
        return votes.stream()
                .map(VoteUtil::createTo)
                .collect(Collectors.toList());
    }
}
