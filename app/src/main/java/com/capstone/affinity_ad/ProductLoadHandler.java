package com.capstone.affinity_ad;

import android.os.Handler;
import android.os.Message;

import androidx.annotation.NonNull;

public class ProductLoadHandler extends Handler {
    boolean done = false;

    @Override
    public void handleMessage(@NonNull Message msg) {
        super.handleMessage(msg);

        switch(msg.what) {
            case 1:
                trigg();
                break;
        }
    }
    public void trigg() {

        done = true;
    }
}
