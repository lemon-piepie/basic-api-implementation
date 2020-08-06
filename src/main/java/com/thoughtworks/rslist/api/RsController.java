package com.thoughtworks.rslist.api;

import com.thoughtworks.rslist.dominate.RsEvent;
import com.thoughtworks.rslist.dominate.UserDetiles;
import com.thoughtworks.rslist.entity.RsEventEntity;
import com.thoughtworks.rslist.entity.UserEntity;
import com.thoughtworks.rslist.exception.CommonError;
import com.thoughtworks.rslist.exception.UserNotValidException;
import com.thoughtworks.rslist.repository.RsEventRepository;
import com.thoughtworks.rslist.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@RestController
@Validated
public class RsController {
    private List<RsEvent> rsList = createInitialList();
    private final RsEventRepository rsEventRepository;
    private final UserRepository userRepository;

    public RsController(RsEventRepository rsEventRepository,UserRepository userRepository){
        this.rsEventRepository = rsEventRepository;
        this.userRepository = userRepository;
    }

    private static List<RsEvent> createInitialList(){
        List<RsEvent> intiList = new ArrayList<>();
        return intiList;
    }


    @PostMapping("/rs/event")
    public ResponseEntity addOneRsEventToList(@RequestBody @Valid RsEvent rsEvent) throws UserNotValidException {
        if (!userRepository.existsById(Integer.valueOf(rsEvent.getUserId()))){
            throw new UserNotValidException("user not exist");
        }

        RsEventEntity rsEventEntity = RsEventEntity.builder()
                .eventName(rsEvent.getEventName())
                .keyWord(rsEvent.getKeyWord())
                .userId(rsEvent.getUserId())
                .build();
        rsEventRepository.save(rsEventEntity);
        return ResponseEntity.created(null).build();
    }

    @ExceptionHandler(UserNotValidException.class)
    public ResponseEntity<CommonError> handleRequestErrorHandler(Exception ex){
        CommonError commonError = new CommonError();
        commonError.setError(ex.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(commonError);
    }

}