package com.jinan.controller.Voter;


import com.fasterxml.jackson.databind.ObjectMapper;

import com.jinan.Utils.WebsocketHandler;

import com.jinan.entities.UserLogin.Login;

import com.jinan.entities.Voter.Vote;
import com.jinan.entities.Voter.VoteRecord;
import com.jinan.Utils.Identify;
import com.jinan.service.UserLogin.LoginService;
import com.jinan.service.Voter.OptionService;
import com.jinan.service.Voter.VoteRecordService;
import com.jinan.service.Voter.VoteService;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.TransactionCallbackWithoutResult;
import org.springframework.transaction.support.TransactionTemplate;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Slf4j
@Controller
public class VoteWeb {
    @Autowired
    Identify identify;
    @Autowired
    private TransactionTemplate transactionTemplate;
    @Resource
    private VoteRecordService voteRecordService;
    @Resource
    private VoteService voteService;
    @Resource
    private OptionService optionService;
    @Autowired
    private LoginService loginService;
    @Autowired
    private WebsocketHandler handler;
    @Autowired
    private RedisTemplate<String,Object> redisTemplate;

    @RequestMapping(value = "/vote/vote")
    public String getVoteRecords(Map<String,Object> map) {
        List<Vote> voteRecords = voteService.getAllVotes();
        map.put("Records", voteRecords);
        return "/vote/voteRecords";
    }

    //  查询某个投票项目
    @RequestMapping("/vote/voteInfo")
    public String showVote(int id, Model model) {
        Vote vote = voteService.getVoteById(id);
        List<VoteRecord> VoteRecord = voteRecordService.getVoteRecordsByVoteId(id);      //  投票排行榜 ----- 一个项目的用户投票数排行 // 查询到每个投票纪录
        if (vote != null || VoteRecord != null) {
            model.addAttribute("Votes", vote);
            model.addAttribute("VoteRecord", VoteRecord);
        }
        return "/vote/VoteInfo";
    }

    //  更新投票
    @PostMapping("/vote/submit")
    public ResponseEntity<Map<String, Object>> submitVote(@RequestBody Map<String, String> requestBody, HttpServletRequest request) {
        VoteRecord voteRecord = new VoteRecord();
        String token = request.getHeader("Authorization");
        if (token == null || !token.startsWith("Bearer ")) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(Map.of("success", false, "message", "无效的令牌"));
        }

        token = token.substring(7); // Remove "Bearer " prefix
        System.out.println("requestBody = " + requestBody);
        try {
            voteRecord.setVoteId(Integer.parseInt(requestBody.get("voteId")));
            voteRecord.setOptionId(Integer.parseInt(requestBody.get("optionId")));
            voteRecord.setVoterName(identify.GetName(token));                       // 得到Jwt加密的令牌中的值
            String name = identify.GetRealName(token);                              // reids 中查自己的登录状态
            if(Boolean.TRUE.equals(redisTemplate.hasKey("login" + name))){
                //  保存投票记录
                boolean insert = voteRecordService.InsertVoteRecord(voteRecord);
                if(insert){
                    return ResponseEntity.ok(Map.of("success", true, "message", "投票成功"));
                }
            }
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Map.of("success", false, "message", "投票失败：投票次数大于 3"));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Map.of("success", false, "message", "投票失败：" + e.getMessage()));
        }
    }
    @GetMapping("/vote/createvote")
    public String createPoll() {
        return "/vote/CreateVote";
    }

    //  创建投票
    @PostMapping("/vote/createvote")
    public ResponseEntity<Map<String, Object>> createPoll(@RequestBody String requestBody, HttpServletRequest request) {
        ObjectMapper objectMapper = new ObjectMapper();     // 解析为Json
        Vote vote= new Vote();           // 这是投票的实体类
        String token = request.getHeader("Authorization");

        if (token == null || !token.startsWith("Bearer ")) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(Map.of("success", false, "message", "无效的令牌"));
        }
        token = token.substring(7); // Remove "Bearer " prefix
        String name = identify.GetName(token);
        Login login = loginService.findUserByName(name);        // 查找用户
        if(login == null){
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(Map.of("success", false, "message", "未找到用户，登录信息问题"));
        }
        transactionTemplate.execute(new TransactionCallbackWithoutResult() {
            @Override
            protected void doInTransactionWithoutResult(TransactionStatus status) {
                try {
                    System.out.println(" ------------------------ 插入了投票 ");
                    Map<String, Object> map = objectMapper.readValue(requestBody, Map.class);   // 解析 JSON 字符串为 Map<String, Object>
                    System.out.println(map);
                    int voteId = voteService.createVote(map, login);                       // 创建投票

                    ArrayList<Object> options = (ArrayList<Object>) map.get("options");    // 访问嵌套的 Map 选项。
                    for (Object option : options) {
                        optionService.createOption(voteId, option);
                    }

                } catch (IOException e) {
                    log.error("创建投票失败 : " + e.getMessage(), e);
                    status.setRollbackOnly();                   // 设置事务回滚
                }
            }
        });
        //  返回响应
        return ResponseEntity.ok(Map.of("success", true, "message", "创建投票成功"));
    }
}

