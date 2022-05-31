package com.lpmarlo.iris.commons.models;

import com.google.type.DateTime;

import java.util.Date;

public class Loan {

    private String id;
    private String description;
    private Date createDate;
    private Date deadline;
    private String status;
    private double paidAmount;
    private double borrowedAmount;
    private double requestedAmount;
    private double interest;
    private int numberOfFees;
    private String borrowerId;

    public Loan(String requestAmount, String description, String borrowerId, Date createDate, double interest, String pending, int numberOfFees, Date deadline) {
        this.requestedAmount = Double.parseDouble(requestAmount);
        this.description = description;
        this.borrowerId = borrowerId;
        this.createDate = createDate;
        this.deadline = deadline;
        this.interest = interest;
        this.status = pending;
        this.numberOfFees = numberOfFees;
    }

    public int getNumberOfFees() {
        return numberOfFees;
    }

    public void setNumberOfFees(int numberOfFees) {
        this.numberOfFees = numberOfFees;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public Date getDeadline() {
        return deadline;
    }

    public void setDeadline(Date deadline) {
        this.deadline = deadline;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String state) {
        this.status = status;
    }

    public double getPaidAmount() {
        return paidAmount;
    }

    public void setPaidAmount(double paidAmount) {
        this.paidAmount = paidAmount;
    }

    public double getBorrowedAmount() {
        return borrowedAmount;
    }

    public void setBorrowedAmount(double borrowedAmount) {
        this.borrowedAmount = borrowedAmount;
    }

    public double getRequestedAmount() {
        return requestedAmount;
    }

    public void setRequestedAmount(double requestedAmount) {
        this.requestedAmount = requestedAmount;
    }

    public double getInterest() {
        return interest;
    }

    public void setInterest(double interest) {
        this.interest = interest;
    }

    public String getBorrowerId() {
        return borrowerId;
    }

    public void setBorrowerId(String borrowerId) {
        this.borrowerId = borrowerId;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
