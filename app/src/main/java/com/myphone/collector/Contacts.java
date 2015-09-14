package com.myphone.collector;

import android.net.Uri;

/**
 * Created by ashkingsharma on 9/11/15.
 */
public class Contacts {

    private String contactName;
    private String contactNum;
    private Uri profilePic;

    private String nickName;
    private String timesContacted;
    private String timesUsed;
    private String presence;
    private String status;
    private String label;
    private String d;

    public Contacts(String contactName, String contactNum,
                    Uri profilePic, String nickName, String timesContacted,
                    String timesUsed, String presence, String status,
                    String label, String d) {
        this.contactName = contactName;
        this.contactNum = contactNum;
        this.profilePic = profilePic;
        this.nickName = nickName;
        this.timesContacted = timesContacted;
        this.timesUsed = timesUsed;
        this.presence = presence;
        this.status = status;
        this.label = label;
        this.d = d;
    }


    public String getD() {
        return d;
    }

    public void setD(String d) {
        this.d = d;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getPresence() {
        return presence;
    }

    public void setPresence(String presence) {
        this.presence = presence;
    }

    public String getTimesUsed() {
        return timesUsed;
    }

    public void setTimesUsed(String timesUsed) {
        this.timesUsed = timesUsed;
    }

    public String getTimesContacted() {
        return timesContacted;
    }

    public void setTimesContacted(String timesContacted) {
        this.timesContacted = timesContacted;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public String getContactNum() {
        return contactNum;
    }

    public void setContactNum(String contactNum) {
        this.contactNum = contactNum;
    }

    public String getContactName() {
        return contactName;
    }

    public void setContactName(String contactName) {
        this.contactName = contactName;
    }

    public Uri getProfilePic(){
        return profilePic;
    }

    public void setProfilePic(Uri profilePic){
        this.profilePic = profilePic;
    }

}
