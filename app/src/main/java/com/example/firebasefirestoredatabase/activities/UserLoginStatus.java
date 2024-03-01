package com.example.firebasefirestoredatabase.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.firebasefirestoredatabase.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

public class UserLoginStatus extends AppCompatActivity {
    View userRegistration;
    EditText userPhoneNumber;
    Button letmein;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_login_status);

        userPhoneNumber = findViewById(R.id.editTextPhoneNumberLoginPage);
        userRegistration = findViewById(R.id.view2);
        letmein = findViewById(R.id.letMeInButton);


        userRegistration.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), GenerateOTPActivity.class));
            }
        });

        String number = userPhoneNumber.getText().toString();

        letmein.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                checkUserExistsStatus(number);
            }
        });


    }

    private void checkUserExistsStatus(String number) {
        FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
        CollectionReference collectionReference = FirebaseFirestore.getInstance().collection("users");

        collectionReference.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    for (DocumentSnapshot documentsnapshot : task.getResult()) {
                        if (documentsnapshot.getString("phone").contains(number)) {
                            Toast.makeText(UserLoginStatus.this, "" + documentsnapshot.getId(), Toast.LENGTH_SHORT).show();
                            break;

                        }

//                       else   {
//                            if (!documentsnapshot.getString("phone").equals(number)) {
//                                Toast.makeText(UserLoginStatus.this, "User Doesn't Exists", Toast.LENGTH_SHORT).show();
//                                break;
//                            }

//                        }

                    }
                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {

            }
        });
//        documentReference.addSnapshotListener(new EventListener<DocumentSnapshot>() {
//            @Override
//            public void onEvent(@Nullable DocumentSnapshot value, @Nullable FirebaseFirestoreException error) {
//                if (value.exists()) {
//                    startActivity(new Intent(getApplicationContext(), MainActivity.class));
//                }
//
//            }
//        });


    }
}