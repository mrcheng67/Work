package com.jinan.service.Voter;

import com.jinan.entities.Voter.Options;

import java.util.List;

public interface OptionService {
    void createOption(int voteId, Object list);

    Options getOptionById(Integer optionId);

    List<Options> getOptionsByVoteId(Integer voteId);

    void updateOption(Options options);

    void deleteOption(Integer optionId);
}
