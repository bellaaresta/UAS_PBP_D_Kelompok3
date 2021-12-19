package com.example.uts_pbp_d_kelompok_3;

import android.content.Context;
import android.content.SharedPreferences;

public class TrackingPreferences {
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    Context context;

    public final static String IS_CEK = "isCek";
    public final static String KEY_TRACKING = "NoTracking";

    public TrackingPreferences(Context context){
        this.context = context;
        sharedPreferences = context.getSharedPreferences("trackingPreferences", Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
    }

    public void setTracking(String NoTracking){
        editor.putBoolean(IS_CEK,true);
        editor.putString(KEY_TRACKING, NoTracking);

        editor.commit();
    }

    public Tracking getTrackingCek(){
        String NoTracking;

        NoTracking = sharedPreferences.getString(KEY_TRACKING,null);
        return new Tracking(NoTracking);
    }

    public boolean checkTracking()
    {
        return sharedPreferences.getBoolean(IS_CEK,false);
    }
}
