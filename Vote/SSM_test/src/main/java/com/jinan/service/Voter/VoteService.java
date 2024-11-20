package com.jinan.service.Voter;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.jinan.entities.User.Employee;
import com.jinan.entities.UserLogin.Login;
import com.jinan.entities.Voter.Vote;

import java.util.List;
import java.util.Map;

public interface VoteService {
    Integer createVote(Map map, Login login) throws JsonProcessingException;

    Vote getVoteById(Integer voteId);

    List<Vote> getAllVotes();

    void updateVote(Vote vote);

    void deleteVote(Integer voteId);
}
