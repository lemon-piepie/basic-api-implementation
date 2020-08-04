package com.thoughtworks.rslist.api;

import com.thoughtworks.rslist.RsEvent;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
public class RsController {
  private List<RsEvent> rsList = initRsEvent();

  private List<RsEvent> initRsEvent(){
    List<RsEvent> rsEvents = new ArrayList<>();
    rsEvents.add(new RsEvent("第一条事件","无"));
    rsEvents.add(new RsEvent("第二条事件","无"));
    rsEvents.add(new RsEvent("第三条事件","无"));
    return rsEvents;
  }


  @GetMapping("rs/{index}")
  public RsEvent getOneList(@PathVariable int index){
    return rsList.get(index - 1);
  }

  @GetMapping("/rs/list")
  public List<RsEvent> getRsEventFromTo(@RequestParam(required = false) Integer start,
                                        @RequestParam(required = false) Integer end){
    if (start == null || end == null){
      return rsList;
    }
    return rsList.subList(start-1,end);
  }

  @PostMapping("/rs/event")
  public void addOneEventToList(@RequestBody RsEvent rsEvent){
    rsList.add(rsEvent);
  }

  @PostMapping("/rs/delete/{index}")
  public void removeEventFromList(@PathVariable int index){
    rsList.remove(index -1);
  }

}
