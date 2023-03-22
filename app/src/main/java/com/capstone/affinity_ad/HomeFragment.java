package com.capstone.affinity_ad;

import static android.content.ContentValues.TAG;

import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
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

        bt_search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((HomeActivity)getActivity()).replaceFragment(new Fragment());
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

    //구글링은 무적이고 신이다 구글에서 긁어옴
    class GridViewAdapter extends BaseAdapter {
        ArrayList<gridItem> items = new ArrayList<gridItem>();


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

                TextView brand = (TextView) view.findViewById(R.id.home_item_brand);
                TextView name = (TextView) view.findViewById(R.id.home_item_name);
                TextView price = (TextView) view.findViewById(R.id.home_item_price);

                brand.setText(g_items.getBrand());
                name.setText(g_items.getName());
                price.setText(g_items.getPrice());
                Log.d(TAG, "getView() - [ "+i+" ] "+g_items.getName());

            } else {
                View v = new View(context);
                v = (View) view;
            }
            //각 아이템 선택 event
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Toast.makeText(context, g_items.getBrand()+" - "+g_items.getName()+" - " + g_items.getPrice(), Toast.LENGTH_SHORT).show();
                }
            });
            return view;
        }
    }
}