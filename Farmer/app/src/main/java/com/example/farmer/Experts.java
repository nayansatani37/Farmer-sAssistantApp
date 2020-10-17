package com.example.farmer;

public class Experts  {

    public Experts() {
    }
    //String id;
    String firstname,lastame,email,mobile,password,address;

    public Experts(String firstname, String lastame, String email, String mobile, String password,String address) {
        this.firstname = firstname;
        this.lastame = lastame;
        this.email = email;
        this.mobile = mobile;
        this.password = password;
        this.address=address;
        //this.id=id;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastame() {
        return lastame;
    }

    public void setLastame(String lastame) {
        this.lastame = lastame;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

//    public String getId() {
//        return id;
//    }
//
//    public void setId(String id) {
//        this.id = id;
//    }
}
