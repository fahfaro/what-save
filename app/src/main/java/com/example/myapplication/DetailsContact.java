package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager2.widget.ViewPager2;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class DetailsContact extends AppCompatActivity {
    private String textdata;
    EditText editText;
    private TextView mTextView;
    private ImageView mImageView;
    private ViewPager2 viewPager2;
    DBHelper db;
    Button btn;
    String messag;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details_contact);
        db = new DBHelper(this);

//        btn = findViewById(R.id.btn);
//        btn.setOnClickListener(this::insert);
        Intent intent = getIntent();
        if (intent != null) {
            String action = intent.getAction();
            String type = intent.getType();
            if (Intent.ACTION_SEND.equals(action) && type != null) {
                if (type.equalsIgnoreCase("text/plain")) {
                    handleTextData(intent);
                } else if (type.startsWith("image/")) {
                    handleImage(intent);
                } else if (type.equalsIgnoreCase("application/pdf")) {
                    handlePdfFile(intent);
                }
            } else if (Intent.ACTION_SEND_MULTIPLE.equals(action) && type != null) {
                if (type.startsWith("image/")) {
                    handleMultipleImage(intent);
                }
            }
        }


        viewPager2 = findViewById(R.id.tabviewpager2);
        viewPager2.setAdapter(new DetailPagerAdapter(this));

        TabLayout tabLayout = findViewById(R.id.tab_layout);

        TabLayoutMediator tabLayoutMediator = new TabLayoutMediator(tabLayout, viewPager2, new TabLayoutMediator.TabConfigurationStrategy() {
            @Override
            public void onConfigureTab(@NonNull @NotNull TabLayout.Tab tab, int position) {
                switch (position) {
                    case 0: {
                        tab.setText("Location");
                        tab.setIcon(R.drawable.ic_location);
                        break;
                    }
                    case 1: {
                        tab.setText("Images");
                        tab.setIcon(R.drawable.ic_image);
                        break;
                    }
                    case 2: {
                        tab.setText("Audio");
                        tab.setIcon(R.drawable.ic_audio);
                        break;
                    }
                    case 3: {
                        tab.setText("Video");
                        tab.setIcon(R.drawable.ic_video);
                        break;
                    }
                    case 4: {
                        tab.setText("Documents");
                        tab.setIcon(R.drawable.ic_pending);
                        break;
                    }
                }
            }
        });
        tabLayoutMediator.attach();
    }

    private void insert(View view) {
//        editText = findViewById(R.id.ed_text);
//        messag = editText.getText().toString();
        db.insertData(messag);
    }

    private void handlePdfFile(Intent intent) {
        Uri pdffile = intent.getParcelableExtra(Intent.EXTRA_STREAM);
        if (pdffile != null) {
            Log.d("Pdf File Path : ", "" + pdffile.getPath());
        }
    }

    private void handleImage(Intent intent) {
        Uri image = intent.getParcelableExtra(Intent.EXTRA_STREAM);
        if (image != null) {
//            mImageView = findViewById(R.id.myImageView);
//            mImageView.setVisibility(View.VISIBLE);
//            mImageView.setImageURI(image);
            Log.d("Image File Path : ", "" + image.getPath());
        }
    }

    private void handleTextData(Intent intent) {
        textdata = intent.getStringExtra(Intent.EXTRA_TEXT);
        if (textdata != null) {

//            mTextView = findViewById(R.id.myFrTextView);
//            mTextView.setVisibility(View.VISIBLE);
//            mTextView.setText(textdata);


            Log.d("TextDat", "" + textdata);
        }
    }

    private void handleMultipleImage(Intent intent) {
        ArrayList<Uri> imageList = intent.getParcelableArrayListExtra(Intent.EXTRA_STREAM);
        if (imageList != null) {
            for (Uri uri : imageList) {
                Log.d("Path ", "" + uri.getPath());
            }
        }
    }
}