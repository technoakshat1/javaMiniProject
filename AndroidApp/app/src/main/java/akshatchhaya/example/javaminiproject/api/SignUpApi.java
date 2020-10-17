package akshatchhaya.example.javaminiproject.api;

import android.app.Activity;
import android.util.Log;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class SignUpApi extends API{

    private static final String TAG = "SignUpApi";

    public SignUpApi(Activity caller,OnResponseListener listener){
        super(caller,listener);
    }

    public void signUp(final String username,final String password){
        String signUpUrl=url+"/signUp";
        StringRequest signUpRequest=new StringRequest(Request.Method.POST, signUpUrl, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
              try{
                  JSONObject obj=new JSONObject(response);
                  authToken=obj.getString("token");
                  storeToken();
                  mListener.onSuccess();
              }catch (JSONException e){
                  e.printStackTrace();
              }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                mListener.onFailure(error.networkResponse.statusCode);
                Log.e(TAG, "onErrorResponse: error in signing up"+error );
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> params=new HashMap<>();
                params.put("username",username);
                params.put("password",password);
                return params;
            }
        };

        mRequestQueue.add(signUpRequest);
    }
}
