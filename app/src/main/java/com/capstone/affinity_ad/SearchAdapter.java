package com.capstone.affinity_ad;

import static android.content.ContentValues.TAG;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.ImageDecoder;
import android.net.Uri;
import android.nfc.Tag;
import android.os.Build;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;


import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.List;
/**
 * Created by Administrator on 2017-08-07.
 */

public class SearchAdapter extends BaseAdapter {

    private Context context;
    private List<String> lbrand;
    private List<String> lname;
    private List<String> lprice;

    private List<String> limage;
    private LayoutInflater inflate;
    private ViewHolder viewHolder;
    Bitmap bitmap;
    public SearchAdapter(List<String> lbrand,List<String> lname,List<String> lprice, List<String> limage,Context context){
        this.lbrand = lbrand;
        this.lname = lname;
        this.lprice = lprice;
        this.limage=limage;
        this.context = context;
        this.inflate = LayoutInflater.from(context);
    }
    @Override
    public int getCount() {
        return lname.size();
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup viewGroup) {
        if(convertView == null){
            convertView = inflate.inflate(R.layout.listview,null);

            viewHolder = new ViewHolder();
            viewHolder.item_brand = (TextView) convertView.findViewById(R.id.search_item_brand);
            viewHolder.item_name = (TextView) convertView.findViewById(R.id.search_item_name);
            viewHolder.item_price = (TextView) convertView.findViewById(R.id.search_item_price);
            viewHolder.item_image= (ImageView) convertView.findViewById(R.id.search_item_img);
            convertView.setTag(viewHolder);
        }else{
            viewHolder = (ViewHolder)convertView.getTag();
        }

        // 리스트에 있는 데이터를 리스트뷰 셀에 뿌린다.
        viewHolder.item_brand.setText(lbrand.get(position));

        viewHolder.item_name.setText(lname.get(position));

        viewHolder.item_price.setText(lprice.get(position));

//        Uri uri=Uri.parse(limage.get(position));
//        String uristr=uri.toString();

//        Glide.with(context).load(limage.get(position)).into(viewHolder.item_image);
        Thread mThread = new Thread() {
            @Override
            public void run() {
                try {
                    String l=limage.get(position);
                    String[] l2=l.split("/");
//                    for(String cut:l2) {
//                        Log.d(TAG, " : " +cut);
//                    }
                    Uri.Builder builder = new Uri.Builder()
                            .scheme("https")
                            .authority(l2[2])
                            .appendPath(l2[3])
                            .appendPath(l2[4])
                            .appendPath(l2[5])
                            ;

                    Uri myUri = builder.build();
                    Log.d(TAG, " : " +myUri.toString());
                    URL url = new URL(myUri.toString());

                    // Web에서 이미지를 가져온 뒤
                    // ImageView에 지정할 Bitmap을 만든다
                    HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                    conn.setDoInput(true); // 서버로 부터 응답 수신
                    conn.connect();

                    InputStream is = conn.getInputStream(); // InputStream 값 가져오기
                    bitmap = BitmapFactory.decodeStream(is); // Bitmap으로 변환

                } catch (MalformedURLException e) {
                    e.printStackTrace();

                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        };

        mThread.start(); // Thread 실행

        try {
            // 메인 Thread는 별도의 작업 Thread가 작업을 완료할 때까지 대기해야한다
            // join()를 호출하여 별도의 작업 Thread가 종료될 때까지 메인 Thread가 기다리게 한다
            mThread.join();

            // 작업 Thread에서 이미지를 불러오는 작업을 완료한 뒤
            // UI 작업을 할 수 있는 메인 Thread에서 ImageView에 이미지를 지정한다
            viewHolder.item_image.setImageBitmap(bitmap);

        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        return convertView;
    }

    class ViewHolder{
        public TextView item_brand;
        public TextView item_name;
        public TextView item_price;

        public ImageView item_image;
    }

}
