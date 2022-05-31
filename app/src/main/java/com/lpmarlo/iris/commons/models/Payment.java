package com.lpmarlo.iris.commons.models;

public class Payment {

    private String id;
    private String lenderId;
    private String loanId;
    private String amount;
    private String date;

    public Payment(String id, String lenderId, String loanId, String amount, String date) {
        this.id = id;
        this.lenderId = lenderId;
        this.loanId = loanId;
        this.amount = amount;
        this.date = date;
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

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
