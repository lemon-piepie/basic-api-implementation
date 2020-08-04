package com.thoughtworks.rslist.api;

import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;

@RestController
public class RsController {
  private List<String> rsList = Arrays.asList("第一条事件", "第二条事件", "第三条事件");

  /*@GetMapping("/rs/list")
  public String getRsList(){
    return rsList.toString();
  }*/

  @GetMapping("rs/{index}")
  public String getOneList(@PathVariable int index){
    return rsList.get(index - 1);
  }

  @GetMapping("/rs/list")
  public String getRsEventFromTo(@RequestParam(required = false) Integer start,
                                 @RequestParam(required = false) Integer end){
    if (start == null || end == null){
      return rsList.toString();
    }
    return rsList.subList(start-1,end).toString();
  }

}
