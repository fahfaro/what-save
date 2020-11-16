package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import org.jetbrains.annotations.NotNull;

public class DetailPagerAdapter extends FragmentStateAdapter {

    public DetailPagerAdapter(@NonNull @NotNull FragmentActivity fragmentActivity) {
        super(fragmentActivity);
    }

    @NotNull
    @Override
    public Fragment createFragment(int position) {
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
            case 3: {
                return new VideoFragment();
            }
            default:
                return new DocumentFragment();
        }
    }

    @Override
    public int getItemCount() {
        return 4;
    }

}