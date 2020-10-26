package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager2.widget.ViewPager2;

import android.os.Bundle;

import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

import org.jetbrains.annotations.NotNull;

public class DetailsContact extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details_contact);


        ViewPager2 viewPager2 = findViewById(R.id.tabviewpager2);
        viewPager2.setAdapter(new DetailPagerAdapter(this));


        TabLayout tabLayout = findViewById(R.id.tab_layout);
        TabLayoutMediator tabLayoutMediator = new TabLayoutMediator(tabLayout, viewPager2, new TabLayoutMediator.TabConfigurationStrategy() {
            @Override
            public void onConfigureTab(@NonNull @NotNull TabLayout.Tab tab, int position) {
                switch (position) {
                    case 0: {
                        tab.setText("Documnets");
                        tab.setIcon(R.drawable.ic_pending);
                        break;
                    }
                    case 1: {
                        tab.setText("Image");
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
                        tab.setText("Location");
                        tab.setIcon(R.drawable.ic_location);
                        break;
                    }
                }
            }
        });
        tabLayoutMediator.attach();
    }
}