package com.myphone;

import android.annotation.TargetApi;
import android.app.FragmentManager;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Window;

/**
 * Created by ashkingsharma on 9/7/15.
 */
public class HomeActivity extends ActionBarActivity implements ActionBar.TabListener{

    private ViewPager viewPager;
    private FragmentPagerAdapter tabAdapter;
    private String[] tabs ={"Contacts","Trunk Call", "Favorite", "Call Log", "Spam"};
    private SharedPreferences passCode;
    private String isVerified = "0";

    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        viewPager = (ViewPager) findViewById(R.id.pager);
        tabAdapter = new FramentPagerAdapter(getSupportFragmentManager());

        viewPager.setAdapter(tabAdapter);

        final ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("Home Page");

        actionBar.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#FFB9A37D")));
        actionBar.setDisplayShowHomeEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);

        passCode = getSharedPreferences("pass",MODE_PRIVATE);
        isVerified = passCode.getString("pass", "0");

        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);

        for(String tab_name : tabs){
            actionBar.addTab(actionBar.newTab().setText(tab_name).setTabListener(this));
        }

        viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                actionBar.setSelectedNavigationItem(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    @Override
    public void onTabSelected(ActionBar.Tab tab, FragmentTransaction ft) {
            viewPager.setCurrentItem(tab.getPosition());
    }

    @Override
    public void onTabUnselected(ActionBar.Tab tab, FragmentTransaction ft) {

    }

    @Override
    public void onTabReselected(ActionBar.Tab tab, FragmentTransaction ft) {

    }

    @Override
    public void onBackPressed() {
        Log.d("isVerified", isVerified);
        if(isVerified.equalsIgnoreCase("1"))
            this.finish();
        else
            super.onBackPressed();
    }
}
