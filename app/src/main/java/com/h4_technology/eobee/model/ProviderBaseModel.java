package com.h4_technology.eobee.model;

/**
 * Created by user on 5/31/2015.
 * {"npi":"1588612642",
 * "LastOrgName":"SANIUK",
 * "FirstName":"ROBERT",
 * "MI":"J",
 * "Credentials":"M.D.",
 * "ChargeAmount":125.0000000000,
 * "ProcedureDescriptionId":2202,
 * "Distance":3.01213336,
 * "DistanceFilter":5}
 */
public class ProviderBaseModel {
    public final String npi;
    public final int ProcedureDescriptionId;
    public final String LastOrgName;
    public final String FirstName;
    public final String MI;
    public ProviderBaseModel (String npi, int procedureDescriptionId, String lastOrgName, String firstName, String mi){
        this.npi = npi;
        this.ProcedureDescriptionId = procedureDescriptionId;
        this.LastOrgName = lastOrgName;
        this.FirstName = firstName;
        this.MI = mi;
    }
}
