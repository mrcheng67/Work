package com.jinan.entities.Voter;

import java.security.Timestamp;

public class VoteRecord {
    private Integer recordId;
    private String voterName;
    private Integer voteId;
    private Integer optionId;
    private Integer voteCount;

    public VoteRecord(){

    }

    public VoteRecord(String voterName, Integer voteId, Integer optionId, Integer voteCount) {
        this.voterName = voterName;
        this.voteId = voteId;
        this.optionId = optionId;
        this.voteCount = voteCount;
    }

    public void setRecordId(Integer recordId) {
        this.recordId = recordId;
    }

    public void setVoterName(String voterName) {
        this.voterName = voterName;
    }

    public void setVoteId(Integer voteId) {
        this.voteId = voteId;
    }

    public void setOptionId(Integer optionId) {
        this.optionId = optionId;
    }

    public void setVoteCount(Integer voteCount) {
        this.voteCount = voteCount;
    }

    public Integer getRecordId() {
        return recordId;
    }

    public String getVoterName() {
        return voterName;
    }

    public Integer getVoteId() {
        return voteId;
    }

    public Integer getOptionId() {
        return optionId;
    }

    public Integer getVoteCount() {
        return voteCount;
    }

    @Override
    public String toString() {
        return "VoteRecord{" +
                "recordId=" + recordId +
                ", voterName='" + voterName + '\'' +
                ", voteId=" + voteId +
                ", optionId=" + optionId +
                ", voteCount=" + voteCount +
                '}';
    }
}
