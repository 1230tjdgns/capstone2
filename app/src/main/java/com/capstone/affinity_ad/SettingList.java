package com.capstone.affinity_ad;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;

import java.util.ArrayList;
import java.util.List;

public class SettingList {
    RequestQueue queue;
    static public   List<String> l_id;
    static public   List<String> l_brand;
    static public  List<String> l_name;
    static public  List<String> l_price;
    static public  List<String> l_image;

    static public ProductLoadHandler productLoadHandler;


    SettingList(Context mContext){
        productLoadHandler = new ProductLoadHandler();
        //키값 리스트
        l_brand = new ArrayList<String>();
        l_name = new ArrayList<String>();
        l_price = new ArrayList<String>();
        l_image =new ArrayList<String>();
        l_id=new ArrayList<String>();

        String url ="https://640de3d61a18a5db83827295.mockapi.io/shoes/";

        if(queue == null) {
            queue = Volley.newRequestQueue(mContext);
        }

        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, url,
                null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                //응답받은 JSONObject에서 데이터 꺼내오기
                parseData(response);
                Log.d("TEST", l_brand.toString());
                productLoadHandler.sendEmptyMessage(1);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                //오류 발생 시 실행
                Toast.makeText(mContext, "error: " + error.toString()
                        , Toast.LENGTH_SHORT).show();
            }
        });
        queue.add(jsonArrayRequest);
    }

    private void parseData(JSONArray jsonArray) {

        try {
            for(int i = 0 ; i<jsonArray.length(); i++) {
                JSONObject jsonObject = jsonArray.getJSONObject(i);

                l_brand.add(jsonObject.getString("brand_name"));
                l_name.add(jsonObject.getString("product_name"));
                l_price.add(jsonObject.getString("price"));
                l_id.add(jsonObject.getString("id"));
                String str =jsonObject.getString("product_img");
                String imagerep = str.replaceAll("[\\[\\]\\\\]","");
                String[] imagelist=imagerep.split(",");

                l_image.add(imagelist[0]);

            }
        }catch(JSONException e){

            e.printStackTrace();
        }

    }

}