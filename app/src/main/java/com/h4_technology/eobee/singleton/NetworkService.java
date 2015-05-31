package com.h4_technology.eobee.singleton;

import android.content.Context;
import android.net.Network;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

/**
 * Created by user on 5/29/2015.
 */
public class NetworkService {
    private static NetworkService mInstance;
    private RequestQueue mRequestQueue;
    private static Context mCtx;

    private NetworkService(Context context) {
        mCtx = context;
        mRequestQueue = getRequestQueue();
    }

    public static synchronized NetworkService getInstance(Context context) {
        if (mInstance == null) {
            mInstance = new NetworkService(context);
        }
        return mInstance;
    }

    public RequestQueue getRequestQueue() {
        if (mRequestQueue == null) {
            // getApplicationContext() is key, it keeps you from leaking the
            // Activity or BroadcastReceiver if someone passes one in.
            mRequestQueue = Volley.newRequestQueue(mCtx.getApplicationContext());
        }
        return mRequestQueue;
    }

    public <T> void addToRequestQueue(Request<T> req) {
        getRequestQueue().add(req);
    }

}
