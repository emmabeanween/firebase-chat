package com.example.emmagoldberg.firebasechat;

import android.content.Context;
import android.content.SharedPreferences;

public class SessionChat {

    private Context context;
    private SharedPreferences mPref;

    public SessionChat(Context mContext) {


        context = mContext;
        mPref  = context.getSharedPreferences("com.example.emmagoldberg.firebasechat",
               0);

    }


    public void setSessionUsername(String username){

        mPref.edit().putString("USERNAME", username).commit();

    }


    public String getUsernameinSession(){


        String stringtoReturn = mPref.getString("USERNAME", "");
        return stringtoReturn;
    }


    public void setRoomLastIn(String roomLastIn){

        mPref.edit().putString("ROOM_LAST", roomLastIn).commit();


    }

    public String getRoomLastIn(){

        String returnString = mPref.getString("ROOM_LAST", "");
        return returnString;

    }











}
