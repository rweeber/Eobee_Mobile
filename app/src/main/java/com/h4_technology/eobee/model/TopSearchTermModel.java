package com.h4_technology.eobee.model;

/**
 * Created by user on 5/29/2015.
 */
public class TopSearchTermModel {
    public int mSearchTermId;
    public String mSearchTerm;
    public TopSearchTermModel() {

    }
    public TopSearchTermModel(int searchTermId, String searchTerm) {
        this.mSearchTermId = searchTermId;
        this.mSearchTerm = searchTerm;
    }

}
