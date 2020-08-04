package com.thoughtworks.rslist.api;

import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@RestController
public class RsController {
  private List<String> rsList = Stream.of("第一条事件", "第二条事件", "第三条事件").collect(Collectors.toList());

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

  @PostMapping("/rs/event")
  public void addOneEventToList(@RequestBody String rsEvent){
    rsList.add(rsEvent);
  }

}
