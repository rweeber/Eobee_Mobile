package com.h4_technology.eobee.model;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by user on 5/31/2015.
 *
 */
public class ProviderListItem extends ProviderBaseModel {
    public final String Credentials;
    public final double ChargeAmount;
    public final double Distance;
    public final int DistanceFilter;
    public ProviderListItem(String npi, int procedureDescriptionId, String lastOrgName, String firstName, String mi, String credentials, double chargeAmount, double distance, int distanceFilter){
        super(npi,procedureDescriptionId, lastOrgName, firstName, mi);
        this.Credentials = credentials;
        this.ChargeAmount = chargeAmount;
        this.Distance = distance;
        this.DistanceFilter = distanceFilter;
    }
    public static List<ProviderListItem> getNewIstanceList(JSONArray json) throws JSONException {
        List<ProviderListItem> providers = new ArrayList();
        for (int i = 0; i < json.length(); i++) {
            JSONObject jsObject = json.getJSONObject(i);
            ProviderListItem model = new ProviderListItem(
                    jsObject.getString("npi"),
                    jsObject.getInt("ProcedureDescriptionId"),
                    jsObject.getString("LastOrgName"),
                    jsObject.getString("FirstName"),
                    jsObject.getString("MI"),
                    jsObject.getString("Credentials"),
                    jsObject.getDouble("ChargeAmount"),
                    jsObject.getDouble("Distance"),
                    jsObject.getInt("DistanceFilter")
            );
            providers.add(model);
        }
        return providers;
    }

}
