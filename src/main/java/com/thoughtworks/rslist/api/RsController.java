package com.thoughtworks.rslist.api;

import com.thoughtworks.rslist.dominate.RsEvent;
import com.thoughtworks.rslist.dominate.UserDetiles;
import com.thoughtworks.rslist.exception.CommonError;
import com.thoughtworks.rslist.exception.InvalidIndexException;
import com.thoughtworks.rslist.exception.InvalidRequestParamException;
import com.thoughtworks.rslist.exception.InvalidUserException;
import org.omg.CORBA.DynAnyPackage.Invalid;
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
  private List<RsEvent> rsList = initRsEvent();


  private List<RsEvent> initRsEvent(){
    List<RsEvent> rsEvents = new ArrayList<>();
    rsEvents.add(new RsEvent("理财产品","金融",
            new UserDetiles("Ming",18,"male","xiaoming@qq.com","11234567890")));
    rsEvents.add(new RsEvent("最新款5G手机","科技",
            new UserDetiles("Hua",20,"female","xiaohua@163.com","11223344556")));
    rsEvents.add(new RsEvent("猪肉涨价了","生活",
            new UserDetiles("Li",19,"female","xiaoli@sina.com","11222333444")));
    return rsEvents;
  }



  @GetMapping("rs/{index}")
  public ResponseEntity<RsEvent> getOneList(@PathVariable int index) throws InvalidIndexException {
    if(index > rsList.size()){
      throw new InvalidIndexException("invalid index");
    }
    return ResponseEntity.ok(rsList.get(index - 1));
  }

  @GetMapping("/rs/list")
  public List<RsEvent> getRsEventFromTo(@RequestParam(required = false) Integer start,
                                        @RequestParam(required = false) Integer end) throws InvalidRequestParamException {
    if (start == null || end == null){
      return rsList;
    }else if (start < 0 || start > rsList.size() || end < 0 || end > rsList.size() || start > end){
      throw new InvalidRequestParamException("invalid request param");
    }
    return rsList.subList(start-1,end);
  }


  @PostMapping("/rs/event")
  public ResponseEntity addOneEventToList(@RequestBody @Valid RsEvent rsEvent){
    rsList.add(rsEvent);
    return ResponseEntity.created(null).build();
  }


  @ExceptionHandler({InvalidIndexException.class, MethodArgumentNotValidException.class,
                    InvalidRequestParamException.class})
  public ResponseEntity exceptionHandler(Exception ex){
    CommonError commonError = new CommonError();
    String errorMessage;
    if (ex instanceof MethodArgumentNotValidException){
      errorMessage = "invalid param";
    }else if (ex instanceof InvalidRequestParamException){
      errorMessage = "invalid request param";
    }else {
      errorMessage = "invalid index";
    }
    commonError.setError(errorMessage);
    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(commonError);
  }

}