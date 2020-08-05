package com.thoughtworks.rslist;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.thoughtworks.rslist.dominate.RsEvent;
import com.thoughtworks.rslist.dominate.UserDetiles;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class RsListApplicationTests {

    @Autowired
    MockMvc mockMvc;

    @Test
    void shouldGetRsList() throws Exception {
        mockMvc.perform(get("/rs/list"))
                .andExpect(jsonPath("$[0].eventName",is("理财产品")))
                .andExpect(jsonPath("$[1].eventName",is("最新款5G手机")))
                .andExpect(jsonPath("$[2].eventName",is("猪肉涨价了")))
                .andExpect(jsonPath("$[0].keyWord",is("金融")))
                .andExpect(jsonPath("$[1].keyWord",is("科技")))
                .andExpect(jsonPath("$[1].keyWord",is("生活")))
                .andExpect(jsonPath("$[0].user.userName",is("Ming")))
                .andExpect(jsonPath("$[0].user.age",is(18)))
                .andExpect(jsonPath("$[0].user.gender",is("male")))
                .andExpect(jsonPath("$[0].user.email",is("xiaoming@qq.com")))
                .andExpect(jsonPath("$[0].user.phoneNumber",is("11234567890")))
                .andExpect(status().isOk());

    }

    @Test
    void shouldGetOneList() throws Exception {
        mockMvc.perform(get("/rs/2"))
                .andExpect(jsonPath("$.eventName",is("最新款5G手机")))
                .andExpect(jsonPath("$.keyWord",is("科技")))
                .andExpect(jsonPath("$.user.userName",is("Hua")))
                .andExpect(jsonPath("$.user.age",is(20)))
                .andExpect(jsonPath("$.user.gender",is("female")))
                .andExpect(jsonPath("$.user.email",is("xiaohua@163.com")))
                .andExpect(jsonPath("$.user.phoneNumber",is("11223344556")))
                .andExpect(status().isOk());
    }

    @Test
    void shouldGetRsEventFromTo() throws Exception {
        mockMvc.perform(get("/rs/list?start=1&end=2"))
                .andExpect(jsonPath("$[0].eventName",is("理财产品")))
                .andExpect(jsonPath("$[1].eventName",is("最新款5G手机")))
                .andExpect(jsonPath("$[0].keyWord",is("金融")))
                .andExpect(jsonPath("$[1].keyWord",is("科技")))
                .andExpect(status().isOk());
    }

    @Test
    void shouldAddRsEventToListWhenUserDoNotExist() throws Exception {
        UserDetiles userNew = new UserDetiles(
                "Wang",22,"male","xiaowang@qq.com","18912341234");
        RsEvent postUserEvent = new RsEvent("PS5发布会","游戏",userNew);
        ObjectMapper objectMapper = new ObjectMapper();
        String postUserEventJson = objectMapper.writeValueAsString(postUserEvent);

        mockMvc.perform(post("/rs/event").content(postUserEventJson).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
        mockMvc.perform(get("/rs/list"))
                .andExpect(jsonPath("$[0].eventName",is("理财产品")))
                .andExpect(jsonPath("$[1].eventName",is("最新款5G手机")))
                .andExpect(jsonPath("$[2].eventName",is("猪肉涨价了")))
                .andExpect(jsonPath("$[3].eventName",is("PS5发布会")))
                .andExpect(jsonPath("$[0].keyWord",is("金融")))
                .andExpect(jsonPath("$[1].keyWord",is("科技")))
                .andExpect(jsonPath("$[2].keyWord",is("生活")))
                .andExpect(jsonPath("$[3].keyWord",is("游戏")))
                .andExpect(jsonPath("$[3].user.userName",is("Wang")))
                .andExpect(jsonPath("$[3].user.age",is(22)))
                .andExpect(jsonPath("$[3].user.gender",is("male")))
                .andExpect(jsonPath("$[3].user.email",is("xiaowang@qq.com")))
                .andExpect(jsonPath("$[3].user.phoneNumber",is("18912341234")))
                .andExpect(status().isOk());
    }

    @Test
    void nameShouldNotEmpty() throws Exception {
        UserDetiles userNew = new UserDetiles(
                null,22,"male","xiaowang@qq.com","18912341234");
        RsEvent postUserEvent = new RsEvent("PS5发布会","游戏",userNew);
        ObjectMapper objectMapper = new ObjectMapper();
        String postUserEventJson = objectMapper.writeValueAsString(postUserEvent);

        mockMvc.perform(post("/rs/event").content(postUserEventJson).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    @Test
    void nameShouldNotLongerThan8() throws Exception {
        UserDetiles userNew = new UserDetiles(
                "xiaozhang",22,"male","xiaowang@qq.com","18912341234");
        RsEvent postUserEvent = new RsEvent("PS5发布会","游戏",userNew);
        ObjectMapper objectMapper = new ObjectMapper();
        String postUserEventJson = objectMapper.writeValueAsString(postUserEvent);

        mockMvc.perform(post("/rs/event").content(postUserEventJson).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    @Test
    void ageShouldNotLessThan18() throws Exception {
        UserDetiles userNew = new UserDetiles(
                "Wang",15,"male","xiaowang@qq.com","18912341234");
        RsEvent postUserEvent = new RsEvent("PS5发布会","游戏",userNew);
        ObjectMapper objectMapper = new ObjectMapper();
        String postUserEventJson = objectMapper.writeValueAsString(postUserEvent);

        mockMvc.perform(post("/rs/event").content(postUserEventJson).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    @Test
    void ageShouldNotMoreThan100() throws Exception {
        UserDetiles userNew = new UserDetiles(
                "Wang",120,"male","xiaowang@qq.com","18912341234");
        RsEvent postUserEvent = new RsEvent("PS5发布会","游戏",userNew);
        ObjectMapper objectMapper = new ObjectMapper();
        String postUserEventJson = objectMapper.writeValueAsString(postUserEvent);

        mockMvc.perform(post("/rs/event").content(postUserEventJson).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    @Test
    void genderShouldNotEmpty() throws Exception {
        UserDetiles userNew = new UserDetiles(
                "Wang",20,null,"xiaowang@qq.com","18912341234");
        RsEvent postUserEvent = new RsEvent("PS5发布会","游戏",userNew);
        ObjectMapper objectMapper = new ObjectMapper();
        String postUserEventJson = objectMapper.writeValueAsString(postUserEvent);

        mockMvc.perform(post("/rs/event").content(postUserEventJson).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    @Test
    void emailShouldBeVaild() throws Exception {
        UserDetiles userNew = new UserDetiles(
                "Wang",20,"male","xiaowang","18912341234");
        RsEvent postUserEvent = new RsEvent("PS5发布会","游戏",userNew);
        ObjectMapper objectMapper = new ObjectMapper();
        String postUserEventJson = objectMapper.writeValueAsString(postUserEvent);

        mockMvc.perform(post("/rs/event").content(postUserEventJson).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    @Test
    void phoneNumberShouldBeVaild() throws Exception {
        UserDetiles userNew = new UserDetiles(
                "Wang",20,"male","xiaowang@qq.com","189123412");
        RsEvent postUserEvent = new RsEvent("PS5发布会","游戏",userNew);
        ObjectMapper objectMapper = new ObjectMapper();
        String postUserEventJson = objectMapper.writeValueAsString(postUserEvent);

        mockMvc.perform(post("/rs/event").content(postUserEventJson).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    @Test
    void phoneNumberShouldNotNull() throws Exception {
        UserDetiles userNew = new UserDetiles(
                "Wang",20,"male","xiaowang@qq.com",null);
        RsEvent postUserEvent = new RsEvent("PS5发布会","游戏",userNew);
        ObjectMapper objectMapper = new ObjectMapper();
        String postUserEventJson = objectMapper.writeValueAsString(postUserEvent);

        mockMvc.perform(post("/rs/event").content(postUserEventJson).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    @Test
    void shouldAddOnlyEventToListWhenUserExist() throws Exception {
        UserDetiles userNew = new UserDetiles(
                "Ming",18,"male","xiaoming@qq.com","11234567890");
        RsEvent postUserEvent = new RsEvent("PS5发布会","游戏",userNew);
        ObjectMapper objectMapper = new ObjectMapper();
        String postUserEventJson = objectMapper.writeValueAsString(postUserEvent);

        mockMvc.perform(post("/rs/event").content(postUserEventJson).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated());
        mockMvc.perform(get("/rs/list"))
                .andExpect(jsonPath("$[3].eventName",is("PS5发布会")))
                .andExpect(jsonPath("$[3].keyWord",is("游戏")))
                .andExpect(jsonPath("$[3]",not(hasKey("user"))))
                .andExpect(status().isOk());
    }
}
