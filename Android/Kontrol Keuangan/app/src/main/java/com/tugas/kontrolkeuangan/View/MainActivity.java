package com.tugas.kontrolkeuangan.View;

import android.os.Bundle;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.tugas.kontrolkeuangan.R;
import com.tugas.kontrolkeuangan.View.Adapter.ViewPagerAdapter;
import com.tugas.kontrolkeuangan.View.Fragment.Lend_Fragment;
import com.tugas.kontrolkeuangan.View.Fragment.Money_Fragment;

import java.util.ArrayList;
import java.util.List;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

public class MainActivity extends AppCompatActivity {
    private ViewPager viewPager;
    private BottomNavigationView bottomNavigationView;
    Money_Fragment money_fragment;
    private final static String TAG = "MAIN";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        viewPager = findViewById(R.id.Fragment_Container);
        setupViewPager(viewPager);
        bottomNavigationView = findViewById(R.id.Bottom_Nav);
        bottomNavigationView.setOnNavigationItemSelectedListener(menuItem -> {
            switch (menuItem.getItemId()) {
                case R.id.asset:
                    viewPager.setCurrentItem(0);
                    break;
                case R.id.lend:
                    viewPager.setCurrentItem(1);
                    break;
            }
            return false;
        });
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) { }

            @Override
            public void onPageSelected(int position) {
                bottomNavigationView.getMenu().getItem(position).setChecked(true);
                if(position==0){
                    money_fragment.SetHutang();
                    money_fragment.SetTotal();
                    money_fragment.SetAllField();
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {
            }
        });
    }
    private void setupViewPager(ViewPager viewPager) {
        List<Fragment> FragmentList = new ArrayList<>();
        money_fragment = new Money_Fragment();
        Lend_Fragment lend_fragment = new Lend_Fragment();
        FragmentList.add(money_fragment);
        FragmentList.add(lend_fragment);
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager(), FragmentList);
        viewPager.setAdapter(adapter);
    }

}