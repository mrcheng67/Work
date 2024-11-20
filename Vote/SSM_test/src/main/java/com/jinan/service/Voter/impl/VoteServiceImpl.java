package com.jinan.service.Voter.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jinan.Utils.SaveToTime;
import com.jinan.entities.User.Employee;
import com.jinan.entities.UserLogin.Login;
import com.jinan.entities.Voter.Options;
import com.jinan.entities.Voter.Vote;
import com.jinan.mapper.Voter.OptionMapper;
import com.jinan.mapper.Voter.VoteMapper;
import com.jinan.service.Voter.VoteService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
@Slf4j
public class VoteServiceImpl implements VoteService {

    private final SaveToTime save;

    @Autowired
    private VoteMapper voteMapper;

    @Autowired
    private OptionMapper optionMapper;

    @Autowired
    public VoteServiceImpl(@Lazy SaveToTime save) {
        this.save = save;
    }
    @Override
    public Integer createVote(Map map, Login login) throws JsonProcessingException {
        Vote vote= new Vote();
        ObjectMapper objectMapper = new ObjectMapper();
        vote.setTitle((String) map.get("title"));
        vote.setDescription((String) map.get("description"));
        vote.setCreatedBy(login.getId());
        String json = objectMapper.writeValueAsString(new Date());
        vote.setCreatedAt(json);
        System.out.println("new Date() = " + new Date() + "JSON = " + json);
        vote.setIsActive(true);
        vote.setExpiryTime(Long.parseLong((String) map.get("expiryTime")));
        voteMapper.insert(vote);
        log.info("创建了Vote,并将他们保存到Redis 与 DelayQueue");
        save.writeRecord2Queue(vote);       // 保存到Redis 与 DelayQueue
        save.writeRecordCache(vote);
        return vote.getVoteId();
    }

    @Override
    public Vote getVoteById(Integer voteId) {
        Optional<Vote> optionalVote = Optional.ofNullable(voteMapper.selectById(voteId));
        if (optionalVote.isPresent()) {
            Vote vote = optionalVote.get();
            List<Options> options = optionMapper.selectByVoteId(voteId);

            vote.setOptions(options);
            return vote;
        }
        return null;
    }

    @Override
    public List<Vote> getAllVotes() {
        return voteMapper.selectAll();
    }

    @Override
    public void updateVote(Vote vote) {
        voteMapper.update(vote);
    }

    @Override
    public void deleteVote(Integer voteId) {
        voteMapper.delete(voteId);
    }

}