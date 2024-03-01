package com.example.firebasefirestoredatabase.adapter;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.example.firebasefirestoredatabase.fragments.CallsFragment;
import com.example.firebasefirestoredatabase.fragments.ChatsFragment;
import com.example.firebasefirestoredatabase.fragments.GroupsFragment;
import com.example.firebasefirestoredatabase.fragments.StatusFragment;

public class ViewPagerMessengerAdapter extends FragmentPagerAdapter {
    public ViewPagerMessengerAdapter(@NonNull FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        if (position == 0) {
            return new GroupsFragment();
        } else if (position == 1) {
            return new ChatsFragment();
        } else if (position == 2) {
            return new StatusFragment();
        } else {
            return new CallsFragment();
        }


    }

    @Override
    public int getCount() {
        return 4;  //no.s of tab
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        if (position == 0) {
            return "Group";
        } else if (position == 1) {
            return "Chats";
        } else if (position == 2) {
            return "Updates";
        } else {
            return "Calls";
        }
    }
}

