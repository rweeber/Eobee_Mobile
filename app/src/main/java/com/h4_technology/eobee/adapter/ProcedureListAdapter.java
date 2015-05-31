package com.h4_technology.eobee.adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.h4_technology.eobee.R;
import com.h4_technology.eobee.model.ProcedureListItem;

import java.util.List;

/**
 * Created by user on 5/30/2015.
 */
public class ProcedureListAdapter extends ArrayAdapter {
    private Context context;
    private boolean useList = true;

    public ProcedureListAdapter(Context context, List items) {
        super(context, android.R.layout.simple_list_item_1, items);
        this.context = context;
    }

    public ProcedureListAdapter(Context context, int resource, List objects) {
        super(context, resource, objects);
    }

    private class ViewHolder {
        TextView procedureName;
    }

    /**
     *
     * @param position
     * @param convertView
     * @param parent
     * @return
     */
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        ProcedureListItem item = (ProcedureListItem)getItem(position);
        View viewToUse = null;

        // This block exists to inflate the settings list item conditionally based on whether
        // we want to support a grid or list view.
        LayoutInflater mInflater = (LayoutInflater) context
                .getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
        if (convertView == null) {
            if(useList){
                viewToUse = mInflater.inflate(R.layout.procedure_list_item, null);
            } else {
                viewToUse = mInflater.inflate(R.layout.procedure_list_item, null);
            }

            holder = new ViewHolder();
            holder.procedureName = (TextView)viewToUse.findViewById(R.id.lbl_procedure_name);
            viewToUse.setTag(holder);
        } else {
            viewToUse = convertView;
            holder = (ViewHolder) viewToUse.getTag();
        }

        holder.procedureName.setText(item.getProcedureName());
        return viewToUse;
    }

}
