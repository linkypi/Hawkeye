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
import android.widget.LinearLayout;
import android.widget.TextView;

import com.lynch.hawkeye.R;
import com.lynch.hawkeye.activity.VideoActivity;
import com.lynch.hawkeye.utils.Utils;

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
        "http://uc-baobab.wdjcdn.com/1479900066561_1ef77057.mp4?t=1480262343&k=8bb842153873c881",
        "http://uc-baobab.wdjcdn.com/1449473403055h.mp4?t=1480262480&k=07dd36582fa174a7",
        "http://uc-baobab.wdjcdn.com/1476327025495_fddf2a25_1280x720.mp4?t=1480262537&k=ce1982e1c49df3b2",
        "http://uc-baobab.wdjcdn.com/1467624049383president_x264.mp4?t=1480262577&k=eb592c1733217576",
        "http://uc-baobab.wdjcdn.com/1479962643470_6423653d.mp4?t=1480262606&k=4e17d133e9a29d83",
        "http://uc-baobab.wdjcdn.com/1480060556060_b2e2578a_1280x720.mp4?t=1480262620&k=0581d1eada6ea38b",
        "http://uc-baobab.wdjcdn.com/1479904452346_1cc4dc0b.mp4?t=1480262639&k=283c8ff3139ecd7c",
        "http://uc-baobab.wdjcdn.com/1476327025495_fddf2a25_1280x720.mp4?t=1480262537&k=ce1982e1c49df3b2"
    );
    private List<String> texts = Arrays.asList(
            "","面朝大海，春暖花开",
            "燃烧吧，摩托",
            "做男模，颜值是唯一的标准吗？",
            "S...B",
            "Handsome",
            "future",
            "希望"
    );

    private List<Integer> images = Arrays.asList(
            R.drawable.img1, R.drawable.img3, R.drawable.img5, R.drawable.img6,
            R.drawable.img7, R.drawable.img8, R.drawable.img9, R.drawable.img10
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
            LinearLayout view = new LinearLayout(mContext);
            view.setGravity(Gravity.CENTER);
            view.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, Utils.dip2px(mContext, 150)));
            view.setBackgroundResource(images.get(index));
            view.setTag(urls.get(index));
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(mContext, VideoActivity.class);
                    intent.putExtra("url",v.getTag().toString());
                    startActivity(intent);
                }
            });
            TextView tview = new TextView(mContext);
            tview.setText(texts.get(index));
            tview.setTextSize(18);
            tview.setTextColor(Color.parseColor("#FFFFFF"));
            view.addView(tview);
            view.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
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
