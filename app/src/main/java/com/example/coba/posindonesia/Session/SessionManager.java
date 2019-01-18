package com.example.coba.posindonesia.Session;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import com.google.gson.Gson;

public class SessionManager {

    private static String TAG = SessionManager.class.getSimpleName();

    SharedPreferences pref;
    SharedPreferences.Editor editor;
    Context _context;

    private String yours;
    private String summary;
    private String resi;

    int PRIVATE_MODE = 0;

    private static final String PREF_NAME = "AndroidHiveLogin";
    private static final String KEY_IS_LOGGEDIN = "isLoggedIn";

    public SessionManager(Context context) {
        this._context = context;
        pref = _context.getSharedPreferences(PREF_NAME, PRIVATE_MODE);
        editor = pref.edit();
    }


    public void setResi(String resi){
        this.resi = resi;
        editor.putString("resi", resi);
        editor.commit();
        Log.d(TAG, "Set Resi session modified!");
    }

    public String getResi(){
        return pref.getString("resi", "-");
    }



    public void setSummary(String summary){
        this.summary = summary;
        editor.putString("summary", summary);
        editor.commit();
    }

    public String getSummary(){
        return pref.getString("summary", "-");
    }

    public void setYours(String yours){
        this.yours = yours;
        editor.putString("yours", yours);
        editor.commit();
    }

    public String getYours(){
        return pref.getString("yours", "-");
    }




}
