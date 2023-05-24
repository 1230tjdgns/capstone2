package com.capstone.affinity_ad;

import static android.content.ContentValues.TAG;

import android.content.Context;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
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
            ImageView img = (ImageView) view.findViewById(R.id.home_item_img);
            TextView brand = (TextView) view.findViewById(R.id.home_item_brand);
            TextView name = (TextView) view.findViewById(R.id.home_item_name);
            TextView price = (TextView) view.findViewById(R.id.home_item_price);

            ProdImageLoadThread th = new ProdImageLoadThread(g_items.getImg(), img);


//            try {
//                th.start();
//                th.join();
//            }
//            catch(Exception e) {
//
//            }

            brand.setText(g_items.getBrand());
            name.setText(g_items.getName());
            price.setText(g_items.getPrice());

        } else {
            View v = new View(context);
            v = (View) view;
        }
        //각 아이템 선택 event
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(context, g_items.getId(), Toast.LENGTH_SHORT).show();
            }
        });


        return view;
    }


}

