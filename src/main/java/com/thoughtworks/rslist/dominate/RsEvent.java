package com.thoughtworks.rslist.dominate;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import javax.validation.Valid;

public class RsEvent {
    private String eventName;
    private String keyWord;

    private UserDetiles user;



    public String getEventName() {
        return eventName;
    }

    public void setEventName(String eventName) {
        this.eventName = eventName;
    }

    public String getKeyWord() {
        return keyWord;
    }

    public void setKeyWord(String keyWord) {
        this.keyWord = keyWord;
    }


    public UserDetiles getUser() {
        return user;
    }

    public void setUser(UserDetiles user) {

        this.user = user;
    }

}