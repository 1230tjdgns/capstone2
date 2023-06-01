package com.capstone.affinity_ad;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

class GridViewAdapter extends BaseAdapter {
    ArrayList<gridItem> items = new ArrayList<gridItem>();
    Bitmap bitmap;
    BitmapList bitmapList;

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


            Thread mThread = new Thread() {
                @Override
                public void run() {
                    try {
                        String l=g_items.getImg();
                        Bitmap temp = BitmapList.checkHash(g_items.getId());
                        if(temp != null) {
                            bitmap = temp;
                        }
                        else {
                            String[] l2=l.split("/");

                            Uri.Builder builder = new Uri.Builder()
                                    .scheme("https")
                                    .authority(l2[2])
                                    .appendPath(l2[3])
                                    .appendPath(l2[4])
                                    .appendPath(l2[5])
                                    ;

                            Uri myUri = builder.build();
                            URL url = new URL(myUri.toString());

                            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                            conn.setDoInput(true);
                            conn.connect();

                            InputStream is = conn.getInputStream();
                            bitmap = BitmapFactory.decodeStream(is);

                            BitmapList.bitmaps.put(g_items.getId(),bitmap);
                        }


                    } catch (MalformedURLException e) {
                        e.printStackTrace();

                    } catch (IOException e) {
                        e.printStackTrace();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            };

            mThread.start(); // Thread 실행

            try {
                mThread.join();

                img.setImageBitmap(bitmap);

            } catch (InterruptedException e) {
                e.printStackTrace();
            }

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
                Intent intent =new Intent(context, ProductActivity.class);
                intent.putExtra("id",g_items.getId());
                context.startActivity(intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK));
            }
        });


        return view;
    }


}

