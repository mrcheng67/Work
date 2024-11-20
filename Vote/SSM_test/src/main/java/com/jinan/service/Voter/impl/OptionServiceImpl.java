package com.jinan.service.Voter.impl;

import com.jinan.entities.Voter.Options;
import com.jinan.mapper.Voter.OptionMapper;
import com.jinan.service.Voter.OptionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OptionServiceImpl implements OptionService {
    @Autowired
    private OptionMapper optionMapper;

    @Override
    public void createOption(int voteId, Object list) {
        Options options = new Options();
        options.setVoteId(voteId);
        options.setOptionText((String) list);
        optionMapper.insert(options);
    }

    @Override
    public Options getOptionById(Integer optionId) {
        return optionMapper.selectById(optionId);
    }

    @Override
    public List<Options> getOptionsByVoteId(Integer voteId) {
        return optionMapper.selectByVoteId(voteId);
    }

    @Override
    public void updateOption(Options options) {
        optionMapper.update(options);
    }

    @Override
    public void deleteOption(Integer optionId) {
        optionMapper.delete(optionId);
    }
}