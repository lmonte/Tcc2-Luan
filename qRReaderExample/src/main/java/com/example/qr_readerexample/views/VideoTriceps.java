package com.example.qr_readerexample.views;

import android.app.Activity;
import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.MediaController;
import android.widget.VideoView;

import com.example.qr_readerexample.R;

/**
 * Created by LuanMonte on 03/06/16.
 */
public class VideoTriceps extends Activity{
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_video);
        MediaController mc=new MediaController(this);

        VideoView view = (VideoView)findViewById(R.id.videoView);
//      mc.setAnchorView(view);

        String path = "android.resource://" + getPackageName() + "/" + R.raw.triceps;
        view.setVideoURI(Uri.parse(path));
        mc.setMediaPlayer(view);
        view.setMediaController(mc);
        view.requestFocus();
        view.start();


        final Button button = (Button) findViewById(R.id.video_ok_button);

    }

    public void qrCodeView(View view) {
        // TODO
        // Usando Intent para se comunicar com outras activitys.
        Intent i = new Intent(this, DecoderActivity.class);
        startActivity(i);
    }

    public void onCompletion(MediaPlayer arg0) {
        arg0.start();
    }

}