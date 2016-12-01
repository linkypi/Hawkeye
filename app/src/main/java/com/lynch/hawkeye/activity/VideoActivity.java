package com.lynch.hawkeye.activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.media.Image;
import android.net.Uri;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.MediaController;
import android.widget.TextView;
import android.widget.VideoView;

import com.lynch.hawkeye.R;
import com.lynch.hawkeye.component.RoundImageView;
import com.lynch.hawkeye.model.Dto;
import com.lynch.hawkeye.utils.Utils;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

import fm.jiecao.jcvideoplayer_lib.JCVideoPlayer;
import fm.jiecao.jcvideoplayer_lib.JCVideoPlayerStandard;
import tv.danmaku.ijk.media.player.IjkMediaPlayer;

public class VideoActivity extends BaseActivity {

    JCVideoPlayer.JCAutoFullscreenListener sensorEventListener;
    SensorManager sensorManager;
    private ImageView imagCollect,imgShare,imgComment,imgDownload;
    private TextView collect_count,share_count,comment_count;
    private LinearLayout linearLayout;
    private RoundImageView imageProducer;
    private List<String> producerImageUrls = Arrays.asList(
            "http://img.kaiyanapp.com/f76d214f94c4120b5ce770099051b49c.jpeg",
            "http://img.kaiyanapp.com/ef6c59b92b487c6bb1e839589bf59876.jpeg",
            "http://img.kaiyanapp.com/bb71efb911bb607525508376f2ba2824.jpeg",
            "http://img.kaiyanapp.com/c2e2a3647e1d62a7f383f9ab25d08f8d.jpeg",
            "http://img.kaiyanapp.com/971af47cfff3f8b1fe5ab47d2c60105a.jpeg",
            "http://img.kaiyanapp.com/7f6daf8fefdc124f00d742e640b72088.jpeg"
    );


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video);
        sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        sensorEventListener = new JCVideoPlayer.JCAutoFullscreenListener();
        imagCollect = (ImageView)findViewById(R.id.img_collect);
        imgComment = (ImageView)findViewById(R.id.img_comment);
        imgDownload = (ImageView)findViewById(R.id.img_download);
        imgShare = (ImageView)findViewById(R.id.img_share);
        collect_count = (TextView)findViewById(R.id.collect_count);
        share_count = (TextView)findViewById(R.id.share_count);
        comment_count = (TextView)findViewById(R.id.comment_count);
        imageProducer = (RoundImageView)findViewById(R.id.img_producer);

        imagCollect.setTag(false);
        imagCollect.setOnClickListener(new OnImageViewClickListener());
        imgShare.setOnClickListener(new OnImageViewClickListener());
        imgComment.setOnClickListener(new OnImageViewClickListener());
        imgDownload.setOnClickListener(new OnImageViewClickListener());

        Intent intent = getIntent();
        Dto dto = (Dto)intent.getSerializableExtra("data");
        if(!Utils.isNullOrEmpty(dto.getUrl())){
            TextView textView = (TextView)findViewById(R.id.video_title);
            textView.setText(dto.getTitle());

//            IjkMediaPlayer.loadLibrariesOnce(null);
//            IjkMediaPlayer.native_profileBegin("libijkplayer.so");
//            IjkVideoView ijkPlayer = (IjkVideoView) findViewById(R.id.videoplayer);
//            ijkPlayer.setVideoURI(mVideoUri);
//            ijkPlayer.start();


            JCVideoPlayerStandard jcVideoPlayerStandard = (JCVideoPlayerStandard) findViewById(R.id.videoplayer);
//            jcVideoPlayerStandard.startFullscreen(VideoActivity.this,JCVideoPlayerStandard.class,url,title);
            jcVideoPlayerStandard.setUp(dto.getUrl(), JCVideoPlayerStandard.SCREEN_LAYOUT_LIST, dto.getTitle());
        }

        //加载背景图片
        linearLayout = (LinearLayout)findViewById(R.id.video_background);
        Picasso.with(this).load("http://img.kaiyanapp.com/58a5de91628e61efb969a71fdad52c99.jpeg?imageMogr2/quality/100")
        .into(new PicassoTarget());

        Random random = new Random();
        int index = random.nextInt(producerImageUrls.size()-1);
        Picasso.with(this).load(producerImageUrls.get(index)).into(imageProducer);
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
        Sensor accelerometerSensor = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        sensorManager.registerListener(sensorEventListener, accelerometerSensor, SensorManager.SENSOR_DELAY_NORMAL);
    }

    @Override
    public void onBackPressed() {
        if (JCVideoPlayer.backPress()) {
            return;
        }
        super.onBackPressed();
    }
    @Override
    protected void onPause() {
        super.onPause();
        //JCVideoPlayer.releaseAllVideos();
        sensorManager.unregisterListener(sensorEventListener);
    }

    @Override
    protected void onStart() {
        super.onStart();
    }
}
