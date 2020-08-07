package com.thoughtworks.rslist.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jayway.jsonpath.JsonPath;
import com.thoughtworks.rslist.dominate.UserDetiles;
import com.thoughtworks.rslist.entity.UserEntity;
import com.thoughtworks.rslist.repository.UserRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;


import java.sql.Connection;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.Statement;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@SpringBootTest
@AutoConfigureMockMvc
public class UserEventTest {
    ObjectMapper objectMapper = new ObjectMapper();
    @Autowired
    MockMvc mockMvc;
    @Autowired
    UserRepository userRepository;


    @BeforeEach
    void cleanUp(){
        userRepository.deleteAll();
    }

    @Test
    void shouldAddUser() throws Exception {
        UserDetiles user = new UserDetiles(
                "Sun",20,"male","xiaosun@qq.com","18912341234");

        String request = objectMapper.writeValueAsString(user);

        mockMvc.perform(post("/user")
                .contentType(MediaType.APPLICATION_JSON)
                .content(request))
                .andExpect(status().isOk());

        List<UserEntity> users = userRepository.findAll();

        assertEquals(1,users.size());
        assertEquals("Sun",users.get(0).getUserName());
    }

    @Test
    void shouldGetUserByIndex() throws Exception {
        UserDetiles user1 = new UserDetiles(
                "Sun",20,"male","xiaosun@qq.com","18912341234");
        UserDetiles user2 = new UserDetiles(
                "Ming",22,"male","xiaoming@qq.com","15912341234");

        String request1 = objectMapper.writeValueAsString(user1);
        String request2 = objectMapper.writeValueAsString(user2);

        mockMvc.perform(post("/user")
                .contentType(MediaType.APPLICATION_JSON)
                .content(request1))
                .andExpect(status().isOk());
        mockMvc.perform(post("/user")
                .contentType(MediaType.APPLICATION_JSON)
                .content(request2))
                .andExpect(status().isOk());
        mockMvc.perform(get("/user/1"))
                .andExpect(jsonPath("$.userName",is("Sun")))
                .andExpect(jsonPath("$.age",is(20)))
                .andExpect(status().isOk());
    }

    @Test
    void shouldDeleteUserByIndex() throws Exception {
        UserDetiles user1 = new UserDetiles(
                "Sun",20,"male","xiaosun@qq.com","18912341234");
        UserDetiles user2 = new UserDetiles(
                "Ming",22,"male","xiaoming@qq.com","15912341234");

        String request1 = objectMapper.writeValueAsString(user1);
        String request2 = objectMapper.writeValueAsString(user2);

        mockMvc.perform(post("/user")
                .contentType(MediaType.APPLICATION_JSON)
                .content(request1))
                .andExpect(status().isOk());
        mockMvc.perform(post("/user")
                .contentType(MediaType.APPLICATION_JSON)
                .content(request2))
                .andExpect(status().isOk());
        mockMvc.perform(delete("/user/1"))
                .andExpect(status().isOk());

        List<UserEntity> users = userRepository.findAll();

        assertEquals(1,users.size());
    }
}
