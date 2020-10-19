package akshatchhaya.example.javaminiproject.api;

import android.app.Activity;
import android.util.Log;

import com.android.volley.AuthFailureError;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import akshatchhaya.example.javaminiproject.models.Model;
import akshatchhaya.example.javaminiproject.models.Variants;

public class DataRetrievalAPI extends API{
    private final OnDataResponse mListener;
    private static final String TAG = "DataRetrievalAPI";

    public DataRetrievalAPI(Activity caller, OnDataResponse listener){
        super(caller,null);
        this.mListener=listener;
    }

    public void getHotFlavours(){
        final String hotFlavorUrl=url+"/hotFlavours";
        flavoursRequest(hotFlavorUrl);
    }

    public void getFlavours(){
        final String flavour=url+"/flavours";
        flavoursRequest(flavour);
    }

    private void flavoursRequest(String url){
        getToken();
        StringRequest flavourRequest=new StringRequest(url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try{
                    JSONArray obj=new JSONArray(response);
                    final Model [] flavors=new Model[obj.length()];
                    for(int i=0;i<flavors.length;i++){
                        JSONObject rawFlavor=obj.getJSONObject(i);
                        String flavor=rawFlavor.getString("flavor");
                        String imageUrl=rawFlavor.getString("image");
                        String description=rawFlavor.getString("description");
                        JSONArray rawVariants=rawFlavor.getJSONArray("variants");
                        Model flavorModel=new Model(flavor,description,imageUrl,rawVariants);
                        flavors[i]=flavorModel;
                    }
                    mListener.onDataAvailable(flavors);
                }catch(JSONException e){
                    mListener.onFailure();
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                mListener.onFailure();
                Log.e(TAG, "onErrorResponse: error "+error);
            }
        }){
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String,String> headers=new HashMap<>();
                headers.put("authorization","Bearer "+authToken);
                return headers;
            }
        };

        mRequestQueue.add(flavourRequest);
    }

}
