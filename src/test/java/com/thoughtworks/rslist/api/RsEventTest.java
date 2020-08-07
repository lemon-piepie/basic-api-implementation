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

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class RsEventTest {

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
    void shouldAddRsEventWhenUserExist() throws Exception {
        UserEntity user1 = UserEntity.builder()
                .userName("Zhou")
                .age(25)
                .gender("female")
                .email("xiaozhou@sina.com")
                .phoneNumber("13812341234")
                .votes(10)
                .build();
        userRepository.save(user1);

        Integer userId = user1.getId();
        String json = "{\"eventName\":\"猪肉涨价了\",\"keyWord\":\"生活\",\"userId\":" + userId + "}";
        mockMvc.perform(post("/rs/event")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json))
                .andExpect(status().isCreated());

        List<RsEventEntity> events = rsEventRepository.findAll();
        assertEquals(1,events.size());
        assertEquals("猪肉涨价了",events.get(0).getEventName());
        assertEquals(userId,events.get(0).getUserId());
    }

    @Test
    void shouldGetErrorAddRsEventWhenUserNotExist() throws Exception {
        String json = "{\"eventName\":\"猪肉涨价了\",\"keyWord\":\"生活\",\"userId\":50}";
        mockMvc.perform(post("/rs/event")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json))
                .andExpect(status().isBadRequest());
    }

    @Test
    void shouldDeleteAllEventWhenDeleteUser() throws Exception {
        UserEntity user = UserEntity.builder()
                .userName("Zheng")
                .age(23)
                .gender("female")
                .email("xiaozheng@sina.com")
                .phoneNumber("17612341234")
                .votes(8)
                .build();
        userRepository.save(user);
        Integer userId = user.getId();
        String userIdString = String.valueOf(userId);

        RsEventEntity event1 = RsEventEntity.builder()
                            .eventName("猪肉涨价了")
                            .keyWord("生活")
                            .userId(userId)
                            .build();
        rsEventRepository.save(event1);
        RsEventEntity event2 = RsEventEntity.builder()
                .eventName("理财产品")
                .keyWord("金融")
                .userId(userId)
                .build();
        rsEventRepository.save(event2);

        mockMvc.perform(delete("/user/" + userIdString)
               .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        List<RsEventEntity> events = rsEventRepository.findAll();
        assertEquals(0,events.size());
        List<UserEntity> users = userRepository.findAll();
        assertEquals(0,users.size());
    }

    @Test
    void shouldUpdateRsEvent() throws Exception {
        UserEntity user = UserEntity.builder()
                .userName("Zhou")
                .age(25)
                .gender("female")
                .email("xiaozhou@sina.com")
                .phoneNumber("13812341234")
                .votes(10)
                .build();
        userRepository.save(user);

        Integer userId = user.getId();
        String userIdString = String.valueOf(userId);
        String json = "{\"eventName\":\"猪肉涨价了\",\"keyWord\":\"生活\",\"userId\":" + userId + "}";
        mockMvc.perform(post("/rs/event")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json))
                .andExpect(status().isCreated());
        String jsonUpdate = "{\"eventName\":\"线上课程\",\"keyWord\":\"学习\",\"userId\":" + userId + "}";
        mockMvc.perform(patch("/rs/"+userIdString)
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonUpdate))
                .andExpect(status().isCreated());
        List<RsEventEntity> events = rsEventRepository.findAll();
        assertEquals(1,events.size());
        assertEquals("线上课程",events.get(0).getEventName());
    }

    @Test
    void shouldVote() throws Exception {
        UserEntity user = UserEntity.builder()
                .userName("Zhou")
                .age(25)
                .gender("female")
                .email("xiaozhou@sina.com")
                .phoneNumber("13812341234")
                .votes(10)
                .build();
        userRepository.save(user);
        Integer userId = user.getId();
        String userIdString = String.valueOf(userId);
        String json = "{\"eventName\":\"猪肉涨价了\",\"keyWord\":\"生活\",\"userId\":" + userId + "}";
        mockMvc.perform(post("/rs/event")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json));
        String voteRequest = "{\"voteNum\":5, \"userId\":" + userId + ", \"voteTime\":\""+ LocalTime.now().toString()+"\"}";
        mockMvc.perform(post("/rs/vote/"+userIdString)
                .contentType(MediaType.APPLICATION_JSON)
                .content(voteRequest))
                .andExpect(status().isOk());
        List<VoteEntity> votes = voteRepository.findAll();
        assertEquals(1,votes.size());
    }

    @Test
    void shouldGetErrorIfVoteNumberNotEnough() throws Exception {
        UserEntity user = UserEntity.builder()
                .userName("Zhou")
                .age(25)
                .gender("female")
                .email("xiaozhou@sina.com")
                .phoneNumber("13812341234")
                .votes(3)
                .build();
        userRepository.save(user);
        Integer userId = user.getId();
        String userIdString = String.valueOf(userId);
        String json = "{\"eventName\":\"猪肉涨价了\",\"keyWord\":\"生活\",\"userId\":" + userId + "}";
        mockMvc.perform(post("/rs/event")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json));
        String voteRequest = "{\"voteNum\":5, \"userId\":" + userId + ", \"voteTime\":\""+ LocalTime.now().toString()+"\"}";
        mockMvc.perform(post("/rs/vote/"+userIdString)
                .contentType(MediaType.APPLICATION_JSON)
                .content(voteRequest))
                .andExpect(status().isBadRequest());
    }

    @Test
    void shouldGetRsEventVoteNumber() throws Exception {
        UserEntity user = UserEntity.builder()
                .userName("Zhou")
                .age(25)
                .gender("female")
                .email("xiaozhou@sina.com")
                .phoneNumber("13812341234")
                .votes(10)
                .build();
        userRepository.save(user);
        Integer userId = user.getId();
        String userIdString = String.valueOf(userId);

        String json = "{\"eventName\":\"猪肉涨价了\",\"keyWord\":\"生活\",\"userId\":" + userId + "}";
        mockMvc.perform(post("/rs/event")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json));
        String voteRequest = "{\"voteNum\":5, \"userId\":" + userId + ", \"voteTime\":\""+ LocalTime.now().toString()+"\"}";
        mockMvc.perform(post("/rs/vote/"+userIdString)
                .contentType(MediaType.APPLICATION_JSON)
                .content(voteRequest))
                .andExpect(status().isOk());
        List<RsEventEntity> events = rsEventRepository.findAll();
        assertEquals(5,events.get(0).getVoteNum());
    }
}
