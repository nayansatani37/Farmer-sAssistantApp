package com.example.farmer;

import android.content.Context;
import android.content.SharedPreferences;

import java.util.HashMap;

public class SessionManagementExperts {

    SharedPreferences expertSession;
    SharedPreferences.Editor editor;
    Context context;


    //Session names
    public static final String SESSION_EXPERTSESSION="expertLoginSession";


    //Experts Session Variable
    private static final String IS_EXLOGIN="IsExLoggedIn";
    public static final String KEY_EXFIRSTNAME="Exfirstname";
    public static final String KEY_EXLASTNAME="Exlastname";
    public static final String KEY_EXEMAIL="Exemail";
    public static final String KEY_EXMOBILE="Exmobile";
    public static final String KEY_EXPASSWORD="Expassword";

    //Constructor
    public SessionManagementExperts(Context _excontext,String sessionName)
    {
        context = _excontext;
        expertSession=context.getSharedPreferences(sessionName,Context.MODE_PRIVATE);
        editor=expertSession.edit();
    }


    //User login Session
    public void createLoginSession(String exfirstname,String exlastname,String exemail,String exmobile,String expassword)
    {
        //save session of user when user loggedin
        editor.putBoolean(IS_EXLOGIN,true);

        editor.putString(KEY_EXFIRSTNAME,exfirstname);
        editor.putString(KEY_EXLASTNAME,exlastname);
        editor.putString(KEY_EXEMAIL,exemail);
        editor.putString(KEY_EXMOBILE,exmobile);
        editor.putString(KEY_EXPASSWORD,expassword);

        editor.commit();

    }
    public HashMap<String,String> getUsersDetailFromSession()
    {
        HashMap<String,String> expertData=new HashMap<String,String>();
        expertData.put(KEY_EXFIRSTNAME,expertSession.getString(KEY_EXFIRSTNAME,null));
        expertData.put(KEY_EXLASTNAME,expertSession.getString(KEY_EXLASTNAME,null));
        expertData.put(KEY_EXEMAIL,expertSession.getString(KEY_EXEMAIL,null));
        expertData.put(KEY_EXMOBILE,expertSession.getString(KEY_EXMOBILE,null));
        expertData.put(KEY_EXPASSWORD,expertSession.getString(KEY_EXPASSWORD,null));


        return expertData;
    }
}
