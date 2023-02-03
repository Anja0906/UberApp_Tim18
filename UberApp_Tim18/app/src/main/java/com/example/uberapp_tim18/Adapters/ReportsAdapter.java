package com.example.uberapp_tim18.Adapters;

import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.uberapp_tim18.R;

import java.util.List;

import DTO.ReportItem;

public class ReportsAdapter extends BaseAdapter {
    private Context context;
    private List<ReportItem> items;

    public ReportsAdapter(Context context, List<ReportItem> items) {
        this.context = context;
        this.items = items;
    }

    @Override
    public int getCount() {
        return items.size();
    }

    @Override
    public Object getItem(int position) {
        return items.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ReportItem item = items.get(position);
        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.driver_report_list_item, parent, false);
        }
        TextView title = convertView.findViewById(R.id.report_main);
        title.setText(item.getName());
        TextView description = convertView.findViewById(R.id.report_description);
        description.setText(item.getDescription());
        ImageView imageView = convertView.findViewById(R.id.image);
        imageView.setImageResource(item.getImagePath());

        return convertView;
    }
}