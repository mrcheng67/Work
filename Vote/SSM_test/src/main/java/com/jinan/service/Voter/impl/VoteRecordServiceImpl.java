package com.jinan.service.Voter.impl;

import com.jinan.entities.Voter.VoteRecord;
import com.jinan.mapper.Voter.VoteRecordMapper;
import com.jinan.service.Voter.VoteRecordService;
import jakarta.annotation.Resource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ZSetOperations;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class VoteRecordServiceImpl implements VoteRecordService {

    @Autowired
    private VoteRecordMapper voteRecordMapper;

    @Resource
    private RedisTemplate<String,Object> redisTemplate;
    @Override
    public boolean InsertVoteRecord(VoteRecord voteRecord) {
        // 是否有投票纪录
        Integer vote_count = voteRecordMapper.selectByUseNameAndId(voteRecord.getVoteId(),voteRecord.getVoterName(),voteRecord.getOptionId());
        System.out.println("vote_count = " + vote_count);
        if(vote_count != null && vote_count < 3){                     // 在没有投票纪录时
            voteRecord.setVoteCount(++vote_count);
            voteRecordMapper.update(voteRecord);
            return true;
        } else if(vote_count == null) {
            voteRecord.setVoteCount(1);
            voteRecordMapper.insert(voteRecord);
            return true;
        } else if(vote_count >= 3){
            return false;
        }
        System.out.println("voteRecord.getVoteCount() = " + voteRecord.getVoteCount());
        return false;
    }

//    @Override
//    public VoteRecord getVoteRecordById(Integer recordId) {
//        return voteRecordMapper.selectById(recordId);
//    }

    @Override
    public List<VoteRecord> getVoteRecordsByVoteId(Integer voteId) {
        List<VoteRecord> records = voteRecordMapper.selectByVoteId(voteId);

        String key = "vote_records_" + voteId;      // 将查到的数据保存到redis中
        redisTemplate.opsForZSet().removeRange(key, 0, -1); // 清空现有数据

        for (VoteRecord record : records) {
            redisTemplate.opsForZSet().add(key, String.valueOf(record.getVoterName() + ":" + record.getOptionId()), record.getVoteCount());
        }

        Set<ZSetOperations.TypedTuple<Object>> sortedRecords = redisTemplate.opsForZSet().reverseRangeWithScores(key, 0, -1);
        records.clear();
        for (ZSetOperations.TypedTuple<Object> sortedRecord : sortedRecords) {
            String memberValue = (String) sortedRecord.getValue();
            String[] parts = memberValue.split(":");
            if (parts.length == 2) {
                String voterName = parts[0];
                Integer optionId = Integer.parseInt(parts[1]);
                Integer voteCount = ((Number) sortedRecord.getScore()).intValue();
                records.add(new VoteRecord(voterName, voteId, optionId, voteCount));
            } else {
                throw new IllegalArgumentException("Invalid member value format: " + memberValue);
            }
        }
        return records;
    }

    @Override
    public void updateVoteRecord(VoteRecord voteRecord) {
        voteRecordMapper.update(voteRecord);
    }

    @Override
    public void deleteVoteRecord(Integer recordId) {
        voteRecordMapper.delete(recordId);
    }
}