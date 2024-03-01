package com.example.firebasefirestoredatabase.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.firebasefirestoredatabase.R;
import com.example.firebasefirestoredatabase.model.UserModelData;
import com.example.firebasefirestoredatabase.firebaseutils.FirebaseUtil;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.Timestamp;
import com.google.firebase.firestore.DocumentSnapshot;

public class UserInfoActivity extends AppCompatActivity {
    TextInputLayout textInputLayout;
    TextInputEditText textInputEditText;
    String phoneNumber;
    Button userNameSubmitButton;
    UserModelData userModelData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_info);

        textInputEditText = findViewById(R.id.userNameInputEditText);
        textInputLayout = findViewById(R.id.textInputLayout);
        userNameSubmitButton = findViewById(R.id.submitUserNameButton);

        phoneNumber = getIntent().getExtras().getString("phone");   //PROBLEM

        getUserName();

        userNameSubmitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setUserName();
            }
        });

    }

    private void setUserName() {
        String username = textInputEditText.getText().toString();
        if (username.isEmpty() || username.length()<3) {// || username.length() < 15
            textInputEditText.setError("Username length must be at least 3 letters"); //Username length must be at least 15 letters
            return;
        }
        if (userModelData != null) {
            userModelData.setUsername((username));
        } else {
            userModelData = new UserModelData(phoneNumber, username, Timestamp.now(), FirebaseUtil.currentUserId());
        }
        FirebaseUtil.currentUserDetails().set(userModelData).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()) {
                    Intent intent = new Intent(UserInfoActivity.this, MainActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(intent);
                }
            }
        });
    }


    private void getUserName() {
        FirebaseUtil.currentUserDetails().get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    userModelData = task.getResult().toObject(UserModelData.class);
                    if (userModelData != null) {
                        textInputEditText.setText(userModelData.getUsername());
                    }
                }
            }
        });
    }
}