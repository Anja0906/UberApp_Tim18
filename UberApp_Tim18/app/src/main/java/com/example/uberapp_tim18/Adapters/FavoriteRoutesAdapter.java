package com.example.uberapp_tim18.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.uberapp_tim18.R;

import java.util.List;

import DTO.FavoriteRoute;
import DTO.ReportItem;

public class FavoriteRoutesAdapter extends BaseAdapter {
    private Context context;
    private List<FavoriteRoute> items;

    public FavoriteRoutesAdapter(Context context, List<FavoriteRoute> items) {
        this.context = context;
        this.items = items;
    }
    @Override
    public int getCount() {
        return items.size();
    }

    @Override
    public Object getItem(int i) {
        return items.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        FavoriteRoute item = items.get(i);
        if (view == null) {
            LayoutInflater inflater = (LayoutInflater) context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.favorite_route_item, viewGroup, false);
        }
        TextView from = view.findViewById(R.id.from);
        if (item.getFrom().length()>15) {
            from.setText(item.getFrom().substring(0, 15)+"...");
        }
        else{
            from.setText(item.getFrom());
        }
        TextView to = view.findViewById(R.id.to);
        if (item.getTo().length()>15) {
            to.setText(item.getTo().substring(0, 15)+"...");
        }
        else{
            to.setText(item.getTo());
        }
        ImageView trash = view.findViewById(R.id.trash);
        Button order = view.findViewById(R.id.orderAgain);

        trash.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //izbrisati favorite rutu
            }
        });

        order.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //ponovo zakazati voznju
            }
        });

        return view;
    }
}
