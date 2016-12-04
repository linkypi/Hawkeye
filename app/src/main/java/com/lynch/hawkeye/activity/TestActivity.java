package com.lynch.hawkeye.activity;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.ListView;

import com.lynch.hawkeye.R;
import com.lynch.hawkeye.component.BannerView;
import com.squareup.picasso.Picasso;

import java.util.Arrays;
import java.util.List;

public class TestActivity extends AppCompatActivity {

    private ListView listview;
    private List<String> datas;
    private List<String> banners;
    private View headView;
    private BannerView carouselView;
    private Context mContext;
    private LayoutInflater mInflater;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = this;
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_test);
        InitView();
    }

    private void InitView() {
        InitDatas();
        mInflater = LayoutInflater.from(this);
        //headView = mInflater.inflate(R.layout.layout_test_header, null);
//        carouselView = (BannerView) headView.findViewById(R.id.CarouselView);
        carouselView = (BannerView) findViewById(R.id.CarouselView);
        //这里考虑到不同手机分辨率下的情况
//        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
//                LinearLayout.LayoutParams.MATCH_PARENT, Utils.dip2px(this, 200));
//        carouselView.setLayoutParams(params);
        carouselView.setSwitchTime(2000);
//        carouselView.setAdapter(new MyAdapter());
        carouselView.setDefaultAdapter(banners);
//        listview = (ListView) findViewById(R.id.list);
//        listview.addHeaderView(headView);
//        ArrayAdapter<String> myAdapter = new ArrayAdapter<String>(mContext,
//                android.R.layout.simple_expandable_list_item_1, datas);
//        listview.setAdapter(myAdapter);

    }

    /**
     * 设定虚拟数据
     */
    private void InitDatas() {
//        datas = new ArrayList<>();
//        for (int i = 0; i < 20; i++) {
//            datas.add("the Item is " + i);
//        }
        //图片来自百度
        banners = Arrays.asList(
                "http://img1.imgtn.bdimg.com/it/u=2826772326,2794642991&fm=15&gp=0.jpg",
                "http://img15.3lian.com/2015/f2/147/d/39.jpg",
                "http://img1.3lian.com/2015/a1/107/d/65.jpg",
                "http://img1.3lian.com/2015/a1/93/d/225.jpg",
                "http://img1.3lian.com/img013/v4/96/d/44.jpg");
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (carouselView != null) {
            carouselView.cancelTimer();
        }
    }

//    private class MyAdapter implements BannerView.Adapter {
//
//        @Override
//        public boolean isEmpty() {
//            return banners.size() > 0 ? false : true;
//        }
//
//        @Override
//        public View getView(final int position) {
//            View view = mInflater.inflate(R.layout.layout_banner_item, null);
//            ImageView imageView = (ImageView) view.findViewById(R.id.image);
//            Picasso.with(mContext).load(banners.get(position)).into(imageView);
//            view.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    //T.showShort(mContext,"Now is "+position);
//                }
//            });
//            return view;
//        }
//
//        @Override
//        public int getCount() {
//            return banners.size();
//        }
//    }
}
