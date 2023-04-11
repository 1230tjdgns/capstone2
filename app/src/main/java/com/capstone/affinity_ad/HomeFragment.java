package com.capstone.affinity_ad;

import static android.content.ContentValues.TAG;

import android.content.Context;
import android.content.Intent;
import android.icu.text.Transliterator;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.Lifecycle;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import androidx.viewpager2.widget.ViewPager2;

import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.ScaleAnimation;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import io.grpc.okhttp.internal.framed.FrameReader;

public class HomeFragment extends Fragment {

    GridViewAdapter adapter;
    ExpandableHeightGridView gv;

    Button bt_search;

    ViewPager2 pager;
    PagerAdapter pagerAdapter;
    CircleAnimIndicator indicator;

    SettingList settingList;
    private  List<String> l_brand;
    private  List<String> l_name;
    private  List<String> l_price;
    private List<String> l_image;
    private  List<String>l_id;

    List<String> brands;

    CategoryListAdapter categoryListAdapter;
    RecyclerView categoryRecyclerView;

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;

    BannerHandler autobanner_handler;

    public static HomeFragment newInstance(String param1, String param2) {
        HomeFragment fragment = new HomeFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }

    }

    private void settingList(){
        l_id.addAll(settingList.l_id);
        l_brand.addAll(settingList.l_brand);
        l_name.addAll(settingList.l_name);
        l_price.addAll(settingList.l_price);
        l_image.addAll(settingList.l_image);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View frag_view = inflater.inflate(R.layout.fragment_home, container, false);

        l_brand = new ArrayList<String>();
        l_name = new ArrayList<String>();
        l_price = new ArrayList<String>();
        l_image = new ArrayList<String>();
        l_id = new ArrayList<String>();

        settingList();

        gv = (ExpandableHeightGridView)frag_view.findViewById(R.id.home_item_grid);
        adapter = new GridViewAdapter();

        gv.setExpanded(true);

        bt_search = frag_view.findViewById(R.id.bt_home_search);

        for(int i = 0 ; i < l_id.size() ; i++) {
            adapter.addItem(new gridItem(l_id.get(i),l_image.get(i),l_brand.get(i), l_name.get(i), l_price.get(i)));
            Log.d("TEST", l_image.get(i));
        }
        gv.setAdapter(adapter);

        categoryRecyclerView = frag_view.findViewById(R.id.category_list);

        brands = l_brand.stream().distinct().collect(Collectors.toList());

        brands.add(0, "All");

        categoryListAdapter = new CategoryListAdapter(adapter, gv, l_brand, l_name, l_price , l_price, l_id);

        categoryListAdapter.addList(brands);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager((Context) frag_view.getContext());
        linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        categoryRecyclerView.setLayoutManager(linearLayoutManager);

        categoryRecyclerView.setAdapter(categoryListAdapter);


        bt_search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((HomeActivity)getActivity()).replaceFragment(new Fragment());
            }
        });


        int[] images = new int[] {
                R.drawable.banner1,
                R.drawable.banner2
        };
        String[] webUrls = new String[] {
                "https://abcmart.a-rt.com/",
                "https://www.nike.com/kr/"
        };

        indicator = frag_view.findViewById(R.id.circleAnimIndicator);
        indicator.setItemMargin(15);
        indicator.setAnimDuration(300);
        indicator.createDotPanel(images.length,R.drawable.indicator_def,R.drawable.indicator_select);

        pager = frag_view.findViewById(R.id.banner_pager);
        pager.setOffscreenPageLimit(1);

        pagerAdapter = new PagerAdapter(frag_view.getContext(), images, webUrls);
        pager.setAdapter(pagerAdapter);

        pager.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                indicator.selectDot(position);
                pagerAdapter.webPageLinked.updateUrl(position);

            }
        });

        autobanner_handler = new BannerHandler();

        PagerRunnable autobanner = new PagerRunnable();

        autobanner.start();

        return frag_view;
    }

    public void nextBanner() {
        int pagenum = pager.getCurrentItem();

        if(pagenum == 1) {
            pagenum = 0;
        }
        else {
            pagenum++;
        }

        pager.setCurrentItem(pagenum, true);
    }

    class BannerHandler extends Handler {

        @Override
        public void handleMessage(@NonNull Message msg) {
            super.handleMessage(msg);
            switch(msg.what) {
                case 0:
                    nextBanner();
                    break;
                default:
                    Log.d("AutoPagerHandler", "핸들러 오류");
            }
        }
    }

    class PagerRunnable extends Thread {
        @Override
        public void run() {
            while(true){
                try {
                    Thread.sleep(5000);
                    autobanner_handler.sendEmptyMessage(0);
                } catch (InterruptedException e){
                    Log.d("interupt", "interupt발생");
                }
            }
        }
    }




}