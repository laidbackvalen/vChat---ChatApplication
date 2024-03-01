package com.example.firebasefirestoredatabase.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.fragment.app.Fragment;

import com.example.firebasefirestoredatabase.activities.GenerateOTPActivity;
import com.example.firebasefirestoredatabase.R;
import com.google.firebase.auth.FirebaseAuth;


public class GroupsFragment extends Fragment {


    public GroupsFragment() {

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_groups, container, false);

        Button signOut = view.findViewById(R.id.signOut);

        signOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseAuth.getInstance().signOut();
                startActivity(new Intent(getContext(), GenerateOTPActivity.class));
                requireActivity().finish();
            }
        });
        return view;
    }

}