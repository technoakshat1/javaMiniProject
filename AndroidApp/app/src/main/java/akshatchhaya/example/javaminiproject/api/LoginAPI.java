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

public class LoginAPI extends API {
    private static final String TAG = "LoginAPI";

    public LoginAPI(Activity caller,OnResponseListener listener){
        super(caller,listener);
    }

    public void login(final String username,final String password){
        String loginUrl=url+"/login";


        StringRequest loginRequest=new StringRequest(Request.Method.POST, loginUrl, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d(TAG, "onResponse: response" + response);
                if(response!=null){
                    try{
                        JSONObject token=new JSONObject(response);
                        authToken=token.getString("token");
                        storeToken();
                        mListener.onSuccess();
                        Log.d(TAG, "onResponse: extracted token is "+authToken);
                    }catch (JSONException e){
                        mListener.onFailure(500);
                        e.printStackTrace();
                    }
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                mListener.onFailure(error.networkResponse.statusCode);
                Log.e(TAG, "onErrorResponse: error "+ error.networkResponse.statusCode );

            }
        }){

            @Override
            protected Map<String,String> getParams(){
                Map<String,String> params = new HashMap<String, String>();
                params.put("username",username);
                params.put("password",password);
                return params;
            }
        };

        mRequestQueue.add(loginRequest);

    }

    public void isLoggedIn(){
        String authUrl=url+"/login";
        getToken();
        if(authToken!=null){
            StringRequest isAuthRequest=new StringRequest(authUrl, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    try{
                        JSONObject obj=new JSONObject(response);
                        if(obj.getString("isAuthenticated").equals("true")){
                            mListener.onSuccess();
                        }
                    }catch(JSONException e){
                        e.printStackTrace();
                    }
                    Log.d(TAG, "onResponse: got a response"+response);

                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    if(error.networkResponse!=null){
                        mListener.onFailure(error.networkResponse.statusCode);
                        Log.e(TAG, "onErrorResponse: error "+error.networkResponse.statusCode);
                    }

                }
            }){
                @Override
                public Map<String, String> getHeaders() throws AuthFailureError {
                    Map<String,String> headers=new HashMap<>();
                    headers.put("authorization","Bearer "+authToken);
                    return headers;
                }
            };
            mRequestQueue.add(isAuthRequest);
        }else{
            mListener.onFailure(403);
        }
    }

    public void confirmPassword(final String password){
        String passwordUrl=url+"/login/confirmPassword";
        getToken();
        StringRequest cnfPassRequest=new StringRequest(Request.Method.POST, passwordUrl, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                 if(response!=null){
                     mListener.onSuccess();
                 }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e(TAG, "onErrorResponse: error"+error );
                mListener.onFailure(403);
            }
        }){
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String,String> headers=new HashMap<>();
                headers.put("authorization","Bearer "+authToken);
                return headers;
            }

            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> params=new HashMap<>();
                params.put("password",password);
                return params;
            }
        };

        mRequestQueue.add(cnfPassRequest);
    }

    public void resetPassword(final String newPassword){
        String passwordUrl=url+"/login/changePassword";
        getToken();
        StringRequest resetPassword=new StringRequest(Request.Method.POST, passwordUrl, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
               mListener.onSuccess();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e(TAG, "onErrorResponse: error"+error );
                mListener.onFailure(403);
            }
        }){
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String,String> headers=new HashMap<>();
                headers.put("authorization","Bearer "+authToken);
                return headers;
            }
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> params=new HashMap<>();
                params.put("newPassword",newPassword);
                return params;
            }
        };

        mRequestQueue.add(resetPassword);
    }



}
