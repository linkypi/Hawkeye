package com.lynch.hawkeye.component;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.lynch.hawkeye.R;
import com.lynch.hawkeye.utils.Utils;
import com.squareup.picasso.Picasso;

import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by linxueqi on 2016/11/28 0028.
 */

public class BannerView extends FrameLayout implements ViewPager.OnPageChangeListener {

    private Adapter adapter;
    private Context context;
    private ViewPager viewPager;
    private LinearLayout carouselLayout;
    private LayoutInflater inflater;
    private static final int MSG = 0X100;
    /**
     * 轮播图最大数
     */
    private int totalCount = Integer.MAX_VALUE;
    /**
     * 当前banner需要显示的数量
     */
    private int showCount;
    private int currentPosition = 0;
    /**
     * 轮播切换小圆点宽度默认宽度
     */
    private static final int DOT_DEFAULT_W = 5;
    /**
     * 轮播切换小圆点宽度
     */
    private int IndicatorDotWidth = DOT_DEFAULT_W;
    /**
     * 用户是否干预
     */
    private boolean isUserTouched = false;
    /**
     * 默认的轮播时间
     */
    private static final int DEFAULT_TIME = 3000;
    /**
     * 设置轮播时间
     */
    private int switchTime = DEFAULT_TIME;
    /**
     * 轮播图定时器
     */
    private Timer mTimer = new Timer();

    public BannerView(Context context) {
        super(context);
        this.context = context;
    }
    public BannerView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
    }
    public BannerView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.context = context;
    }

    private void create(){
        inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.layout_banner, null);
        this.viewPager = (ViewPager) view.findViewById(R.id.banner_viewpager);
        this.carouselLayout = (LinearLayout) view.findViewById(R.id.layout_banner_page);
        IndicatorDotWidth = Utils.dip2px(context, IndicatorDotWidth);
        this.viewPager.addOnPageChangeListener(this);
        addView(view);
    }

    private void init() {
        viewPager.setAdapter(null);
        carouselLayout.removeAllViews();
        if (adapter.isEmpty()) {
            return;
        }
        int count = adapter.getCount();
        showCount = adapter.getCount();
        //绘制切换小圆点
        for (int i = 0; i < count; i++) {
            View view = new View(context);
            if (currentPosition == i) {
                view.setPressed(true);
                LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                        IndicatorDotWidth + Utils.dip2px(context, 3),
                        IndicatorDotWidth + Utils.dip2px(context, 3));
                params.setMargins(IndicatorDotWidth, 0, 0, 0);
                view.setLayoutParams(params);
            } else {
                LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(IndicatorDotWidth, IndicatorDotWidth);
                params.setMargins(IndicatorDotWidth, 0, 0, 0);
                view.setLayoutParams(params);
            }
            view.setBackgroundResource(R.drawable.carousel_layout_dot);
            carouselLayout.addView(view);
        }
        viewPager.setAdapter(new ViewPagerAdapter());
        viewPager.setCurrentItem(0);
        this.viewPager.setOnTouchListener(new OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                    case MotionEvent.ACTION_MOVE:
                        //有用户滑动事件发生
                        isUserTouched = true;
                        break;
                    case MotionEvent.ACTION_UP:
                        isUserTouched = false;
                        break;
                }
                return false;
            }
        });
        //以指定周期和岩石开启一个定时任务
        mTimer.schedule(mTimerTask, switchTime, switchTime);
    }

    public void setAdapter(Adapter adapter) {
        if (adapter != null) {
            this.adapter = adapter;
            init();
        }
    }

    public void setDefaultAdapter(List<String> banners){
        this.adapter = new BannerView.DefaultAdapter(banners);
        init();
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        create();
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        currentPosition = position;
        int count = carouselLayout.getChildCount();
        for (int i = 0; i < count; i++) {
            View view = carouselLayout.getChildAt(i);
            if (position % showCount == i) {
                view.setSelected(true);
                //当前位置的点要绘制的较大一点，高亮显示
                LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                        IndicatorDotWidth + Utils.dip2px(context, 3),
                        IndicatorDotWidth + Utils.dip2px(context, 3));
                params.setMargins(IndicatorDotWidth, 0, 0, 0);
                view.setLayoutParams(params);
            } else {
                view.setSelected(false);
                LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(IndicatorDotWidth, IndicatorDotWidth);
                params.setMargins(IndicatorDotWidth, 0, 0, 0);
                view.setLayoutParams(params);
            }
        }
    }
    @Override
    public void onPageScrollStateChanged(int state) {

    }

    private View getView(String url)
    {
//        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.layout_banner_item, null);
        ImageView imageView = (ImageView) view.findViewById(R.id.image);
        Picasso.with(context).load(url).into(imageView);
        return view;
    }

    class ViewPagerAdapter extends PagerAdapter {
        @Override
        public int getCount() {
            return totalCount;
        }
        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }
        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            position %= showCount;
            String url = adapter.getUrl(position);
            View view = getView(url) ;//adapter.getView(position);
            container.addView(view);
            return view;
        }
        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((View) object);
        }
        @Override
        public int getItemPosition(Object object) {
            return super.getItemPosition(object);
        }
        @Override
        public void finishUpdate(ViewGroup container) {
            super.finishUpdate(container);
            int position = viewPager.getCurrentItem();
            if (position == 0) {
                position = showCount;
                viewPager.setCurrentItem(position, false);
            } else if (position == totalCount - 1) {
                position = showCount - 1;
                viewPager.setCurrentItem(position, false);
            }
        }
    }

    private TimerTask mTimerTask = new TimerTask() {
        @Override
        public void run() {
            //用户滑动时，定时任务不响应
            if (!isUserTouched) {
                currentPosition = (currentPosition + 1) % totalCount;
                handler.sendEmptyMessage(MSG);
            }
        }
    };
    public void cancelTimer() {
        if (this.mTimer != null) {
            this.mTimer.cancel();
        }
    }

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            if (msg.what == MSG) {
                //Log.e("Pos", "the position is " + currentPosition);
                if (currentPosition == totalCount - 1) {
                    viewPager.setCurrentItem(showCount - 1, false);
                } else {
                    viewPager.setCurrentItem(currentPosition);
                }
            }
        }
    };
    /**
     *可自定义设置轮播图切换时间，单位毫秒
     * @param switchTime millseconds
     */
    public void setSwitchTime(int switchTime) {
        this.switchTime = switchTime;
    }
    /**
     * @param indicatorDotWidth
     */
    public void setIndicatorDotWidth(int indicatorDotWidth) {
        IndicatorDotWidth = indicatorDotWidth;
    }

    /**
     * 适配器
     */
    public interface Adapter {
        boolean isEmpty();
        String getUrl(int position);
        int getCount();
    }

    private class DefaultAdapter implements BannerView.Adapter {

        private List<String> banners;

        public DefaultAdapter(){}
        public DefaultAdapter(List<String> banners){
            this.banners = banners;
        }
        @Override
        public boolean isEmpty() {
            return banners.size() > 0 ? false : true;
        }

        @Override
        public String getUrl(int position){
            return banners.get(position);
        }
//        @Override
//        public View getView(final int position) {
//            LayoutInflater inflater = LayoutInflater.from(context);
//            View view = inflater.inflate(R.layout.layout_banner_item, null);
//            ImageView imageView = (ImageView) view.findViewById(R.id.image);
//            Picasso.with(context).load(banners.get(position)).into(imageView);
//            view.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//
//                }
//            });
//            return view;
//        }

        @Override
        public int getCount() {
            return banners.size();
        }
    }
}
