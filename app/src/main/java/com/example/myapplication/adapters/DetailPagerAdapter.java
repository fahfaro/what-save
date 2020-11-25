package com.example.myapplication.adapters;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.example.myapplication.fragments.AudioFragment;
import com.example.myapplication.fragments.ImageFragment;
import com.example.myapplication.fragments.PdfFragment;
import com.example.myapplication.fragments.VideoFragment;

import org.jetbrains.annotations.NotNull;

public class DetailPagerAdapter extends FragmentStateAdapter {

    public DetailPagerAdapter(@NonNull @NotNull FragmentActivity fragmentActivity) {
        super(fragmentActivity);
    }

    @NotNull
    @Override
    public androidx.fragment.app.Fragment createFragment(int position) {
        switch (position) {
            case 0: {
                return new PdfFragment();
            }
            case 1: {
                return new ImageFragment();
            }
            case 2: {
                return new AudioFragment();
            }
            default: {
                return new VideoFragment();
            }
//            default:
//                return new DocumentFragment();
        }
    }

    @Override
    public int getItemCount() {
        return 4;
    }

}