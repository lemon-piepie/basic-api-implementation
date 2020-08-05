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
                .andExpect(jsonPath("$[2].keyWord",is("生活")))
                .andExpect(jsonPath("$[0]",not(hasKey("user"))))
                .andExpect(jsonPath("$[1]",not(hasKey("user"))))
                .andExpect(jsonPath("$[2]",not(hasKey("user"))))
                .andExpect(status().isOk());

    }

    @Test
    void shouldAddRsEventToListAndGet201() throws Exception {
        UserDetiles userNew = new UserDetiles(
                "Wang",22,"male","xiaowang@qq.com","18912341234");
        RsEvent postUserEvent = new RsEvent("PS5发布会","游戏",userNew);
        ObjectMapper objectMapper = new ObjectMapper();
        String postUserEventJson = objectMapper.writeValueAsString(postUserEvent);

        mockMvc.perform(post("/rs/event").content(postUserEventJson).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated());
        mockMvc.perform(get("/rs/list"))
                .andExpect(jsonPath("$[0].eventName",is("理财产品")))
                .andExpect(jsonPath("$[1].eventName",is("最新款5G手机")))
                .andExpect(jsonPath("$[2].eventName",is("猪肉涨价了")))
                .andExpect(jsonPath("$[3].eventName",is("PS5发布会")))
                .andExpect(jsonPath("$[0].keyWord",is("金融")))
                .andExpect(jsonPath("$[1].keyWord",is("科技")))
                .andExpect(jsonPath("$[2].keyWord",is("生活")))
                .andExpect(jsonPath("$[3].keyWord",is("游戏")))
                .andExpect(jsonPath("$[0]",not(hasKey("user"))))
                .andExpect(jsonPath("$[1]",not(hasKey("user"))))
                .andExpect(jsonPath("$[2]",not(hasKey("user"))))
                .andExpect(jsonPath("$[3]",not(hasKey("user"))))
                .andExpect(status().isOk());
    }

    @Test
    void shouldGetOneList() throws Exception {
        mockMvc.perform(get("/rs/2"))
                .andExpect(jsonPath("$.eventName",is("最新款5G手机")))
                .andExpect(jsonPath("$.keyWord",is("科技")))
                .andExpect(jsonPath("$",not(hasKey("user"))))
                .andExpect(status().isOk());
    }

    @Test
    void shouldGetUsersOnList() throws Exception {
        mockMvc.perform(get("/rs/userList"))
                .andExpect(jsonPath("$[0].userName",is("Ming")))
                .andExpect(jsonPath("$[1].userName",is("Hua")))
                .andExpect(jsonPath("$[2].userName",is("Li")))
                .andExpect(jsonPath("$[0].age",is(18)))
                .andExpect(jsonPath("$[1].age",is(20)))
                .andExpect(jsonPath("$[2].age",is(19)))
                .andExpect(jsonPath("$[0].gender",is("male")))
                .andExpect(jsonPath("$[1].gender",is("female")))
                .andExpect(jsonPath("$[2].gender",is("female")))
                .andExpect(jsonPath("$[0].email",is("xiaoming@qq.com")))
                .andExpect(jsonPath("$[1].email",is("xiaohua@163.com")))
                .andExpect(jsonPath("$[2].email",is("xiaoli@sina.com")))
                .andExpect(jsonPath("$[0].phoneNumber",is("11234567890")))
                .andExpect(jsonPath("$[1].phoneNumber",is("11223344556")))
                .andExpect(jsonPath("$[2].phoneNumber",is("11222333444")))
                .andExpect(status().isOk());
    }
}
