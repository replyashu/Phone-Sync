package com.myphone.collector;

import java.util.Date;

/**
 * Created by ashkingsharma on 9/10/15.
 */
public class HistoryLog {
    private String name;
    private String contact;
    private String type;
    private String duration;

    public Long getDat() {
        return dat;
    }

    public void setDat(Long dat) {
        this.dat = dat;
    }

    private Long dat;

    public Date getCallDate() {
        return callDate;
    }

    public void setCallDate(Date callDate) {
        this.callDate = callDate;
    }

    private Date callDate;

    public HistoryLog(String duration, String type,
                      String contact, String name, Date date, Long dat) {
        this.duration = duration;
        this.type = type;
        this.contact = contact;
        this.name = name;
        this.callDate = date;
        this.dat = dat;
    }


    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


}
