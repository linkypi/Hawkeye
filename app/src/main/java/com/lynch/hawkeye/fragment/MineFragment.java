package com.lynch.hawkeye.fragment;

import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.lynch.hawkeye.activity.LoginActivity;
import com.lynch.hawkeye.R;
import com.lynch.hawkeye.component.RoundImageView;
import com.lynch.hawkeye.component.SelectPhotoPopupWindow;
import com.lynch.hawkeye.utils.AppContext;

import java.io.File;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link MineFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link MineFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MineFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private RoundImageView head_portrait;
    private View view;
    private static final int PHOTO_REQUEST_CAREMA = 1;
    private static final int PHOTO_REQUEST_GALLERY = 2;
    private static final int PHOTO_REQUEST_CUT = 3;
    /* 头像名称 */
    private static final String PHOTO_FILE_NAME = "temp_photo.jpg";
    private static Bitmap image;
    private  File tempFile;

    private OnFragmentInteractionListener mListener;
    private SelectPhotoPopupWindow popupWindow;

    public MineFragment() {
        // Required empty public constructor
    }

    public static MineFragment newInstance(String param1, String param2) {
        MineFragment fragment = new MineFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser) {
            //相当于Fragment的onResume
        } else {
            //相当于Fragment的onPause
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        if (getArguments() != null) {
//            mParam1 = getArguments().getString(ARG_PARAM1);
//            mParam2 = getArguments().getString(ARG_PARAM2);
//        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_mine, container, false);
        head_portrait = (RoundImageView)view.findViewById(R.id.head_portrait);
        head_portrait.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(AppContext.hasLogin){
                    if(popupWindow==null)
                    {   popupWindow = new SelectPhotoPopupWindow(view.getContext(), itemsOnClick); }

                    popupWindow.showAtLocation(view.findViewById(R.id.bottom),
                            Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL, 0, 0);
                }else{
                    Intent intent = new Intent(getActivity(), LoginActivity.class);
                    startActivity(intent);
                }

            }
        });

        return view;
    }
    //为弹出窗口实现监听类
    private View.OnClickListener itemsOnClick = new View.OnClickListener(){

        public void onClick(View v) {
            popupWindow.dismiss();
            switch (v.getId()) {
                case R.id.btn_take_photo:
                    camera(v);
                    break;
                case R.id.btn_select_photo:
                    getPhotoFromAlbum(v);
                    break;
                default:
                    break;
            }
        }
    };

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode)
        {
            case PHOTO_REQUEST_GALLERY:
                if (data != null) {
                    Uri uri = data.getData();
                    crop(uri);
                }
                break;
            case PHOTO_REQUEST_CAREMA:
                if (hasSdcard()) {
                    crop(Uri.fromFile(tempFile));
                } else {
                    Toast.makeText(getActivity(), "未找到存储卡，无法存储照片！",Toast.LENGTH_LONG).show();
                }
                break;
            case PHOTO_REQUEST_CUT:
                if (data != null) {
                    image = data.getParcelableExtra("data");
                    head_portrait.setImageBitmap(image);
                }
                try {
                    // 将临时文件删除
                    tempFile.delete();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;
            default:
                break;
        }

        super.onActivityResult(requestCode, resultCode, data);
    }
    private void crop(Uri uri) {
        // 裁剪图片意图
        Intent intent = new Intent("com.android.camera.action.CROP");
        intent.setDataAndType(uri, "image/*");
        intent.putExtra("crop", "true");
        // 裁剪框的比例，1：1
        intent.putExtra("aspectX", 1);
        intent.putExtra("aspectY", 1);
        // 裁剪后输出图片的尺寸大小
        intent.putExtra("outputX", 250);
        intent.putExtra("outputY", 250);

        intent.putExtra("outputFormat", "JPEG");// 图片格式
        intent.putExtra("noFaceDetection", true);// 取消人脸识别
        intent.putExtra("return-data", true);
        // 开启一个带有返回值的Activity，请求码为PHOTO_REQUEST_CUT
        startActivityForResult(intent, PHOTO_REQUEST_CUT);
    }

    public void camera(View view) {
        Intent intent = new Intent("android.media.action.IMAGE_CAPTURE");
        if (hasSdcard()) {
            tempFile = new File(Environment.getExternalStorageDirectory(), PHOTO_FILE_NAME);
            Uri uri = Uri.fromFile(tempFile);
            intent.putExtra(MediaStore.EXTRA_OUTPUT, uri);
        }
        startActivityForResult(intent, PHOTO_REQUEST_CAREMA);
    }

    public void getPhotoFromAlbum(View view) {
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType("image/*");
        // 开启一个带有返回值的Activity，请求码为PHOTO_REQUEST_GALLERY
        startActivityForResult(intent, PHOTO_REQUEST_GALLERY);
    }

    /*    判断sdcard是否被挂载     */
    private boolean hasSdcard() {
        return (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED));
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
