package com.example.myapplication.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.MediaController;
import android.widget.Toast;
import android.widget.VideoView;

import com.bumptech.glide.Glide;
import com.example.myapplication.R;

import java.io.File;
import java.util.Objects;

public class ViewItem extends AppCompatActivity {
    private Button btnBack, btnPlay, btnStop;
    private VideoView videoView;
    private ImageView mImageView;
    private MediaPlayer mPlayer;
    private static final String DIR_NAME_IMAGE = "images";
    private static final String DIR_NAME_AUDIO = "audio";
    private static final String DIR_NAME_VIDEO = "video";
//    private PowerManager.WakeLock wl;
    private int count = 0;
    private File file;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_item);
//        String titleLocation;
        String titleImage;
        String titleAudio;
//        String titleDocument;
        init();
        Intent intent = getIntent();
//        titleLocation = intent.getStringExtra("pdf");
        titleImage = intent.getStringExtra("image");
        titleAudio = intent.getStringExtra("audio");
        String titleVideo = intent.getStringExtra("video");
//        titleDocument = intent.getStringExtra("document");
        /*if (titleLocation != null) {
        } else*/ if (titleImage != null) {
            showImage(titleImage);
        } else if (titleAudio != null) {
            playAudio(titleAudio);
        } else if (titleVideo != null) {
            playVideo(titleVideo);
        } /*else if (titleDocument != null) {

        }*/
    }

    @Override
    protected void onDestroy() {
        if (count == 1){mPlayer.stop();}
        super.onDestroy();
    }

    private void showImage(String title) {
        Objects.requireNonNull(getSupportActionBar()).setTitle("WhatsSave");
        mImageView.setVisibility(View.VISIBLE);
        File path = getDir(DIR_NAME_IMAGE, MODE_PRIVATE);
        File file = new File(path, title);
        if (file.exists()) {
            try {
                Glide.with(this).load(file).into(mImageView);
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            Toast.makeText(this, "No", Toast.LENGTH_SHORT).show();
        }
    }

    private void playAudio(String title) {
        Objects.requireNonNull(getSupportActionBar()).setTitle("WhatsSave");
        btnBack.setVisibility(View.VISIBLE);
        btnPlay.setVisibility(View.VISIBLE);
        btnStop.setVisibility(View.VISIBLE);
        btnBack.setOnClickListener(this::backParent);
        btnPlay.setOnClickListener(this::playAudio);
        btnStop.setOnClickListener(this::stopPlay);
        File path = getDir(DIR_NAME_AUDIO, MODE_PRIVATE);
        file = new File(path, title);
        if (file.exists()) {

            mPlayer = MediaPlayer.create(this, Uri.fromFile(file));
            mPlayer.setLooping(true);

            if (count == 0) {
                mPlayer.start();
                count++;
            } else {
                mPlayer.pause();
                count--;
            }
        } else {
            Toast.makeText(this, "No", Toast.LENGTH_SHORT).show();
        }
    }

    private void backParent(View view) {
        mPlayer.stop();
        Intent intent = new Intent(this, DetailsContact.class);
        startActivity(intent);
    }

    private void playpause() {
        if (count < 0) {
            count++;
            mPlayer = MediaPlayer.create(this, Uri.fromFile(file));
            mPlayer.setLooping(true);
            mPlayer.start();
        } else if (count == 0) {
            mPlayer.start();
            count++;
        } else {
            mPlayer.pause();
            count--;
        }

    }

    private void playAudio(View view) {
        playpause();
    }

    private void stopPlay(View view) {
        mPlayer.stop();
        count--;
    }

    private void playVideo(String title) {
//        getSupportActionBar().hide();
        videoView.setVisibility(View.VISIBLE);
        File path = getDir(DIR_NAME_VIDEO, MODE_PRIVATE);
        File file = new File(path, title);
        if (file.exists()) {
            MediaController mediaController = new MediaController(this);
            videoView.setOnPreparedListener(mp -> mediaController.setAnchorView(videoView));
            videoView.setMediaController(mediaController);
            videoView.setVideoPath(String.valueOf(file));
            videoView.start();
        } else {
            Toast.makeText(this, "No", Toast.LENGTH_SHORT).show();
        }
    }

    private void init() {
        mImageView = findViewById(R.id.myImageViewMain);
        videoView = findViewById(R.id.videoview);
        btnBack = findViewById(R.id.btn_back);
        btnPlay = findViewById(R.id.btn_play);
        btnStop = findViewById(R.id.btn_stop);
    }
}