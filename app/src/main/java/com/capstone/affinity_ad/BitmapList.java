package com.capstone.affinity_ad;

import android.graphics.Bitmap;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class BitmapList {

    static public HashMap<String, Bitmap> bitmaps = new HashMap<String, Bitmap>();

    static public Bitmap checkHash(String u) {
        if(bitmaps.containsKey(u)) {
            return bitmaps.get(u);
        }
        else {
            return null;
        }
    }

}
