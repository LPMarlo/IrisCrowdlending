package com.lpmarlo.iris.commons.models;

import com.google.firebase.Timestamp;

import java.util.Date;

public class Payment {

    private String id;
    private String lenderId;
    private String loanId;
    private String amount;
    private Timestamp date;

    public Payment() {
    }

    public Payment(String lenderId, String loanId, String amount) {
        this.id = lenderId + System.currentTimeMillis() * Math.random() * 100;
        this.lenderId = lenderId;
        this.loanId = loanId;
        this.amount = amount;
        this.date = Timestamp.now();
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getLenderId() {
        return lenderId;
    }

    public void setLenderId(String lenderId) {
        this.lenderId = lenderId;
    }

    public String getLoanId() {
        return loanId;
    }

    public void setLoanId(String loanId) {
        this.loanId = loanId;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public Timestamp getDate() {
        return date;
    }

    public void setDate(Timestamp date) {
        this.date = date;
    }
}
