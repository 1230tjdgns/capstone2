package com.capstone.affinity_ad;

import static android.content.ContentValues.TAG;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.viewpager2.widget.ViewPager2;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

import org.checkerframework.common.subtyping.qual.Bottom;

import java.util.ArrayList;

public class HomeActivity extends AppCompatActivity {

    Fragment defaultFrag;
    BottomNavigationView nav_menu;

    ViewPager2 pager;
    final static int page_num = 2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        nav_menu = findViewById(R.id.bottom_nav);
        defaultFrag = new HomeFragment();

        nav_menu.setSelectedItemId(R.id.home);

        getSupportFragmentManager().beginTransaction().replace(R.id.mainFrame,defaultFrag).commitAllowingStateLoss();

        nav_menu.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                //리스너 적용하면 아이콘 선택시 색상이 변하지 않기 때문에 수동으로 적용
                item.setChecked(true);
                switch (item.getItemId()) {
                    case R.id.home: {
                        getSupportFragmentManager().beginTransaction().replace(R.id.mainFrame, new HomeFragment()).commit();
                        return true;
                    }
                    case R.id.search: {
                        //new Fragment() 대신 이동 할 프래그 클래스 적기
                        getSupportFragmentManager().beginTransaction().replace(R.id.mainFrame, new SearchFragment()).commit();
                        return true;
                    }
                    case R.id.profile: {
                        //new Fragment() 대신 이동 할 프래그 클래스 적기
                        getSupportFragmentManager().beginTransaction().replace(R.id.mainFrame, new Fragment()).commit();
                        return true;
                    }

                }

                return false;
            }
        });
    }

    //홈 화면 상단에 검색 버튼을 위한, 프래그 내부에서 상위 엑티비티의 프레임 교체에 필요한 메소드
    public void replaceFragment(Fragment fragment) {
//        FragmentManager fragmentManager = getSupportFragmentManager();
//        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
//        fragmentTransaction.replace(R.id.mainFrame, fragment).commit();

        nav_menu.setSelectedItemId(R.id.search);
    }


}