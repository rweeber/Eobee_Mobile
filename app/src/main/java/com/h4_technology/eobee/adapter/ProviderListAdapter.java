package com.h4_technology.eobee.adapter;

import android.app.Activity;
import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.h4_technology.eobee.R;
import com.h4_technology.eobee.model.ProviderListItem;

import java.text.NumberFormat;
import java.util.List;

/**
 * Created by user on 5/31/2015.
 */
public class ProviderListAdapter extends ArrayAdapter {
    private Context context;
    private boolean useList = true;
    private NumberFormat formatter;

    public ProviderListAdapter(Context context, List<ProviderListItem> items) {
        super(context, R.layout.provider_list_item, items);
        this.context = context;
        formatter = NumberFormat.getCurrencyInstance();
    }

    private class ViewHolder {
        TextView charge;
        TextView lastOrgName;
        TextView distance;
        Typeface font = Typeface.createFromAsset(context.getAssets(),"Quicksand_Book.otf");
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
        ProviderListItem item = (ProviderListItem)getItem(position);
        View viewToUse = null;

        // This block exists to inflate the settings list item conditionally based on whether
        // we want to support a grid or list view.
        LayoutInflater mInflater = (LayoutInflater) context
                .getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
        if (convertView == null) {
            if(useList){
                viewToUse = mInflater.inflate(R.layout.provider_list_item, null);
            } else {
                viewToUse = mInflater.inflate(R.layout.provider_list_item, null);
            }

            holder = new ViewHolder();
            holder.charge = (TextView)viewToUse.findViewById(R.id.avg_charge);
            holder.charge.setTypeface(holder.font);
            holder.lastOrgName = (TextView)viewToUse.findViewById(R.id.last_org_name);
            holder.charge.setTypeface(holder.font);
            holder.distance = (TextView)viewToUse.findViewById(R.id.distance);
            holder.charge.setTypeface(holder.font);
            viewToUse.setTag(holder);
        } else {
            viewToUse = convertView;
            holder = (ViewHolder) viewToUse.getTag();
        }

        holder.charge.setText(formatter.format(item.ChargeAmount));//$123.12
        holder.lastOrgName.setText(item.LastOrgName);
        holder.distance.setText(String.valueOf(item.Distance));
        return viewToUse;
    }

}
