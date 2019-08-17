package com.bmmuradov.core;

public class MessagesData {

    //properties
    String message;
    long time;
    String sender;
    String receiver;
    String senderName;
    boolean chatted;

    //constructors
    public MessagesData() {
    }

    public MessagesData(String message, String sender) {
        this.message=message;
        this.sender=sender;
        time=0;
        chatted=false;
    }

    //methods
    public String getMessage(){
        return message;
    }

    public long getTime(){
        return time;
    }


    public String getSender() {
        return sender;
    }

    public void setMessage(String message) {
        this.message=message;
    }

    public void setTime(long time) {
        this.time=time;
    }

    public void setSender(String sender) {
        this.sender=sender;
    }

    public String getReceiver(){
        return receiver;
    }

    public void setReceiver(String username) {
        this.receiver=username;
    }

    public String getSenderName() {
        return senderName;
    }

    public void setSenderName(String senderName) {
        this.senderName = senderName;
    }


    public boolean isChatted() {
        return chatted;
    }

    public void setChatted(boolean chatted) {
        this.chatted = chatted;
    }

}
