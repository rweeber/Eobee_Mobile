package com.h4_technology.eobee.model;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by user on 5/30/2015.
 */
public class ProcedureListItem {
    private String procedureName;
    public String getProcedureName() {
        return this.procedureName;
    }

    private int procedureDescriptionId;
    public int getProcedureDescriptionId(){
        return this.procedureDescriptionId;
    }

    public void setProcedureDescriptionId(int id){
        this.procedureDescriptionId = id;
    }

    public void setProcedureName(String name){
        this.procedureName = name;
    }

    public ProcedureListItem(String name, int id) {
        this.procedureName = name;
        this.procedureDescriptionId = id;
    }

}
