package com.h4_technology.eobee.model;

/**
 * Created by user on 5/30/2015.
 */
public class ProcedureListItem {
    private String procedureName;
    public String getProcedureName() {
        return this.procedureName;
    }

    public void setProcedureName(String name){
        this.procedureName = name;
    }

    public ProcedureListItem(String name) {
        this.procedureName = name;
    }
}
