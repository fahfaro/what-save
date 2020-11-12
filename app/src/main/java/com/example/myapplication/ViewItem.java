package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.MediaController;
import android.widget.Toast;
import android.widget.VideoView;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

public class ViewItem extends AppCompatActivity {
    private Button btnBack, btnPlay, btnStop;
    private VideoView videoView;
    private ImageView mImageView;
    private MediaPlayer mPlayer;
    private static final String DIR_NAME_IMAGE = "Images";
    private static final String DIR_NAME_AUDIO = "Audio";
    private static final String IMAGE_NAME = "5285.jpeg";
    private static final String AUDIO_NAME = "4584.mp3";
    private static final String VIDEO_NAME = "5080.mp4";
    private static final String DIR_NAME_VIDEO = "Video";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_item);
        init();
        Intent intent = getIntent();
        String titleLocation, titleImage, titleAudio, titleVideo, titleDocument;
        titleLocation = intent.getStringExtra("Location");
        titleImage = intent.getStringExtra("Image");
        titleAudio = intent.getStringExtra("Audio");
        titleVideo = intent.getStringExtra("Video");
        titleDocument = intent.getStringExtra("Document");
        if (titleLocation != null) {

        } else if (titleImage != null) {
            showImage(titleImage);
        } else if (titleAudio != null) {
            playAudio(titleAudio);
        } else if (titleVideo != null) {
            playVideo(titleVideo);
        } else if (titleDocument != null) {

        }
    }

    private void showImage(String title) {
        mImageView.setVisibility(View.VISIBLE);
        Bitmap image = null;
        File path = getDir(DIR_NAME_IMAGE, MODE_PRIVATE);
        File file = new File(path, title);
        InputStream inputStream = null;
        if (file.exists()) {
            try {
                inputStream = new FileInputStream(file);
                image = BitmapFactory.decodeStream(inputStream);
                mImageView.setImageBitmap(image);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        } else {
            Toast.makeText(this, "No", Toast.LENGTH_SHORT).show();
        }
    }

    private void playAudio(String title) {
        btnBack.setVisibility(View.VISIBLE);
        btnPlay.setVisibility(View.VISIBLE);
        btnStop.setVisibility(View.VISIBLE);
        btnStop.setOnClickListener(this::stopPlay);
        File path = getDir(DIR_NAME_AUDIO, MODE_PRIVATE);
        File file = new File(path, title);
        if (file.exists()) {
            mPlayer = MediaPlayer.create(this, Uri.fromFile(file));
            mPlayer.setLooping(true);
            mPlayer.start();
        } else {
            Toast.makeText(this, "No", Toast.LENGTH_SHORT).show();
        }
    }

    private void stopPlay(View view) {
        mPlayer.stop();
    }

    private void playVideo(String title) {
        videoView.setVisibility(View.VISIBLE);
        File path = getDir(DIR_NAME_VIDEO, MODE_PRIVATE);
        File file = new File(path, title);
        if (file.exists()) {
            MediaController mediaController = new MediaController(this);
            videoView.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                @Override
                public void onPrepared(MediaPlayer mp) {
                    mediaController.setAnchorView(videoView);
                }
            });
            videoView.setMediaController(mediaController);
            videoView.setVideoPath(String.valueOf(file));
            videoView.start();
        } else {
            Toast.makeText(this, "No", Toast.LENGTH_SHORT).show();
        }
    }

    private void init() {
        mImageView = findViewById(R.id.myImageView);
        videoView = findViewById(R.id.videoview);
        btnBack = findViewById(R.id.btn_back);
        btnPlay = findViewById(R.id.btn_play);
        btnStop = findViewById(R.id.btn_stop);
    }
}