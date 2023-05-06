package ru.project.graduation.zharinov.votingforrestaurant.web.vote;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.Nullable;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import ru.project.graduation.zharinov.votingforrestaurant.error.DataConflictException;
import ru.project.graduation.zharinov.votingforrestaurant.model.Vote;
import ru.project.graduation.zharinov.votingforrestaurant.repository.VoteRepository;
import ru.project.graduation.zharinov.votingforrestaurant.service.VoteService;
import ru.project.graduation.zharinov.votingforrestaurant.to.VoteTo;
import ru.project.graduation.zharinov.votingforrestaurant.util.DateTimeUtil;
import ru.project.graduation.zharinov.votingforrestaurant.util.VoteUtil;
import ru.project.graduation.zharinov.votingforrestaurant.util.validation.ValidationUtil;
import ru.project.graduation.zharinov.votingforrestaurant.web.AuthUser;

import java.net.URI;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@RestController
@Slf4j
@AllArgsConstructor
@RequestMapping(value = VoteController.URL, produces = MediaType.APPLICATION_JSON_VALUE)
public class VoteController {
    static final String URL = "/api/votes";
    private final VoteService voteService;
    private final VoteRepository repository;

    @GetMapping("/{id}")
    public VoteTo get(@AuthenticationPrincipal AuthUser authUser, @PathVariable int id) {
        log.info("get vote {} for user {}", id, authUser.id());
        return VoteUtil.createTo(repository.checkBelong(id, authUser.id()));
    }

    @GetMapping
    public List<VoteTo> getAll(@AuthenticationPrincipal AuthUser authUser) {
        log.info("getAll votes");
        return VoteUtil.getTos(repository.getAll(authUser.id()));
    }

    @GetMapping("/by-date")
    public VoteTo getByDate(@AuthenticationPrincipal AuthUser authUser,
                            @RequestParam @Nullable @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date) {
        log.info("getAll votes for user {} for date {}", authUser.id(), date);
        return VoteUtil.createTo(repository.getByDate(authUser.id(), date).orElseThrow(
                () -> new DataConflictException("Vote is not exist for User id=" + authUser.id() + " for date " + date)));
    }

    @PostMapping
    public ResponseEntity<Vote> createWithLocation(@AuthenticationPrincipal AuthUser authUser, @RequestParam int restaurantId) {
        log.info("User {} votes for Restaurant {}", authUser.id(), restaurantId);
        LocalDate currentDate = LocalDate.now();
        if (repository.getByDate(authUser.id(), currentDate).isPresent()) {
            throw new DataConflictException("Only one vote allowed per user");
        }
        Vote vote = new Vote(null, null, authUser.getUser(), currentDate);
        Vote created = voteService.save(vote, restaurantId);
        URI uriOfNewResource = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path(URL + "/{id}")
                .buildAndExpand(created.getId()).toUri();
        return ResponseEntity.created(uriOfNewResource).body(created);
    }

    @PutMapping
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void update(@AuthenticationPrincipal AuthUser authUser, @RequestParam int restaurantId) {
        LocalDateTime currentDateTime = LocalDateTime.now(DateTimeUtil.getClock());
        log.info("User {} votes for Restaurant {}", authUser.id(), restaurantId);
        Vote vote = repository.getByDate(authUser.id(), currentDateTime.toLocalDate()).orElseThrow(
                () -> new DataConflictException(String.format("Vote is not found for today for user %s", authUser.getUser().getEmail()))
        );
        ValidationUtil.checkTime(currentDateTime.toLocalTime());
        voteService.save(vote, restaurantId);
    }
}
