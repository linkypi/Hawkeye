package com.lynch.hawkeye.activity;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.net.Uri;
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
import java.util.StringTokenizer;

import javax.sql.ConnectionPoolDataSource;

import qiu.niorgai.StatusBarCompat;

/**
 * Created by JL on 2016/11/24/0024.
 */

public class MainActivity extends BaseActivity implements BottomNavigationBar.OnTabSelectedListener,
        MineFragment.OnFragmentInteractionListener,FeatheredFragment.OnFragmentInteractionListener ,
        FavoritesFragment.OnFragmentInteractionListener,FindFragment.OnFragmentInteractionListener
{

    private ArrayList<Fragment> fragmentArr;
    private Fragment mCurrentFrgment;
    @Override
    public  void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        /* 透明状态栏只有在 SDK >= 19 (Android 4.4) 才会生效. */
        StatusBarCompat.translucentStatusBar(MainActivity.this);

        BottomNavigationBar bottomNavigationBar = (BottomNavigationBar) findViewById(R.id.bottom_navigation_bar);
        bottomNavigationBar.setMode(BottomNavigationBar.MODE_FIXED);
        bottomNavigationBar.setBackgroundStyle(BottomNavigationBar.BACKGROUND_STYLE_STATIC);
        bottomNavigationBar.addItem(new BottomNavigationItem(R.drawable.ic_home_white_24dp, R.string.feathered).setActiveColorResource(R.color.orange))
                .addItem(new BottomNavigationItem(R.drawable.ic_book_white_24dp, R.string.find).setActiveColorResource(R.color.blue))
                .addItem(new BottomNavigationItem(R.drawable.ic_music_note_white_24dp, R.string.favorites).setActiveColorResource(R.color.blue))
                .addItem(new BottomNavigationItem(R.drawable.ic_videogame_asset_white_24dp, R.string.mine).setActiveColorResource(R.color.green))
                .setFirstSelectedPosition(0)
                .initialise();
        bottomNavigationBar.setTabSelectedListener(this);
        fragmentArr = getFragments();
        setDefaultFragment();
    }

    /**
     * 设置默认的
     */
    private void setDefaultFragment() {
        FragmentManager fm = getFragmentManager();
        FragmentTransaction transaction = fm.beginTransaction();
        mCurrentFrgment = fragmentArr.get(0);
        transaction.add(R.id.content_frame, mCurrentFrgment,mCurrentFrgment.getClass().getName());
        transaction.commit();
    }

    private ArrayList<Fragment> getFragments() {
        ArrayList<Fragment> fragmentArr = new ArrayList<>();
        MineFragment mine = MineFragment.newInstance(getString(R.string.mine),null);

        fragmentArr.add(FeatheredFragment.newInstance(getString(R.string.feathered),null));
        fragmentArr.add(FindFragment.newInstance(getString(R.string.find),null));
        fragmentArr.add(FavoritesFragment.newInstance(getString(R.string.favorites),null));
        fragmentArr.add(MineFragment.newInstance(getString(R.string.mine),null));

        return fragmentArr;
    }

    @Override
    public void onTabSelected(int position) {
        if (fragmentArr != null) {
            if (position < fragmentArr.size()) {
                switchContent(position);
            }
        }
    }

    private void switchContent(int position) {
        FragmentManager fm = getFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        Fragment fragment = fragmentArr.get(position);
        String tag = fragment.getClass().getName();

        //先根据Tag从FragmentTransaction事物获取之前添加的Fragment
        Fragment cacheFragment = fm.findFragmentByTag(tag);

        if (null == cacheFragment) {
            cacheFragment = fragment;
        }

        if (!cacheFragment.isAdded()) {
            ft.hide(mCurrentFrgment).add(R.id.content_frame, cacheFragment, tag).commit();
        } else {
            ft.hide(mCurrentFrgment).show(cacheFragment).commit();
        }
        mCurrentFrgment = cacheFragment;
    }

    @Override
    public void onTabUnselected(int position) {
    }

    @Override
    public void onTabReselected(int position) {
    }

    @Override
    public void onMineFragmentInteraction(Uri uri){

    }
    @Override
    public void onFeatheredFragmentInteraction(Uri uri){

    }
    @Override
    public void onFindFragmentInteraction(Uri uri){

    }
    @Override
    public void onFavoritesFragmentInteraction(Uri uri){

    }
}
