package com.jinan.mapper.Voter;

import com.jinan.entities.Voter.Options;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
public interface OptionMapper {
    void insert(Options options);

    Options selectById(Integer optionId);

    List<Options> selectByVoteId(Integer voteId);

    void update(Options option);

    void delete(Integer optionId);

}