package com.jinan.mapper.Voter;

import com.jinan.entities.Voter.VoteRecord;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import java.util.List;

@Mapper
public interface VoteRecordMapper {
    int insert(VoteRecord voteRecord);

    Integer selectByUseNameAndId(@Param("voteId") int voteId,@Param("voterName") String voterName,@Param("optionId") int optionId);

    List<VoteRecord> selectByVoteId(@Param("voteId") int voteId);

    int update(VoteRecord voteRecord);

    int delete(@Param("recordId") int recordId);

}
