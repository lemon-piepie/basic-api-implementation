package com.thoughtworks.rslist.api;

import com.thoughtworks.rslist.dominate.UserDetiles;
import com.thoughtworks.rslist.exception.CommonError;
import com.thoughtworks.rslist.exception.InvalidIndexException;
import com.thoughtworks.rslist.exception.InvalidRequestParamException;
import com.thoughtworks.rslist.exception.InvalidUserException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

@RestController
public class UserController {
    private List<UserDetiles> userList = initUserList();

    private List<UserDetiles> initUserList(){
        List<UserDetiles> userDetails = new ArrayList<>();
        userDetails.add(new UserDetiles("Ming",18,"male","xiaoming@qq.com","11234567890"));
        userDetails.add(new UserDetiles("Hua",20,"female","xiaohua@163.com","11223344556"));
        userDetails.add(new UserDetiles("Li",19,"female","xiaoli@sina.com","11222333444"));
        return userDetails;
    }

    @PostMapping("/user")
    public ResponseEntity addOneUserToList(@RequestBody @Valid UserDetiles rsUser) throws InvalidUserException {
        userList.add(rsUser);
        return ResponseEntity.created(null).build();
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity exceptionHandler(Exception ex){
        CommonError commonError = new CommonError();
        String errorMessage = "invalid user";
        commonError.setError(errorMessage);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(commonError);
    }
}
