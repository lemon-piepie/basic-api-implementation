package com.thoughtworks.rslist.api;

import com.thoughtworks.rslist.dominate.RsEvent;
import com.thoughtworks.rslist.dominate.UserDetiles;
import org.springframework.web.bind.annotation.*;

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


  /*
  @PostMapping("/rs/delete/{index}")
  public void removeEventFromList(@PathVariable int index){
    rsList.remove(index -1);
  }

  @PostMapping("/rs/modify/{index}")
  public void modifyEventInList(@PathVariable int index,
                                @RequestBody RsEvent rsEvent){
    RsEvent modifyEvent = new RsEvent(null,null);
    if (rsEvent.getEventName()!=null){
      modifyEvent.setEventName(rsEvent.getEventName());
    }else {
      modifyEvent.setEventName(rsList.get(index-1).getEventName());
    }
    if (rsEvent.getKeyWord()!=null){
      modifyEvent.setKeyWord(rsEvent.getKeyWord());
    }else {
      modifyEvent.setKeyWord(rsList.get(index-1).getKeyWord());
    }
    rsList.set(index-1,modifyEvent);
  }
 */
}
