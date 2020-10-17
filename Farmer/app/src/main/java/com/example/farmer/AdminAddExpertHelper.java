package com.example.farmer;

public class AdminAddExpertHelper
{
    String expertFirstname,expertLastname,expertEmail,expertMobile,expertPassword,expertAddress;

    public AdminAddExpertHelper() {
    }

    public AdminAddExpertHelper(String expertFirstname, String expertLastname, String expertEmail, String expertMobile, String expertPassword, String expertAddress) {
        this.expertFirstname = expertFirstname;
        this.expertLastname = expertLastname;
        this.expertEmail = expertEmail;
        this.expertMobile = expertMobile;
        this.expertPassword = expertPassword;
        this.expertAddress = expertAddress;
    }

    public String getExpertFirstname() {
        return expertFirstname;
    }

    public void setExpertFirstname(String expertFirstname) {
        this.expertFirstname = expertFirstname;
    }

    public String getExpertLastname() {
        return expertLastname;
    }

    public void setExpertLastname(String expertLastname) {
        this.expertLastname = expertLastname;
    }

    public String getExpertEmail() {
        return expertEmail;
    }

    public void setExpertEmail(String expertEmail) {
        this.expertEmail = expertEmail;
    }

    public String getExpertMobile() {
        return expertMobile;
    }

    public void setExpertMobile(String expertMobile) {
        this.expertMobile = expertMobile;
    }

    public String getExpertPassword() {
        return expertPassword;
    }

    public void setExpertPassword(String expertPassword) {
        this.expertPassword = expertPassword;
    }

    public String getExpertAddress() {
        return expertAddress;
    }

    public void setExpertAddress(String expertAddress) {
        this.expertAddress = expertAddress;
    }
}
