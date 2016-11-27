package com.lynch.hawkeye.activity;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.ashokvarma.bottomnavigation.BottomNavigationBar;
import com.ashokvarma.bottomnavigation.BottomNavigationItem;
import com.lynch.hawkeye.R;
import com.lynch.hawkeye.fragment.MineFragment;
import com.lynch.hawkeye.fragment.FavoritesFragment;
import com.lynch.hawkeye.fragment.FindFragment;
import com.lynch.hawkeye.fragment.FeatheredFragment;

import java.util.ArrayList;

import qiu.niorgai.StatusBarCompat;

/**
 * Created by JL on 2016/11/24/0024.
 */

public class MainActivity extends AppCompatActivity implements BottomNavigationBar.OnTabSelectedListener {

    private ArrayList<Fragment> fragments;

    @Override
    public  void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        /* 透明状态栏只有在 SDK >= 19 (Android 4.4) 才会生效. */
        StatusBarCompat.translucentStatusBar(MainActivity.this);

        BottomNavigationBar bottomNavigationBar = (BottomNavigationBar) findViewById(R.id.bottom_navigation_bar);
        bottomNavigationBar.setMode(BottomNavigationBar.MODE_FIXED);
        bottomNavigationBar
                .setBackgroundStyle(BottomNavigationBar.BACKGROUND_STYLE_STATIC
                );
        bottomNavigationBar.addItem(new BottomNavigationItem(R.drawable.ic_home_white_24dp, R.string.feathered).setActiveColorResource(R.color.orange))
                .addItem(new BottomNavigationItem(R.drawable.ic_book_white_24dp, R.string.find).setActiveColorResource(R.color.blue))
                .addItem(new BottomNavigationItem(R.drawable.ic_music_note_white_24dp, R.string.favorites).setActiveColorResource(R.color.blue))
                .addItem(new BottomNavigationItem(R.drawable.ic_videogame_asset_white_24dp, R.string.mine).setActiveColorResource(R.color.green))
                .setFirstSelectedPosition(0)
                .initialise();
        bottomNavigationBar.setTabSelectedListener(this);
        fragments = getFragments();
        setDefaultFragment();
    }

    /**
     * 设置默认的
     */
    private void setDefaultFragment() {
        FragmentManager fm = getFragmentManager();
        FragmentTransaction transaction = fm.beginTransaction();
        transaction.replace(R.id.layFrame, FeatheredFragment.newInstance(getString(R.string.feathered),null));
        transaction.commit();
    }

    private ArrayList<Fragment> getFragments() {
        ArrayList<Fragment> fragments = new ArrayList<>();
        fragments.add(FeatheredFragment.newInstance(getString(R.string.feathered),null));
        fragments.add(FavoritesFragment.newInstance(getString(R.string.favorites),null));
        fragments.add(FindFragment.newInstance(getString(R.string.find),null));
        fragments.add(MineFragment.newInstance(getString(R.string.mine),null));
        return fragments;
    }

    @Override
    public void onTabSelected(int position) {
        if (fragments != null) {
            if (position < fragments.size()) {
                FragmentManager fm = getFragmentManager();
                FragmentTransaction ft = fm.beginTransaction();
                Fragment fragment = fragments.get(position);
                ft.replace(R.id.layFrame, fragment);
                ft.commitAllowingStateLoss();
            }
        }

    }

    @Override
    public void onTabUnselected(int position) {
        if (fragments != null) {
            if (position < fragments.size()) {
                FragmentManager fm = getFragmentManager();
                FragmentTransaction ft = fm.beginTransaction();
                Fragment fragment = fragments.get(position);
                ft.remove(fragment);
                ft.commitAllowingStateLoss();
            }
        }
    }

    @Override
    public void onTabReselected(int position) {

    }
}
