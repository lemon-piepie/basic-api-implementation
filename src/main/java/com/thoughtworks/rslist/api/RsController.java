package com.thoughtworks.rslist.api;

import com.thoughtworks.rslist.dominate.RsEvent;
import com.thoughtworks.rslist.dominate.UserDetiles;
import com.thoughtworks.rslist.entity.RsEventEntity;
import com.thoughtworks.rslist.entity.UserEntity;
import com.thoughtworks.rslist.repository.RsEventRepository;
import com.thoughtworks.rslist.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@RestController
public class RsController {

    @Autowired
    RsEventRepository rsEventRepository;
    UserRepository userRepository;

    @PostMapping("/rs/event")
    public ResponseEntity addOneRsEventToList(@RequestBody @Valid RsEvent rsEvent)  {
        RsEventEntity rsEventEntity = RsEventEntity.builder()
                .eventName(rsEvent.getEventName())
                .keyWord(rsEvent.getKeyWord())
                .userId(rsEvent.getUserId())
                .build();
        rsEventRepository.save(rsEventEntity);
        return ResponseEntity.created(null).build();
    }


}