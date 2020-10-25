package akshatchhaya.example.javaminiproject.api;

import android.app.Activity;
import android.content.SharedPreferences;

import com.android.volley.RequestQueue;

class API {
     String authToken;
     final String url="http://192.168.43.64:3000";
     final Activity mCaller;
     final OnResponseListener mListener;
     final VolleyRequestController mRequestController;
     final RequestQueue mRequestQueue;

    public static final String SHARED_PREFERENCE ="SHARED_PREFERENCE";
    public static final String AUTH_TOKEN="AUTH_TOKEN";

     API(Activity caller,OnResponseListener listener){
        if(caller!=null){
            this.mCaller=caller;
            this.mListener=listener;
            this.mRequestController=VolleyRequestController.getInstance(caller);
            this.mRequestQueue=this.mRequestController.getRequestQueue();
        }else{
            throw new NullPointerException("Caller can't be null in API Constructor call");
        }

    }



    void storeToken(){
        SharedPreferences pref=mCaller.getSharedPreferences(SHARED_PREFERENCE, Activity.MODE_PRIVATE);
        SharedPreferences.Editor editor=pref.edit();
        editor.putString(AUTH_TOKEN,authToken);
        editor.apply();
    }

    void getToken(){
        SharedPreferences pref=mCaller.getSharedPreferences(SHARED_PREFERENCE,Activity.MODE_PRIVATE);
        authToken=pref.getString(AUTH_TOKEN,null);
    }

    void deleteToken(){
        SharedPreferences pref=mCaller.getSharedPreferences(SHARED_PREFERENCE,Activity.MODE_PRIVATE);
        SharedPreferences.Editor editor=pref.edit();
        editor.remove(AUTH_TOKEN).apply();
    }

}
