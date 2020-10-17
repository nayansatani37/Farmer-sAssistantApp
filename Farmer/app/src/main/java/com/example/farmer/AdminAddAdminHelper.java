package com.example.farmer;

public class AdminAddAdminHelper {

    String adminfirstname,adminlastname,adminemail,adminmobile,adminpassword;

    public AdminAddAdminHelper() {

    }

    public AdminAddAdminHelper(String adminfirstname, String adminlastname, String adminemail, String adminmobile, String adminpassword) {
        this.adminfirstname = adminfirstname;
        this.adminlastname = adminlastname;
        this.adminemail = adminemail;
        this.adminmobile = adminmobile;
        this.adminpassword = adminpassword;
    }

    public String getAdminfirstname() {
        return adminfirstname;
    }

    public void setAdminfirstname(String adminfirstname) {
        this.adminfirstname = adminfirstname;
    }

    public String getAdminlastname() {
        return adminlastname;
    }

    public void setAdminlastname(String adminlastname) {
        this.adminlastname = adminlastname;
    }

    public String getAdminemail() {
        return adminemail;
    }

    public void setAdminemail(String adminemail) {
        this.adminemail = adminemail;
    }

    public String getAdminmobile() {
        return adminmobile;
    }

    public void setAdminmobile(String adminmobile) {
        this.adminmobile = adminmobile;
    }

    public String getAdminpassword() {
        return adminpassword;
    }

    public void setAdminpassword(String adminpassword) {
        this.adminpassword = adminpassword;
    }
}
