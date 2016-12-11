package com.lynch.hawkeye.fragment;

import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.lynch.hawkeye.R;
import com.lynch.hawkeye.activity.SearchActivity;
import com.lynch.hawkeye.activity.VideoActivity;
import com.lynch.hawkeye.component.BannerView;
import com.lynch.hawkeye.model.Dto;
import com.lynch.hawkeye.config.AppContext;
import com.lynch.hawkeye.utils.Utils;
import com.squareup.picasso.Picasso;

import java.util.Arrays;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link FeatheredFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link FeatheredFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FeatheredFragment extends BaseFragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private List<String> videos = Arrays.asList(
            "/stronger.mp4",
            "/banana.mp4",
            "/sick.mp4",
            "/secret.mp4",
            "/maginrabbit.mp4",
            "/360.mov",
            "/cat.mp4",
            "/slide.mp4",
            "/pian.mp4",
            "/sunshine.mp4",
            "/3d.mp4",
            "/cat.mp4",
            "/pian.mp4"
    );
    private List<String> texts = Arrays.asList(
            "弱者：做一个更强的人",
            "修草坪的小黄人",
            "长话短说，我病了",
            "博物馆后面的大秘密",
            "魔术师和兔子",
            "360°全景延时影片「复古巴黎」",
            "喂养猫的好奇心",
            "高山滑雪玩腻了，我们高空滑云",
            "史上超超超快大片已上线",
            "日月轮转，山上的星空",
            "良心3D动画:酒精怪物",
            "做男模，颜值是唯一的标准吗？",
            "future"
    );

    private List<Integer> images = Arrays.asList(
            R.drawable.img1, R.drawable.img3, R.drawable.img5, R.drawable.img6,
            R.drawable.img7, R.drawable.img8, R.drawable.img9, R.drawable.img10
    );

    private List<String> imageUrls = Arrays.asList(
            "http://img.kaiyanapp.com/012a82e8d4547c33ff3861c82747a83e.jpeg?imageMogr2/quality/100",
            "http://img.kaiyanapp.com/67edba495eee5e7f5c9a19a5963a3742.jpeg?imageMogr2/quality/60",
            "http://img.kaiyanapp.com/93caa3b7d2f174a29e862a41f0dd9f4d.jpeg?imageMogr2/quality/60",
            "http://img.kaiyanapp.com/173b7eb7e8ccffde842ef3585ce56bfa.jpeg?imageMogr2/quality/60",
            "http://img.kaiyanapp.com/ac0ca8481abb8d3cde72240e45021aed.jpeg?imageMogr2/quality/100",
            "http://img.kaiyanapp.com/15fdc64c67ea45a1ed39f807c29cf2de.jpeg?imageMogr2/quality/60",
            "http://img.kaiyanapp.com/1d5cd526be9a751617efdf5e51bb6a2d.jpeg?imageMogr2/quality/60",
            "http://img.kaiyanapp.com/98637a768af28989e6722b3788a8175c.jpeg?imageMogr2/quality/60",
            "http://img.kaiyanapp.com/1f27faaaa5514aea9d28de970e7efe1a.jpeg?imageMogr2/quality/60",
            "http://img.kaiyanapp.com/00ed86faa91106a4974e59b292ee1b7e.jpeg?imageMogr2/quality/60",
            "http://img.kaiyanapp.com/15435f8b3310734535402e57b5e946b3.jpeg?imageMogr2/quality/60",
            "http://img.kaiyanapp.com/98637a768af28989e6722b3788a8175c.jpeg?imageMogr2/quality/60",
            "http://img.kaiyanapp.com/15fdc64c67ea45a1ed39f807c29cf2de.jpeg?imageMogr2/quality/60"
    );

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private LinearLayout content_view;
    private BannerView bannerView;
    private ImageButton btnSearch;

    private OnFragmentInteractionListener mListener;

    public FeatheredFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment FeatheredFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static FeatheredFragment newInstance(String param1, String param2) {
        FeatheredFragment fragment = new FeatheredFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    private void initBanners(){
        List<String> banners = Arrays.asList(
                "http://img15.3lian.com/2015/f2/147/d/39.jpg",
                "http://img1.3lian.com/2015/a1/107/d/65.jpg",
//                "http://img1.3lian.com/2015/a1/107/d/21.jpg",
                "http://img15.3lian.com/2015/f2/147/d/9.jpg",
                "http://img1.3lian.com/2015/a1/93/d/225.jpg",
                "http://img1.3lian.com/img013/v4/96/d/44.jpg");
        bannerView.setSwitchTime(2000);
        bannerView.setDefaultAdapter(banners);
    }
    private void initViews(){

        for (int index = 0; index < 12; index++) {
            RelativeLayout view = new RelativeLayout(mContext);
            //LayoutParams的类型由父控件决定
            LinearLayout.LayoutParams lps = new LinearLayout.LayoutParams(
                    RelativeLayout.LayoutParams.MATCH_PARENT, Utils.dip2px(mContext, 121));
            lps.gravity = Gravity.CENTER;
            view.setGravity(Gravity.CENTER);
            view.setLayoutParams(lps);
            String url = AppContext.ip + videos.get(index);
            view.setTag(new Dto(texts.get(index),url));
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(mContext, VideoActivity.class);
                    Dto dto = (Dto)v.getTag();
                    Bundle bundle = new Bundle();
                    bundle.putSerializable("data", dto);
                    intent.putExtras(bundle);
                    startActivity(intent);
                }
            });

            ImageView imageView = new ImageView(mContext);
            RelativeLayout.LayoutParams ilps = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.MATCH_PARENT);//Utils.dip2px(mContext, 120)
            imageView.setLayoutParams(ilps);
            imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
            Picasso.with(mContext).load(imageUrls.get(index)).into(imageView);
            view.addView(imageView);

            TextView tview = new TextView(mContext);
            tview.setText(texts.get(index));
            tview.setTextSize(16);
            tview.setTextColor(Color.parseColor("#FFFFFF"));
            tview.setGravity(Gravity.CENTER);
            RelativeLayout.LayoutParams tlps = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.MATCH_PARENT);
            tview.setLayoutParams(tlps);
            view.addView(tview);

            content_view.addView(view);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_feathered, container, false);
        content_view = (LinearLayout)view.findViewById(R.id.content);
        bannerView = (BannerView) view.findViewById(R.id.banners);
        btnSearch = (ImageButton)view.findViewById(R.id.btn_search);
        btnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, SearchActivity.class);
                startActivity(intent);
            }
        });
        initBanners();
        initViews();

        return view;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFeatheredFragmentInteraction(uri);
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        if (bannerView != null) {
            //bannerView.cancelTimer();
        }
    }
    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFeatheredFragmentInteraction(Uri uri);
    }
}
