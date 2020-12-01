package com.example.whatsave.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.whatsave.R;
import com.example.whatsave.helper.Constants;
import com.khizar1556.mkvideoplayer.MKPlayerActivity;

import java.io.File;
import java.util.Objects;

public class VideoPlayer extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video_player);

        View videoView = findViewById(R.id.video_layout);
        Objects.requireNonNull(getSupportActionBar()).setTitle(Constants.TOOLBAR_NAME);
        Intent intent = getIntent();
        String title = intent.getStringExtra("video");
        File path = getDir(Constants.DIR_NAME_VIDEO, MODE_PRIVATE);
        File file = new File(path, title);
        if (file.exists()) {
            videoView.setVisibility(View.VISIBLE);
            MKPlayerActivity.configPlayer(this).play(String.valueOf(file));
        } else {
            Toast.makeText(this, "No", Toast.LENGTH_SHORT).show();
        }
        onBackPressed();
    }
}