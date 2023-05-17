package ru.zharinov.votingforrestaurant.web.vote;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import ru.zharinov.votingforrestaurant.model.Vote;
import ru.zharinov.votingforrestaurant.service.VoteServiceImpl;

import java.util.List;

@RestController
@Slf4j
@RequestMapping(value = VoteController.REST_URL, produces = MediaType.APPLICATION_JSON_VALUE)
public class VoteController {
    public static final String REST_URL = "/api/votes";

    private final VoteServiceImpl voteService;

    @Autowired
    public VoteController(VoteServiceImpl voteService) {
        this.voteService = voteService;
    }

    @GetMapping
    public ResponseEntity<Iterable<Vote>> getAll() {
        log.info("Get all votes");
        return new ResponseEntity<>(voteService.getAll(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Vote> getVoteById(@PathVariable("id") Integer id) {
        log.info("Get vote by id: {}", id);
        Vote vote = voteService.get(id);
        if (vote != null) {
            return new ResponseEntity<>(vote, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping("/vote/{id}")
    public ResponseEntity<Vote> updateMenu(@PathVariable("id") Integer id, @RequestBody Vote vote) {
        log.info("Update vote with id: {}, {}", id, vote);
        Vote updatedVote = voteService.update(vote, id);
        if (updatedVote != null) {
            return new ResponseEntity<>(updatedVote, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping(path = "/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable int id) {
        log.info("Delete vote with id: {}", id);
        voteService.delete(id);
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    @Transactional
    public ResponseEntity<Vote> createMenu(@RequestBody Vote vote) {
        log.info("Create vote: {}", vote);
        return new ResponseEntity<>(voteService.create(vote), HttpStatus.CREATED);
    }

    @GetMapping("/users/{userId}")
    public ResponseEntity<List<Vote>> getAllVotesForUser(@PathVariable("userId") Integer userId) {
        log.info("Get all votes for user with id: {}", userId);
        List<Vote> votes = voteService.getAllVotesForUser(userId);
        if (!votes.isEmpty()) {
            return new ResponseEntity<>(votes, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/users/{userId}/restaurants/{restaurantId}")
    public ResponseEntity<List<Vote>> getAllVotesForUserAndRestaurant(
            @PathVariable("userId") Long userId,
            @PathVariable("restaurantId") Long restaurantId) {
        log.info("Get all votes for user with id: {} and restaurant with id: {}", userId, restaurantId);
        List<Vote> votes = voteService.getAllVotesForUserAndRestaurant(userId, restaurantId);
        if (!votes.isEmpty()) {
            return new ResponseEntity<>(votes, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}