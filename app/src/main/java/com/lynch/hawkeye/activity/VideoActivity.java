package com.lynch.hawkeye.activity;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.MediaController;
import android.widget.VideoView;

import com.lynch.hawkeye.R;
import com.lynch.hawkeye.utils.Utils;

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
        if(!Utils.isNullOrEmpty(url)){
//            VideoView videoView = (VideoView)findViewById(R.id.video_view);
//            videoView.setMediaController(new MediaController(this));
//            videoView.setVideoURI(Uri.parse(url));
//            videoView.requestFocus();
            JCVideoPlayerStandard jcVideoPlayerStandard = (JCVideoPlayerStandard) findViewById(R.id.videoplayer);
//            jcVideoPlayerStandard.setUp(url, JCVideoPlayerStandard.SCREEN_LAYOUT_LIST, "测试");
            jcVideoPlayerStandard.startFullscreen(VideoActivity.this,JCVideoPlayerStandard.class,url,"测试");
            //jcVideoPlayerStandard.thumbImageView.setback("http://p.qpic.cn/videoyun/0/2449_43b6f696980311e59ed467f22794e792_1/640");
        }
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
}
