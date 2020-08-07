package com.thoughtworks.rslist.api;

import com.thoughtworks.rslist.dominate.RsEvent;
import com.thoughtworks.rslist.dominate.UserDetiles;
import com.thoughtworks.rslist.entity.UserEntity;
import com.thoughtworks.rslist.repository.RsEventRepository;
import com.thoughtworks.rslist.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.transaction.Transactional;
import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

@RestController
public class UserController {

    @Autowired
    UserRepository userRepository;
    @Autowired
    RsEventRepository rsEventRepository;


    @PostMapping("/user")
    public void addOneUserToList(@RequestBody @Valid UserDetiles user)  {
        UserEntity userEntity = UserEntity.builder()
                .userName(user.getUserName())
                .age(user.getAge())
                .gender(user.getGender())
                .email(user.getEmail())
                .phoneNumber(user.getPhoneNumber())
                .votes(user.getVotes())
                .build();
        userRepository.save(userEntity);
    }

    @GetMapping("/user/{index}")
    ResponseEntity<UserEntity> getOneUser(@PathVariable int userId){
        return ResponseEntity.ok(userRepository.getUserById(userId));
    }

    @Transactional
    @DeleteMapping("/user/{userId}")
    void deleteUser(@PathVariable int userId){
        userRepository.deleteById(userId);
        rsEventRepository.deleteByUserId(userId);
    }
}