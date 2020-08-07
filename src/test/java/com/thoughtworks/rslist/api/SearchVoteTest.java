package com.thoughtworks.rslist.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.thoughtworks.rslist.entity.RsEventEntity;
import com.thoughtworks.rslist.entity.UserEntity;
import com.thoughtworks.rslist.entity.VoteEntity;
import com.thoughtworks.rslist.repository.RsEventRepository;
import com.thoughtworks.rslist.repository.UserRepository;
import com.thoughtworks.rslist.repository.VoteRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalTime;
import java.util.List;

import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@SpringBootTest
@AutoConfigureMockMvc
public class SearchVoteTest {
    ObjectMapper objectMapper = new ObjectMapper();
    @Autowired
    MockMvc mockMvc;
    @Autowired
    UserRepository userRepository;
    @Autowired
    RsEventRepository rsEventRepository;
    @Autowired
    VoteRepository voteRepository;

    @BeforeEach
    void cleanUp(){
        userRepository.deleteAll();
        rsEventRepository.deleteAll();
        voteRepository.deleteAll();
    }

    @Test
    void shouldGetVotesByVoteTime() throws Exception {
        UserEntity user = UserEntity.builder()
                .userName("Zheng")
                .age(22)
                .gender("female")
                .email("xiaozheng@sina.com")
                .phoneNumber("13812341234")
                .votes(8)
                .build();
        userRepository.save(user);
        Integer userId = user.getId();
        String userIdString = String.valueOf(userId);

        String json = "{\"eventName\":\"猪肉涨价了\",\"keyWord\":\"生活\",\"userId\":" + userId + "}";
        mockMvc.perform(post("/rs/event")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json));
        String voteRequest = "{\"voteNum\":3, \"userId\":" + userId + ", \"voteTime\":\""+ LocalTime.now().toString()+"\"}";
        mockMvc.perform(post("/rs/vote/"+userIdString)
                .contentType(MediaType.APPLICATION_JSON)
                .content(voteRequest))
                .andExpect(status().isOk());
        String voteRequest2 = "{\"voteNum\":2, \"userId\":" + userId + ", \"voteTime\":\""+ LocalTime.now().toString()+"\"}";
        mockMvc.perform(post("/rs/vote/"+userIdString)
                .contentType(MediaType.APPLICATION_JSON)
                .content(voteRequest2))
                .andExpect(status().isOk());

        String startTime = "17:00:00";
        String endTime = "19:00:00";
        mockMvc.perform(post("/vote/list?start="+startTime+"&end="+endTime))
                .andExpect(status().isOk());
        List<VoteEntity> votes = voteRepository.findAll();
        assertEquals(2,votes.size());
    }
}
