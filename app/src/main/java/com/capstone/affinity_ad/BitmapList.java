package com.capstone.affinity_ad;

import android.graphics.Bitmap;

import java.util.ArrayList;

public class BitmapList {
    static public ArrayList<Bitmap> bitmaps = new ArrayList<Bitmap>();
    static public ArrayList<String> bitmapurls = new ArrayList<String>();


    static public int checkBitmap(String u) {
        int index = -1;
        for (int i = 0 ; i < bitmapurls.size() ; i++) {
            if(bitmapurls.get(i).equals(u)) {
                index = i;
                break;
            }
        }
        return index;
    }
}
