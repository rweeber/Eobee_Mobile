package com.h4_technology.eobee;

import android.content.Context;
import android.net.ConnectivityManager;

import com.h4_technology.eobee.model.TopSearchTermModel;

import org.apache.http.client.HttpClient;
import org.apache.http.impl.client.DefaultHttpClient;

import java.net.URI;

/**
 * Created by user on 5/29/2015.
 */
public class DataHelper {

    public DataHelper () {

    }

    public static String getBaseUrl() {
        return "http://eobeewebapi.azurewebsites.net/";
    }


}
