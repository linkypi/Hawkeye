package com.lynch.hawkeye;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.lynch.hawkeye.adapter.ViewPagerAdapter;

import java.util.ArrayList;
import java.util.List;

public class GuideActivity extends AppCompatActivity implements ViewPager.OnPageChangeListener {

    private ViewPager guidePages;

    private LinearLayout mRadioGroup;

    private List<View> views;

    private ViewPagerAdapter vpAdapter;

    private ImageView[] imageViews = null;

    private ImageView imageView = null;

    private HorizontalScrollView scroll;
    /** 背景图片视图 */
    private ImageView image;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_guide);
        initView();
        initData();
    }
    public void initView() {
        guidePages = (ViewPager) findViewById(R.id.guidePages);
        mRadioGroup = (LinearLayout) findViewById(R.id.guide_dots);
        scroll = (HorizontalScrollView) findViewById(R.id.scroll);
        image = (ImageView) findViewById(R.id.image);

        LayoutInflater inflater = LayoutInflater.from(this);
        views = new ArrayList<View>();
        views.add(inflater.inflate(R.layout.layout_guide1, null));
        views.add(inflater.inflate(R.layout.layout_guide2, null));
        views.add(inflater.inflate(R.layout.layout_guide3, null));
        views.add(inflater.inflate(R.layout.layout_guide4, null));
        int tag = getIntent().getIntExtra("tag", 0);
        vpAdapter = new ViewPagerAdapter(views, this, tag);
        guidePages.setAdapter(vpAdapter);
    }

    public void initData() {
        imageViews = new ImageView[4];
        for (int i = 0; i < 4; i++) {
            imageView = new ImageView(this);
            imageView.setLayoutParams(new FrameLayout.LayoutParams(FrameLayout.LayoutParams.WRAP_CONTENT, FrameLayout.LayoutParams.WRAP_CONTENT));
            LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            lp.setMargins(10, 5, 0, 5);
            imageView.setLayoutParams(lp);
            imageViews[i] = imageView;
            if (i == 0) {
                imageViews[i].setBackgroundResource(R.drawable.guide_radiogroup_selected);
            } else {
                imageViews[i].setBackgroundResource(R.drawable.guide_radiogroup_normal);
            }
            mRadioGroup.addView(imageViews[i]);
        }
        guidePages.setOnPageChangeListener(this);
    }

    @Override
    public void onPageScrollStateChanged(int arg0) {

    }

    @Override
    public void onPageScrolled(int arg0, float arg1, int arg2) {
        // pager所有子页面的总宽度
        float widthOfPagers = guidePages.getWidth() * 4;
        // 背景图片的宽的
        float widthOfScroll = image.getWidth();
        // ViewPager可滑动的总长度
        float moveWidthOfPagers = widthOfPagers - guidePages.getWidth();
        // 背景图的可滑动总长度
        float moveWidthOfScroll = widthOfScroll - guidePages.getWidth();

        // 可滑动距离比例
        float ratio = moveWidthOfScroll / moveWidthOfPagers;
        // 当前Pager的滑动距离
        float currentPosOfPager = arg0 * guidePages.getWidth() + arg2;

        // 背景滑动到对应位置
        scroll.scrollTo((int) (currentPosOfPager * ratio), scroll.getScrollY());
    }

    @Override
    public void onPageSelected(int arg0) {
        for (int i = 0; i < imageViews.length; i++) {
            imageViews[i].setBackgroundResource(R.drawable.guide_radiogroup_selected);
            if (arg0 != i) {
                imageViews[i].setBackgroundResource(R.drawable.guide_radiogroup_normal);
            }
        }
    }

}
