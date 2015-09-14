package com.myphone.collector;

/**
 * Created by ashkingsharma on 9/14/15.
 */
public class Favorite {

    private String emailOfUser;
    private String displayName;

    public Favorite(String emailOfUser, String displayName) {
        this.emailOfUser = emailOfUser;
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public String getEmailOfUser() {
        return emailOfUser;
    }

    public void setEmailOfUser(String emailOfUser) {
        this.emailOfUser = emailOfUser;
    }





}
