package com.example.farmer;

public class AdminTipHelperClass {
    String tiptitle,tipdesc;

    public AdminTipHelperClass() {

    }

    public AdminTipHelperClass(String tiptitle, String tipdesc) {
        this.tiptitle = tiptitle;
        this.tipdesc = tipdesc;
    }

    public String getTiptitle() {
        return tiptitle;
    }

    public void setTiptitle(String tiptitle) {
        this.tiptitle = tiptitle;
    }

    public String getTipdesc() {
        return tipdesc;
    }

    public void setTipdesc(String tipdesc) {
        this.tipdesc = tipdesc;
    }
}
