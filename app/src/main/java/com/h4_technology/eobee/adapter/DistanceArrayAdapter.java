package com.h4_technology.eobee.adapter;

import android.content.Context;
import android.graphics.Typeface;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by user on 5/28/2015.
 */
public class DistanceArrayAdapter extends ArrayAdapter<String> {
    private Context context;
    private ArrayList<String> distanceItems;
    Typeface font;

    public DistanceArrayAdapter(Context context, int resource, ArrayList<String> distanceItems){
        super(context, resource, distanceItems);
        this.context = context;
        this.distanceItems = distanceItems;
        font = Typeface.createFromAsset(getContext().getAssets(), "Quicksand_Bold.otf");
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        TextView view = (TextView) super.getView(position, convertView, parent);
        view.setTypeface(font);
        return view;
    }

    @Override
    public View getDropDownView(int position, View convertView, ViewGroup parent) {
        TextView view = (TextView) super.getDropDownView(position, convertView, parent);
        view.setTypeface(font);
        return view;
    }
}
