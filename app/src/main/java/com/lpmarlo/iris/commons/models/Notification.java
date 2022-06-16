package com.lpmarlo.iris.commons.models;

import androidx.recyclerview.widget.RecyclerView;

public class Notification {

    private String id;
    private String lenderName;
    private String amount;
    private String date;


    public Notification(String lenderName, String amount, String date) {
        this.lenderName = lenderName;
        this.amount = amount;
        this.date = date;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getLenderName() {
        return lenderName;
    }

    public void setLenderName(String lenderName) {
        this.lenderName = lenderName;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
