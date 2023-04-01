package com.capstone.affinity_ad;

import static android.content.ContentValues.TAG;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

class GridViewAdapter extends BaseAdapter {
    ArrayList<gridItem> items = new ArrayList<gridItem>();


    public void clearItem() {
        items = new ArrayList<gridItem>();
    }

    public void addItem(gridItem item) {
        items.add(item);
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
        final Context context = viewGroup.getContext();
        final gridItem g_items = items.get(i);

        if (view == null) {
            LayoutInflater inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.home_grid_item, viewGroup, false);

            TextView brand = (TextView) view.findViewById(R.id.home_item_brand);
            TextView name = (TextView) view.findViewById(R.id.home_item_name);
            TextView price = (TextView) view.findViewById(R.id.home_item_price);

            brand.setText(g_items.getBrand());
            name.setText(g_items.getName());
            price.setText(g_items.getPrice());
            Log.d(TAG, "getView() - [ "+i+" ] "+g_items.getName());

        } else {
            View v = new View(context);
            v = (View) view;
        }
        //각 아이템 선택 event
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(context, g_items.getBrand()+" - "+g_items.getName()+" - " + g_items.getPrice(), Toast.LENGTH_SHORT).show();
            }
        });
        return view;
    }
}