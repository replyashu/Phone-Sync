package com.myphone;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.myphone.fragment.GetContactsFragment;
import com.myphone.fragment.GetFavoriteFragment;
import com.myphone.fragment.GetHistoryLogFragment;
import com.myphone.fragment.GetSpamFragment;
import com.myphone.fragment.GetTruckCallerFragment;

/**
 * Created by ashkingsharma on 9/7/15.
 */
public class FramentPagerAdapter extends FragmentPagerAdapter {

    private String[] tabs ={"Contacts","Trunk Call", "Favorite", "Call Log", "Spam"};

    public FramentPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        switch (position){
            case 0: return new GetContactsFragment();
            case 1: return new GetTruckCallerFragment();
            case 2: return new GetFavoriteFragment();
            case 3: return new GetHistoryLogFragment();
            case 4: return new GetSpamFragment();
        }
        return  null;
    }

    @Override
    public int getCount() {
        return tabs.length;
    }
}
