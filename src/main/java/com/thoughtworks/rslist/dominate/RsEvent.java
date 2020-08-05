package com.thoughtworks.rslist.dominate;

public class RsEvent {
    private String eventName;
    private String keyWord;
    private UserDetiles user;

    public  RsEvent(String eventName, String keyWord,UserDetiles user){
        this.eventName = eventName;
        this.keyWord = keyWord;
        this.user = user;
    }

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
