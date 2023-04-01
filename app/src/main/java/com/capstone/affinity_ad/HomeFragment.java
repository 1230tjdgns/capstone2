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
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import androidx.viewpager2.widget.ViewPager2;

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

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link HomeFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class HomeFragment extends Fragment {

    GridViewAdapter adapter;
    ExpandableHeightGridView gv;

    Button bt_search;
    Button bt_ct_top;
    Button bt_ct_bottom;
    Button bt_ct_outer;
    Button bt_ct_shoes;
    Button bt_ct_accessory;

    ViewPager2 pager;
    PagerAdapter pagerAdapter;
    CircleAnimIndicator indicator;


    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public HomeFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment HomeFragment.
     */
    // TODO: Rename and change types and number of parameters
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

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        //프래그에 뷰를 참조 하려면 이거 해야한다드라
        View frag_view = inflater.inflate(R.layout.fragment_home, container, false);

        //그리드 초기화
        gv = (ExpandableHeightGridView)frag_view.findViewById(R.id.home_item_grid);
        adapter = new GridViewAdapter();
        //그리드뷰에 개별 스크롤이 생기지 않게하는 마술
        gv.setExpanded(true);

        //카테고리 버튼
        bt_ct_accessory = frag_view.findViewById(R.id.bt_home_accessory);
        bt_ct_shoes = frag_view.findViewById(R.id.bt_home_shoes);
        bt_ct_outer = frag_view.findViewById(R.id.bt_home_outer);
        bt_ct_bottom = frag_view.findViewById(R.id.bt_home_bottom);
        bt_ct_top = frag_view.findViewById(R.id.bt_home_top);
        //상단 검색 버튼
        bt_search = frag_view.findViewById(R.id.bt_home_search);
        //카테고리 버튼 리스너 셋
        bt_ct_accessory.setOnClickListener(new CategorySelect());
        bt_ct_shoes.setOnClickListener(new CategorySelect());
        bt_ct_outer.setOnClickListener(new CategorySelect());
        bt_ct_bottom.setOnClickListener(new CategorySelect());
        bt_ct_top.setOnClickListener(new CategorySelect());

        // 초기 카테고리 선택
        for(int i = 0 ; i < 6 ; i++) {
            adapter.addItem(new gridItem("","상의 브랜드", "상의 이름", "상의 가격"));
        }
        gv.setAdapter(adapter);

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

        return frag_view;
    }


    class CategorySelect implements View.OnClickListener {

        @Override
        public void onClick(View view) {
            adapter.clearItem();

            switch(view.getId()) {
                case R.id.bt_home_accessory:
                    for(int i = 0 ; i < 6 ; i++) {
                        adapter.addItem(new gridItem("","악세서리 브랜드", "악세서리 이름", "악세서리 가격"));
                    }
                    break;
                case R.id.bt_home_bottom:
                    for(int i = 0 ; i < 6 ; i++) {
                        adapter.addItem(new gridItem("","하의 브랜드", "하의 이름", "하의 가격"));
                    }
                    break;
                case R.id.bt_home_top:
                    for(int i = 0 ; i < 6 ; i++) {
                        adapter.addItem(new gridItem("","상의 브랜드", "상의 이름", "상의 가격"));
                    }
                    break;
                case R.id.bt_home_shoes:
                    for(int i = 0 ; i < 6 ; i++) {
                        adapter.addItem(new gridItem("","신발 브랜드", "신발 이름", "신발 가격"));
                    }
                    break;
                case R.id.bt_home_outer:
                    for(int i = 0 ; i < 6 ; i++) {
                        adapter.addItem(new gridItem("","아우터 브랜드", "아우터 이름", "아우터 가격"));
                    }
                    break;
            }
            gv.setAdapter(adapter);
        }
    }

}