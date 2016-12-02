package com.lynch.hawkeye.fragment;

import android.app.Fragment;
import android.content.Context;
import android.graphics.drawable.GradientDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.LinearInterpolator;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.lynch.hawkeye.R;
import com.lynch.hawkeye.utils.Utils;
import com.squareup.picasso.Picasso;

import java.util.Arrays;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link FindFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link FindFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FindFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private LinearLayout linearLayout;
    private Context context;

    private List<String> imageUrls = Arrays.asList("http://img.kaiyanapp.com/9f4c1559d54d4274e5463fba4262b284.jpeg",
            "http://img.kaiyanapp.com/cd74ae49d45ab6999bcd55dbae6d550f.jpeg",
            "http://img.kaiyanapp.com/00d60c80c7fa0db3bacd568b5999d045.jpeg",
            "http://img.kaiyanapp.com/482c741c06644f5566c7218096dbaf26.jpeg",
            "http://img.kaiyanapp.com/0117b9108c7cff43700db8af5e24f2bf.jpeg",
            "http://img.kaiyanapp.com/ac6971c1b9fc942c7547d25fc6c60ad2.jpeg",
            "http://img.kaiyanapp.com/2b7ac9d21ca06df7e39e80a3799a3fb6.jpeg",
            "http://img.kaiyanapp.com/a2fc6d32ac0b4f2842fb3d545d06f09b.jpeg");

    private OnFragmentInteractionListener mListener;

    public FindFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment FindFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static FindFragment newInstance(String param1, String param2) {
        FindFragment fragment = new FindFragment();
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

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_find, container, false);
        linearLayout = (LinearLayout) view.findViewById(R.id.content_find);
        this.context = getActivity();
        initViews();
        return view;
    }

    private void initViews() {
        for (int index = 0; index < 4; index++) {
            LinearLayout layout = new LinearLayout(context);
            LinearLayout.LayoutParams lparams = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT, Utils.dip2px(context, 150));
            lparams.weight = 1;
            layout.setOrientation(LinearLayout.HORIZONTAL);
            layout.setLayoutParams(lparams);

            for (int item = 0; item < 2; item++) {
                ImageView imageView = new ImageView(context);
                LinearLayout.LayoutParams ilps = new LinearLayout.LayoutParams(
                        RelativeLayout.LayoutParams.MATCH_PARENT,Utils.dip2px(context, 150));
                ilps.weight = 0.4f;
                imageView.setLayoutParams(ilps);
                imageView.setPadding(5,5,5,5);
                imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
                Picasso.with(context).load(imageUrls.get(index * 2 + item)).into(imageView);
                layout.addView(imageView);
            }
            linearLayout.addView(layout);
        }
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
