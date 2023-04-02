package com.capstone.affinity_ad;

import static android.content.ContentValues.TAG;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.google.gson.JsonParser;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;
import java.util.TimerTask;

public class SettingList {
    RequestQueue queue;
    String good;
    private  ArrayList<List> list;
    private  List<String> l_brand;
    private  List<String> l_name;
    private  List<String> l_price;
    private  List<String> l_image;

    SettingList(Context mContext){
        //키값 리스트
        list= new ArrayList<List>() ;
        l_brand = new ArrayList<String>();
        l_name = new ArrayList<String>();
        l_price = new ArrayList<String>();
        l_image =new ArrayList<String>();
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

//        Log.d(TAG,jsonArrayRequest.toString());
    }

    public ArrayList<List> getlist() {
        list.add(l_brand);
        list.add(l_name);
        list.add(l_price);
        list.add(l_image);
        return list;
    }

    private void parseData(JSONArray jsonArray) {


        //Toast.makeText(MainActivity.this, fire, Toast.LENGTH_SHORT).show();
        try {
            //data 담기
            //String jsonstring=jsonArray.getString(0);
            //JSONArray jsonA =jsonArray.put(jsonstring);
            //JSONObject jsonObject = jsonArray.getJSONObject(0);
            //Log.d(TAG," : "+ jsonObject.getString("brand_name"));
            for(int i = 0 ; i<jsonArray.length(); i++) {
                //Log.d(TAG,i+" : "+ jsonA.toString());
                JSONObject jsonObject = jsonArray.getJSONObject(i);

                l_brand.add(jsonObject.getString("brand_name"));
                l_name.add(jsonObject.getString("product_name"));
                l_price.add(jsonObject.getString("price"));
                String str =jsonObject.getString("product_img");
                String imagerep = str.replaceAll("[\\[\\]\\\\]","");
                String[] imagelist=imagerep.split(",");

                l_image.add(imagelist[0]);

                //Log.d(TAG," : "+ imagelist[0]);
            }
        }catch(JSONException e){

            e.printStackTrace();
        }

    }


}
