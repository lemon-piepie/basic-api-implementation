package com.thoughtworks.rslist.api;

import com.thoughtworks.rslist.dominate.RsEvent;
import com.thoughtworks.rslist.entity.RsEventEntity;
import com.thoughtworks.rslist.entity.VoteEntity;
import com.thoughtworks.rslist.repository.RsEventRepository;
import com.thoughtworks.rslist.repository.UserRepository;
import com.thoughtworks.rslist.repository.VoteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

@RestController
public class SearchVote {
    @Autowired
    UserRepository userRepository;
    @Autowired
    RsEventRepository rsEventRepository;
    @Autowired
    VoteRepository voteRepository;

    @PostMapping("/vote/list")
    public void geVoteFromTo(@RequestParam(required = false) String start,
                             @RequestParam(required = false) String end) throws ParseException {

        List<VoteEntity> voteList = voteRepository.findAll();
        SimpleDateFormat simpleDate = new SimpleDateFormat("HH:mm:ss");
        long startTime = simpleDate.parse(start).getTime();
        long endTime = simpleDate.parse(end).getTime();

        for (int i = 0; i < voteList.size(); i++){
            long votesTime = simpleDate.parse(voteList.get(i).getVoteTime()).getTime();
            if (votesTime > startTime && votesTime <endTime){
                voteRepository.save(voteList.get(i));
            }
        }
    }
}
