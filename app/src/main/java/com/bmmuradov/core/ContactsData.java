package com.bmmuradov.core;

//keeps contacts data
public class ContactsData {

    //properties
    public String email;
    public String contactPhone;
    public String username;
    public String uId;

    //constructors
    public ContactsData() {
    }

    public ContactsData(String email, String contactPhone, String uId, String username, boolean chatted) {
        this.email=email;
        this.contactPhone=contactPhone;
        this.uId=uId;
        this.username=username;
    }

    //methods
    public String getEmail() {
        return email;
    }

    public String getPhone() {
        return contactPhone;
    }

    public void setEmail(String newMail) {
        email=newMail;
    }

    public void setPhone(String phone) {
        contactPhone=phone;
    }

    public void setuId(String id) {
        uId=id;
    }

    public String getuId() {
        return uId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username=username;
    }



}
