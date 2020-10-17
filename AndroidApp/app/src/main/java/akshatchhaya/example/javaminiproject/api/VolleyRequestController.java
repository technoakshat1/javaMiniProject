package akshatchhaya.example.javaminiproject.api;

import android.content.Context;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

class VolleyRequestController {
    private static VolleyRequestController instance;
    private RequestQueue requestQueue;


    private VolleyRequestController(Context context) {
        requestQueue = Volley.newRequestQueue(context.getApplicationContext());
    }

    public static VolleyRequestController getInstance(Context context) {
        if (instance == null) {
            instance = new VolleyRequestController(context);
        }
        return  instance;
    }

    public RequestQueue getRequestQueue(){
        return requestQueue;
    }
}
