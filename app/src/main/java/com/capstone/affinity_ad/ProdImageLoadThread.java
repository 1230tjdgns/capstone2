package com.capstone.affinity_ad;

import static android.content.ContentValues.TAG;

import android.graphics.BitmapFactory;
import android.net.Uri;
import android.util.Log;
import android.widget.ImageView;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class ProdImageLoadThread extends Thread{
    String id;
    ImageView v;
    ProdImageLoadThread(String id, ImageView v) {
        this.id = id;
        this.v = v;
    }

    @Override
    public void run() {
        try {
            String l = id;
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
            v.setImageBitmap(BitmapFactory.decodeStream(is)); // Bitmap으로 변환

        } catch (MalformedURLException e) {
            e.printStackTrace();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
