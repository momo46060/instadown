package com.bomb.app.mosalah.Fragment;

import android.content.Context;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import com.bomb.app.mosalah.R;

public class TitleAdapter extends FragmentPagerAdapter {
    Context context;
    Integer obj = new Integer(R.string.wall);
    private String[]title=new String[]{"Wallpapers","Favorite"};

    private final Fragment fragment[]=new Fragment[title.length];
    public TitleAdapter(FragmentManager fm, Context context) {
        super(fm);
        this.context=context;
        fragment[0]=new FragmentMain();
        fragment[1]=new FragmentFavorit();
    }

    @Override
    public Fragment getItem(int i) {

        return fragment[i];
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return title[position];
    }

    @Override
    public int getCount() {
        return fragment.length;
    }
}
