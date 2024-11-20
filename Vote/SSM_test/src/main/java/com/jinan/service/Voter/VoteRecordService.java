package com.jinan.service.Voter;

import com.jinan.entities.Voter.VoteRecord;

import java.util.List;

public interface VoteRecordService {
    boolean InsertVoteRecord(VoteRecord voteRecord);

    //VoteRecord getVoteRecordById(Integer recordId);

    List<VoteRecord> getVoteRecordsByVoteId(Integer voteId);

    void updateVoteRecord(VoteRecord voteRecord);

    void deleteVoteRecord(Integer recordId);
}
