package com.example.whatsave.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.whatsave.R;
import com.example.whatsave.helper.Constants;

import java.io.File;
import java.util.Objects;

public class ViewImage extends AppCompatActivity {
    private ImageView mImageView1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_image);
        String titleImage;
        init();
        Intent intent = getIntent();
        titleImage = intent.getStringExtra("image");
        if (titleImage != null) {
            showImage(titleImage);
        }
    }

    private void showImage(String title) {
        Objects.requireNonNull(getSupportActionBar()).setTitle(Constants.TOOLBAR_NAME);
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

    private void init() {
        mImageView1 = findViewById(R.id.myImageViewMain1);
    }
}