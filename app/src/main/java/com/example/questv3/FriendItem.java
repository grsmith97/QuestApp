package com.example.questv3;

public class FriendItem {

    String fName, fEmail;

    public FriendItem() {
    }

    public FriendItem(String fName, String fEmail) {
        this.fName = fName;
        this.fEmail = fEmail;
    }

    public String getfName() {
        return fName;
    }

    public void setfName(String fName) {
        this.fName = fName;
    }

    public String getfEmail() {
        return fEmail;
    }

    public void setfEmail(String fEmail) {
        this.fEmail = fEmail;
    }
}
