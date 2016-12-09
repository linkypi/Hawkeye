package com.lynch.hawkeye.activity;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.media.Image;
import android.media.MediaMetadataRetriever;
import android.media.ThumbnailUtils;
import android.net.Uri;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Display;
import android.view.KeyEvent;
import android.view.OrientationEventListener;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.MediaController;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.VideoView;

import com.dl7.player.media.IjkPlayerView;
import com.lynch.hawkeye.R;
import com.lynch.hawkeye.component.RoundImageView;
import com.lynch.hawkeye.model.Dto;
import com.lynch.hawkeye.utils.Utils;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

//import wseemann.media.FFmpegMediaMetadataRetriever;

//import wseemann.media.FFmpegMediaMetadataRetriever;

public class VideoActivity extends BaseActivity {

//    JCVideoPlayer.JCAutoFullscreenListener sensorEventListener;
    private SensorManager sensorManager;
    private ImageView imagCollect,imgShare,imgComment,imgDownload;
    private TextView collect_count,share_count,comment_count;
    private LinearLayout linearLayout;
    private RoundImageView imageProducer;
    private RelativeLayout layoutBack ;
    private IjkPlayerView playerView;
    private RelativeLayout layoutPlayer;
    private int height;

    private List<String> producerImageUrls = Arrays.asList(
            "http://img.kaiyanapp.com/f76d214f94c4120b5ce770099051b49c.jpeg",
            "http://img.kaiyanapp.com/ef6c59b92b487c6bb1e839589bf59876.jpeg",
            "http://img.kaiyanapp.com/bb71efb911bb607525508376f2ba2824.jpeg",
            "http://img.kaiyanapp.com/c2e2a3647e1d62a7f383f9ab25d08f8d.jpeg",
            "http://img.kaiyanapp.com/971af47cfff3f8b1fe5ab47d2c60105a.jpeg",
            "http://img.kaiyanapp.com/7f6daf8fefdc124f00d742e640b72088.jpeg"
    );

//    static {
//        System.loadLibrary("ijkffmpeg");
//        System.loadLibrary("ijksdl");
//        System.loadLibrary("ijkplayer");
//    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video);
//        sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
//        sensorEventListener = new JCVideoPlayer.JCAutoFullscreenListener();
//        setSupportActionBar();

        imagCollect = (ImageView)findViewById(R.id.img_collect);
        imgComment = (ImageView)findViewById(R.id.img_comment);
        imgDownload = (ImageView)findViewById(R.id.img_download);
        imgShare = (ImageView)findViewById(R.id.img_share);
        collect_count = (TextView)findViewById(R.id.collect_count);
        share_count = (TextView)findViewById(R.id.share_count);
        comment_count = (TextView)findViewById(R.id.comment_count);
        imageProducer = (RoundImageView)findViewById(R.id.img_producer);
        layoutBack = (RelativeLayout)findViewById(R.id.layout_video_back);
        playerView = (IjkPlayerView) findViewById(R.id.videoplayer);
        layoutPlayer = (RelativeLayout)findViewById(R.id.layout_player);
        height = layoutPlayer.getLayoutParams().height;

        imagCollect.setTag(false);
        imagCollect.setOnClickListener(new OnImageViewClickListener());
        imgShare.setOnClickListener(new OnImageViewClickListener());
        imgComment.setOnClickListener(new OnImageViewClickListener());
        imgDownload.setOnClickListener(new OnImageViewClickListener());
        layoutBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(VideoActivity.this ,MainActivity.class);
                startActivity(intent);
            }
        });

        Intent intent = getIntent();
        Dto dto = (Dto)intent.getSerializableExtra("data");
        initPlayer(dto);

//        startOrientationChangeListener();

        //加载背景图片
        linearLayout = (LinearLayout)findViewById(R.id.video_background);
        Picasso.with(this).load("http://img.kaiyanapp.com/58a5de91628e61efb969a71fdad52c99.jpeg?imageMogr2/quality/100")
        .into(new PicassoTarget());

        Random random = new Random();
        int index = random.nextInt(producerImageUrls.size()-1);
        Picasso.with(this).load(producerImageUrls.get(index)).into(imageProducer);
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {

        super.onConfigurationChanged(newConfig);
        playerView.configurationChanged(newConfig);
        //切换为竖屏

        if (newConfig.orientation == this.getResources().getConfiguration().ORIENTATION_PORTRAIT) {
            setPlayerHeight(height);
        }
        //切换为横屏
        if (newConfig.orientation == this.getResources().getConfiguration().ORIENTATION_LANDSCAPE) {

            DisplayMetrics dm = new DisplayMetrics();
            getWindowManager().getDefaultDisplay().getMetrics(dm);
            setPlayerHeight(dm.heightPixels);
        }
    }

    private void setPlayerHeight(int height){
        ViewGroup.LayoutParams params = layoutPlayer.getLayoutParams();
        params.height = height;
        layoutPlayer.setLayoutParams(params);
    }

    private void initPlayer(Dto data){

        //if(!Utils.isNullOrEmpty(data.getUrl())){
        TextView textView = (TextView)findViewById(R.id.video_title);
        textView.setText(data.getTitle());

//        MediaMetadataRetriever mmr=new MediaMetadataRetriever();
//        mmr.setDataSource(data.getUrl(), new HashMap<String, String>());
//        Bitmap bitmap=mmr.getFrameAtTime();

//        FFmpegMediaMetadataRetriever mmr = new FFmpegMediaMetadataRetriever();
//        mmr.setDataSource(data.getUrl());
//        mmr.extractMetadata(FFmpegMediaMetadataRetriever.METADATA_KEY_ALBUM);
//        mmr.extractMetadata(FFmpegMediaMetadataRetriever.METADATA_KEY_ARTIST);
//        Bitmap bitmap = mmr.getFrameAtTime(2000*1000, FFmpegMediaMetadataRetriever.OPTION_CLOSEST); // frame at 2 seconds
//        mmr.release();
//        if (bitmap.getWidth() > 640) {// 如果图片宽度规格超过640px,则进行压缩
//            bitmap = ThumbnailUtils.extractThumbnail(bitmap,
//                    640, 480,ThumbnailUtils.OPTIONS_RECYCLE_INPUT);
//        }

//        FFmpegMediaMetadataRetriever retriever = new FFmpegMediaMetadataRetriever();
//        try {
//            retriever.setDataSource(data.getUrl());
//            //here 5 means frame at the 5th sec.
//            Bitmap bitmap = retriever.getFrameAtTime(5);
//            playerView.mPlayerThumb.setImageBitmap( bitmap);
//        } catch (Exception ex) {
//            // Assume this is a corrupt video file
//            ex.printStackTrace();
//        }



        //  以下为配置接口，选择需要的调用
//        Picasso.with(this).load("http://img.kaiyanapp.com/58a5de91628e61efb969a71fdad52c99.jpeg?imageMogr2/quality/100")
//                .into(playerView.mPlayerThumb);    // 显示界面图
        playerView.init()              // 初始化，必须先调用
                .setTitle(data.getTitle())  // 设置标题，全屏时显示
                .setSkipTip(1000*60*1)  // 设置跳转提示
                .enableOrientation()    // 使能重力翻转
                .setVideoPath(data.getUrl())    // 设置视频Url，单个视频源可用这个
                //.setVideoSource(null, VIDEO_URL, VIDEO_URL, VIDEO_URL, null)    // 设置视频Url，多个视频源用这个
                .setMediaQuality(IjkPlayerView.MEDIA_QUALITY_HIGH)  // 指定初始视频源
                ;
                //.enableDanmaku();        // 使能弹幕功能
                //.setDanmakuSource(getResources().openRawResource(R.raw.comments))   // 添加弹幕资源，必须在enableDanmaku()后调用
//                .start();   // 启动播放
    }

    public class PicassoTarget implements Target
    {
        @Override
        public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {
            if (Build.VERSION.SDK_INT < Build.VERSION_CODES.JELLY_BEAN) {
                linearLayout.setBackgroundDrawable(new BitmapDrawable(bitmap));
            }else{
                linearLayout.setBackground(new BitmapDrawable(bitmap));
            }
        }

        @Override
        public void onBitmapFailed(Drawable errorDrawable) {
            Log.d("fail", "onBitmapFailed: ");
        }

        @Override
        public void onPrepareLoad(Drawable placeHolderDrawable) {

        }
    }

    public class  OnImageViewClickListener implements View.OnClickListener
    {
        @Override
        public void onClick(View v) {
            ImageView imgview = (ImageView)v;
            switch (v.getId()){
                case R.id.img_collect:
                    collect(imgview);
                    break;
                case R.id.img_share:
                    share(imgview);
                    break;
                case R.id.img_comment:
                    comment(imgview);
                    break;
                case R.id.img_download:
                    download(imgview);
                    break;
            }
        }

        private void collect(ImageView view){
            Boolean flag = (Boolean) view.getTag();
            int count = Integer.parseInt(collect_count.getText().toString());
            if(flag){
                count--;
                view.setBackgroundResource(R.drawable.uncollect);
            }else {
                count++;
                view.setBackgroundResource(R.drawable.collected);
            }
            view.setTag(!flag);
            collect_count.setText(""+count);
        }

        private void share(ImageView view){
            int count = Integer.parseInt(share_count.getText().toString());
            count++;
            share_count.setText(""+count);
        }

        private void comment(ImageView view){
            int count = Integer.parseInt(comment_count.getText().toString());
            count++;
            comment_count.setText(""+count);
        }

        private void download(ImageView view){
           showMsg("下载中...");
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        playerView.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        playerView.onPause();
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        playerView.onDestroy();
    }

//    @Override
//    public void onConfigurationChanged(Configuration newConfig) {
//        super.onConfigurationChanged(newConfig);
//        playerView.configurationChanged(newConfig);
//    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (playerView.handleVolumeKey(keyCode)) {
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }
    @Override
    public void onBackPressed() {
        if (playerView.onBackPressed()) {
            return;
        }
        super.onBackPressed();
    }

    @Override
    protected void onStart() {
        super.onStart();
    }
}
