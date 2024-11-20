package com.jinan.entities.Voter;

public class Options {
    private Integer optionId;
    private Integer voteId;
    private String optionText;

    public void setOptionId(Integer optionId) {
        this.optionId = optionId;
    }

    public void setVoteId(Integer voteId) {
        this.voteId = voteId;
    }

    public void setOptionText(String optionText) {
        this.optionText = optionText;
    }

    public Integer getOptionId() {
        return optionId;
    }

    public Integer getVoteId() {
        return voteId;
    }

    public String getOptionText() {
        return optionText;
    }

    @Override
    public String toString() {
        return "Options{" +
                "optionId=" + optionId +
                ", voteId=" + voteId +
                ", optionText='" + optionText + '\'' +
                '}';
    }
}