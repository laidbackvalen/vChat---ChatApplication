package com.example.firebasefirestoredatabase.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

import com.example.firebasefirestoredatabase.R;
import com.example.firebasefirestoredatabase.adapter.SearchUserRecyclerAdapter;
import com.example.firebasefirestoredatabase.model.UserModelData;
import com.example.firebasefirestoredatabase.firebaseutils.FirebaseUtil;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.firestore.Query;

public class SearchUserActivity extends AppCompatActivity {
    ImageView backIcon, searchIconImage;
    RecyclerView recyclerViewSearch;
    EditText searchInput;
    SearchUserRecyclerAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_user);

        backIcon = findViewById(R.id.backIconSearchUser);
        recyclerViewSearch = findViewById(R.id.recyclerViewSearchActivity);
        searchInput = findViewById(R.id.searchInputEdittext);
        searchIconImage = findViewById(R.id.searchIconSearchActivity);

        backIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), MainActivity.class));
                finish();
            }
        });

        searchIconImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String searchTerm = searchInput.getText().toString();
                if (searchTerm.isEmpty() || searchTerm.length() < 3) {
                    searchInput.setError("Invalid Username");
                    return;
                }
                setupSearchRecyclerView(searchTerm);
            }
        });
    }

    private void setupSearchRecyclerView(String searchTerm) {

        Query query = FirebaseUtil.allUserCollectionReference().whereGreaterThanOrEqualTo("username", searchTerm.toLowerCase().toUpperCase());
        FirestoreRecyclerOptions<UserModelData> options = new FirestoreRecyclerOptions.Builder<UserModelData>().setQuery(query, UserModelData.class).build();

        adapter = new SearchUserRecyclerAdapter(options, getApplicationContext());
        recyclerViewSearch.setLayoutManager(new LinearLayoutManager(this));
        recyclerViewSearch.setAdapter(adapter);
        adapter.startListening();
    }

    @Override
    protected void onStart() {
        super.onStart();
        if(adapter!=null){
            adapter.startListening();
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        if(adapter!=null){
            adapter.startListening();
        }

    }

    @Override
    protected void onResume() {
        super.onResume();
        if(adapter!=null){
            adapter.startListening();
        }
    }
}