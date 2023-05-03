package ru.project.graduation.zharinov.votingforrestaurant.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;
import ru.project.graduation.zharinov.votingforrestaurant.error.DataConflictException;
import ru.project.graduation.zharinov.votingforrestaurant.model.Vote;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Transactional(readOnly = true)
public interface VoteRepository extends BaseRepository<Vote> {
    @Query("SELECT v FROM Vote v WHERE v.id=:id AND v.user.id = :userId")
    Optional<Vote> get(int id, int userId);

    @Query("SELECT v FROM Vote v WHERE v.user.id = :userId ORDER BY v.dateOfVoting DESC")
    List<Vote> getAll(int userId);

    @Query("SELECT v FROM Vote v WHERE v.user.id = :userId AND v.dateOfVoting= :dateOfVoting")
    Optional<Vote> getByDate(Integer userId, LocalDate dateOfVoting);

    default Vote checkBelong(int id, int userId) {
        return get(id, userId).orElseThrow(
                () -> new DataConflictException("Vote id=" + id + " is not exist or doesn't belong to User id=" + userId));
    }
}