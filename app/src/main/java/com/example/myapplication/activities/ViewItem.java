package com.example.myapplication.activities;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.MediaController;
import android.widget.Toast;
import android.widget.VideoView;

import com.bumptech.glide.Glide;
import com.example.myapplication.R;
import com.example.myapplication.helper.Constants;

import java.io.File;
import java.util.Objects;
public class ViewItem extends AppCompatActivity {
    private VideoView videoView;
    private ImageView mImageView1;
    private int count = 0;
    private File file;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_item);
        String titleImage;
        init();
        Intent intent = getIntent();
        titleImage = intent.getStringExtra("image");
        String titleVideo = intent.getStringExtra("video");
        if (titleImage != null) {
            showImage(titleImage);
        } else if (titleVideo != null) {
            playVideo(titleVideo);
        }
    }
    private void showImage(String title) {
        Objects.requireNonNull(getSupportActionBar()).setTitle("WhatsSave");
        mImageView1.setVisibility(View.VISIBLE);
        File path = getDir(Constants.DIR_NAME_IMAGE, MODE_PRIVATE);
        File file = new File(path, title);
        if (file.exists()) {
            try {
                Glide.with(this).load(file).into(mImageView1);
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            Toast.makeText(this, "No", Toast.LENGTH_SHORT).show();
        }
    }

    private void playVideo(String title) {
//        getSupportActionBar().hide();
        videoView.setVisibility(View.VISIBLE);
        File path = getDir(Constants.DIR_NAME_VIDEO, MODE_PRIVATE);
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
        mImageView1 = findViewById(R.id.myImageViewMain1);
        videoView = findViewById(R.id.videoview);
    }
}