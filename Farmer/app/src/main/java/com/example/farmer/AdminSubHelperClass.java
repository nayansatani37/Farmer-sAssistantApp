package com.example.farmer;

public class AdminSubHelperClass {
    String subname,subdetails,subwhocanget,subrequiredform,sublimitperyear,substartingdate,subendingdate;

    public AdminSubHelperClass() {

    }


    public AdminSubHelperClass(String subname, String subdetails, String subwhocanget, String subrequiredform, String sublimitperyear, String substartingdate, String subendingdate) {
        this.subname = subname;
        this.subdetails = subdetails;
        this.subwhocanget = subwhocanget;
        this.subrequiredform = subrequiredform;
        this.sublimitperyear = sublimitperyear;
        this.substartingdate = substartingdate;
        this.subendingdate = subendingdate;
    }

    public String getSubname() {
        return subname;
    }

    public void setSubname(String subname) {
        this.subname = subname;
    }

    public String getSubdetails() {
        return subdetails;
    }

    public void setSubdetails(String subdetails) {
        this.subdetails = subdetails;
    }

    public String getSubwhocanget() {
        return subwhocanget;
    }

    public void setSubwhocanget(String subwhocanget) {
        this.subwhocanget = subwhocanget;
    }

    public String getSubrequiredform() {
        return subrequiredform;
    }

    public void setSubrequiredform(String subrequiredform) {
        this.subrequiredform = subrequiredform;
    }

    public String getSublimitperyear() {
        return sublimitperyear;
    }

    public void setSublimitperyear(String sublimitperyear) {
        this.sublimitperyear = sublimitperyear;
    }

    public String getSubstartingdate() {
        return substartingdate;
    }

    public void setSubstartingdate(String substartingdate) {
        this.substartingdate = substartingdate;
    }

    public String getSubendingdate() {
        return subendingdate;
    }

    public void setSubendingdate(String subendingdate) {
        this.subendingdate = subendingdate;
    }
}
