package com.lpmarlo.iris.commons.models;

public class Lender {

    private String id;
    private String name;
    private String surnames;
    private String email;
    private String phoneNumber;
    private String password;
    private String birthday;
    private String iban;
    private String profilePhoto;

    public Lender() {
        // Default constructor required for calls to DataSnapshot.getValue(User.class)
    }

    public Lender(String id, String name, String surnames, String email, String phoneNumber, String password, String birthday) {
        this.id = id;
        this.name = name;
        this.surnames = surnames;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.password = password;
        this.birthday = birthday;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurnames() {
        return surnames;
    }

    public void setSurnames(String surnames) {
        this.surnames = surnames;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public String getIban() {
        return iban;
    }

    public void setIban(String iban) {
        this.iban = iban;
    }

    public String getProfilePhoto() {
        return profilePhoto;
    }

    public void setProfilePhoto(String profilePhoto) {
        this.profilePhoto = profilePhoto;
    }
}
