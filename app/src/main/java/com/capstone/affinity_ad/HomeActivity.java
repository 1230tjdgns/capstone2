package com.capstone.affinity_ad;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Toast;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

public class HomeActivity extends AppCompatActivity {

    Fragment defaultFrag;
    BottomNavigationView nav_menu;
    private long backpressedTime = 0;

    @Override
    public void onBackPressed() {

        if (System.currentTimeMillis() > backpressedTime + 2000) {
            backpressedTime = System.currentTimeMillis();
            Toast.makeText(this, "\'뒤로\' 버튼을 한번 더 누르면 종료됩니다.", Toast.LENGTH_SHORT).show();
        } else if (System.currentTimeMillis() <= backpressedTime + 2000) {
            finish();
        }

    }
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

                        getSupportFragmentManager().beginTransaction().replace(R.id.mainFrame,new SearchFragment()).commit();
                        return true;
                    }
                    case R.id.profile: {
                        //new Fragment() 대신 이동 할 프래그 클래스 적기
                        getSupportFragmentManager().beginTransaction().replace(R.id.mainFrame, new ProfileFragment()).commit();
                        return true;
                    }

                }

                return false;
            }
        });
    }

    //홈 화면 상단에 검색 버튼을 위한, 프래그 내부에서 상위 엑티비티의 프레임 교체에 필요한 메소드
    public void replaceFragment(Fragment fragment) {


        nav_menu.setSelectedItemId(R.id.search);
    }


}