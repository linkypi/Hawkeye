package com.lynch.hawkeye.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Parcelable;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.Button;

import com.lynch.hawkeye.LoginActivity;
import com.lynch.hawkeye.MainActivity;
import com.lynch.hawkeye.R;
import com.lynch.hawkeye.utils.Utils;

import java.util.List;

/**
 * Created by linxueqi on 2016/11/22 0022.
 */

public class ViewPagerAdapter extends PagerAdapter {
    private List<View> views;

    private int tag;

    private Context context;

    public ViewPagerAdapter(List<View> views, Context context, int tag) {
        this.views = views;
        this.context = context;
        this.tag = tag;
    }

    @Override
    public void destroyItem(View arg0, int arg1, Object arg2) {
        ((ViewPager) arg0).removeView(views.get(arg1));
    }

    @Override
    public void finishUpdate(View arg0) {
    }

    @Override
    public int getCount() {
        if (views != null) {
            return views.size();
        }
        return 0;
    }

    @Override
    public Object instantiateItem(View arg0, int arg1) {
        ((ViewPager) arg0).addView(views.get(arg1), 0);
        if (arg1 == views.size() - 1) {
            Button mWhatNew = (Button) arg0.findViewById(R.id.what_new);
            mWhatNew.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    //setGuided();
                    goHome();
                }

            });
        }
        return views.get(arg1);
    }

    private void goHome() {
        if (tag == 1) {
            ((Activity) context).finish();
        } else {
            Intent intent = new Intent(context, LoginActivity.class);
            context.startActivity(intent);
            ((Activity) context).finish();
        }
    }

//    private void setGuided() {
//        Utils.writeBoolean(_context,"isFirstIn", true);
//    }

    // 判断是否由对象生成界面
    @Override
    public boolean isViewFromObject(View arg0, Object arg1) {
        return (arg0 == arg1);
    }

    @Override
    public void restoreState(Parcelable arg0, ClassLoader arg1) {
    }

    @Override
    public Parcelable saveState() {
        return null;
    }

    @Override
    public void startUpdate(View arg0) {
    }
}
