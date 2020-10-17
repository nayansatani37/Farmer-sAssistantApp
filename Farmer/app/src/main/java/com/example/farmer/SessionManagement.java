package com.example.farmer;

import android.content.Context;
import android.content.SharedPreferences;

import java.util.HashMap;

public class SessionManagement {

    SharedPreferences userSession;
    SharedPreferences.Editor editor;
    Context context;

    //Session names
    public static final String SESSION_USERSESSION="userLoginSession";
    public static final String SESSION_REMEMBERME="rememberMe";



    //User Session Variable
    private static final String IS_LOGIN="IsLoggedIn";
    public static final String KEY_FIRSTNAME="firstname";
    public static final String KEY_LASTNAME="lastname";
    public static final String KEY_EMAIL="email";
    public static final String KEY_MOBILE="mobile";
    public static final String KEY_PASSWORD="password";



    //Remember me variables
    private static final String IS_REMEMBERME="IsRememberMe";
    public static final String KEY_SESSIONMOBILE="mobile";
    public static final String KEY_SESSIONPASSWORD="password";



    //Constructor
    public SessionManagement(Context _context,String sessionName)
    {
        context = _context;
        userSession=context.getSharedPreferences(sessionName,Context.MODE_PRIVATE);
        editor=userSession.edit();
    }


    //User login Session
    public void createLoginSession(String firstname,String lastname,String email,String mobile,String password)
    {
        //save session of user when user loggedin
        editor.putBoolean(IS_LOGIN,true);

        editor.putString(KEY_FIRSTNAME,firstname);
        editor.putString(KEY_LASTNAME,lastname);
        editor.putString(KEY_EMAIL,email);
        editor.putString(KEY_MOBILE,mobile);
        editor.putString(KEY_PASSWORD,password);

        editor.commit();

    }
    public HashMap<String,String> getUsersDetailFromSession()
    {
        HashMap<String,String> userData=new HashMap<String,String>();
        userData.put(KEY_FIRSTNAME,userSession.getString(KEY_FIRSTNAME,null));
        userData.put(KEY_LASTNAME,userSession.getString(KEY_LASTNAME,null));
        userData.put(KEY_EMAIL,userSession.getString(KEY_EMAIL,null));
        userData.put(KEY_MOBILE,userSession.getString(KEY_MOBILE,null));
        userData.put(KEY_PASSWORD,userSession.getString(KEY_PASSWORD,null));


        return userData;
    }
    public boolean checkLogin()
    {
        if (userSession.getBoolean(IS_LOGIN,false))
        {
            return true;
        }
        else
        {
            return false;
        }
    }
    public void logOutUserFromSession()
    {
        editor.clear();
        editor.commit();
    }



    //Remember me Session FUnction

    public void createRememberMeSession(String mobile,String password)
    {
        //save session of user when user loggedin
        editor.putBoolean(IS_REMEMBERME,true);

        editor.putString(KEY_SESSIONMOBILE,mobile);
        editor.putString(KEY_SESSIONPASSWORD,password);

        editor.commit();

    }

    public HashMap<String,String> getRememberMeDetailFromSession()
    {
        HashMap<String,String> userData=new HashMap<String,String>();

        userData.put(KEY_SESSIONMOBILE,userSession.getString(KEY_SESSIONMOBILE,null));
        userData.put(KEY_SESSIONPASSWORD,userSession.getString(KEY_SESSIONPASSWORD,null));


        return userData;
    }

    public boolean checkRememberMe()
    {
        if (userSession.getBoolean(IS_REMEMBERME,false))
        {
            return true;
        }
        else
        {
            return false;
        }
    }

}
