package com.lynch.hawkeye.fragment;

import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.lynch.hawkeye.R;
import com.lynch.hawkeye.activity.VideoActivity;
import com.lynch.hawkeye.model.Dto;
import com.lynch.hawkeye.utils.Utils;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

import java.io.IOException;
import java.io.InputStream;
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

    private List<String> urls = Arrays.asList(
            "http://192.168.0.176:7000/mp4/360.mp4",
            "http://192.168.0.176:7000/mp4/cat.mp4",
            "http://192.168.0.176:7000/mp4/slide.mp4",
            "http://192.168.0.176:7000/mp4/pian.mp4",
            "http://192.168.0.176:7000/mp4/sunshine.mp4",
            "http://192.168.0.176:7000/mp4/3d.mp4",
            "http://192.168.0.176:7000/mp4/cat.mp4",
            "http://192.168.0.176:7000/mp4/pian.mp4"
    );
    private List<String> texts = Arrays.asList(
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
    LinearLayout content_view;

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

    private void initViews(){

        for (int index = 0; index < 8; index++) {
            RelativeLayout view = new RelativeLayout(mContext);
            view.setGravity(Gravity.CENTER);
            LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, Utils.dip2px(mContext, 150));
            lp.gravity = Gravity.CENTER;
            view.setLayoutParams(lp);
            view.setTag(new Dto(texts.get(index),urls.get(index)));
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(mContext, VideoActivity.class);
                    Dto dto = (Dto)v.getTag();
                    intent.putExtra("url",dto.getUrl());
                    intent.putExtra("title",dto.getTitle());
                    startActivity(intent);
                }
            });

            ImageView imageView = new ImageView(mContext);
            imageView.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, Utils.dip2px(mContext, 150)));
            Picasso.with(mContext).load(imageUrls.get(index)).into(imageView);
            view.addView(imageView);

            TextView tview = new TextView(mContext);
            tview.setText(texts.get(index));
            tview.setTextSize(16);
            tview.setTextColor(Color.parseColor("#FFFFFF"));
            tview.setGravity(Gravity.CENTER);
            tview.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
            view.addView(tview);

            content_view.addView(view);

        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_feathered, container, false);
        content_view = (LinearLayout)view.findViewById(R.id.content);
        initViews();
        return view;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
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
        void onFragmentInteraction(Uri uri);
    }
}
