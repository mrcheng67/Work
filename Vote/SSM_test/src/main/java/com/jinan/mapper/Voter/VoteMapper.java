package com.jinan.mapper.Voter;

import com.jinan.entities.Voter.Vote;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
public interface VoteMapper {
    Integer insert(Vote vote);

    Vote selectById(Integer voteId);

    List<Vote> selectAll();

    void update(Vote vote);

    void delete(Integer voteId);

}