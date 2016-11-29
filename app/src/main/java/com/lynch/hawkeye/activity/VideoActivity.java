package com.lynch.hawkeye.activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.MediaController;
import android.widget.TextView;
import android.widget.VideoView;

import com.lynch.hawkeye.R;
import com.lynch.hawkeye.utils.Utils;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

import fm.jiecao.jcvideoplayer_lib.JCVideoPlayer;
import fm.jiecao.jcvideoplayer_lib.JCVideoPlayerStandard;

public class VideoActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video);

        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        String url = bundle.getString("url");
        String title = bundle.getString("title");
        if(!Utils.isNullOrEmpty(url)){
            TextView textView = (TextView)findViewById(R.id.video_title);
            textView.setText(title);
            JCVideoPlayerStandard jcVideoPlayerStandard = (JCVideoPlayerStandard) findViewById(R.id.videoplayer);
            //jcVideoPlayerStandard.startFullscreen(VideoActivity.this,JCVideoPlayerStandard.class,url,title);
            jcVideoPlayerStandard.setUp(url, JCVideoPlayerStandard.SCREEN_LAYOUT_LIST, title);
        }

        final LinearLayout linearLayout = (LinearLayout)findViewById(R.id.video_background);
        Picasso.with(this).load("http://img1.3lian.com/img013/v4/96/d/44.jpg")
                //http://img.kaiyanapp.com/58a5de91628e61efb969a71fdad52c99.jpeg?imageMogr2/quality/100") //.into(linearLayout);
        .into(new Target() {
            @Override
            public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {
                if (Build.VERSION.SDK_INT < 16) {
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
        });
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
        JCVideoPlayer.releaseAllVideos();
    }

    @Override
    protected void onStart() {
        super.onStart();
    }
}
