package com.example.firebasefirestoredatabase.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.firebasefirestoredatabase.R;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.firebase.auth.FirebaseAuth;
import com.hbb20.CountryCodePicker;

public class GenerateOTPActivity extends AppCompatActivity {
    EditText inputUserPhoneNumber;
    Button generateOTPButton;
    CardView googleCardView;

    private static final String TAG = "GoogleActivity";
    private static final int RC_SIGN_IN = 9001;

    // [START declare_auth]
    private FirebaseAuth mAuth;
    // [END declare_auth]

    private GoogleSignInClient mGoogleSignInClient;

    CountryCodePicker countrycodePicker;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_generate_otp);

        generateOTPButton = findViewById(R.id.loginButton);
        inputUserPhoneNumber = findViewById(R.id.editTextPhoneNumber);
        countrycodePicker = findViewById(R.id.countryCodePicker);
        googleCardView = findViewById(R.id.cardViewGoogle);

        //COUNTRY CODE PICKER
        countrycodePicker.registerCarrierNumberEditText(inputUserPhoneNumber);


        //GENERATE OTP on click
        generateOTPButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //IF CONDITIONS
                if (inputUserPhoneNumber.getText().toString().isEmpty()) {
                    inputUserPhoneNumber.setError("Empty Phone Number");
                } else if (inputUserPhoneNumber.getText().toString().length() < 10) {
                    inputUserPhoneNumber.setError("Number Must Contain 10 Digits");
                } else {
                    //INTENT TO SEND NUMBER FROM GenerateOTPActivity TO  UserVerifyingActivity()
                    Intent intent = new Intent(GenerateOTPActivity.this, UserVerifyingActivity.class);
                    intent.putExtra("phone", countrycodePicker.getFullNumberWithPlus());
                    startActivity(intent);
                    finish();
                }

            }
        });
    }

//        googleCardView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                signIn();
//            }
//        });
//
//
//        //GOOGLE SIGN IN
//
//        // [START config_signin]
//        // Configure Google Sign In
//        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
//                .requestIdToken(getString(R.string.default_web_client_id))
//                .requestEmail()
//                .build();
//
//        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);
//        // [END config_signin]
//
//        // [START initialize_auth]
//        // Initialize Firebase Auth
//        mAuth = FirebaseAuth.getInstance();
//        // [END initialize_auth]
//    }
//
//    // [START on_start_check_user]
//    @Override
//    public void onStart() {
//        super.onStart();
//        // Check if user is signed in (non-null) and update UI accordingly.
//        FirebaseUser currentUser = mAuth.getCurrentUser();
//        updateUI(currentUser);
//    }
//    // [END on_start_check_user]
//
//    // [START onactivityresult]
//    @Override
//    public void onActivityResult(int requestCode, int resultCode, Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//
//        // Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(...);
//        if (requestCode == RC_SIGN_IN) {
//            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
//            try {
//                // Google Sign In was successful, authenticate with Firebase
//                GoogleSignInAccount account = (GoogleSignInAccount) ((Task<?>) task).getResult(ApiException.class);
//                Log.d(TAG, "firebaseAuthWithGoogle:" + account.getId());
//                firebaseAuthWithGoogle(account.getIdToken());
//            } catch (ApiException e) {
//                // Google Sign In failed, update UI appropriately
//                Log.w(TAG, "Google sign in failed", e);
//            }
//        }
//    }
//    // [END onactivityresult]
//
//    // [START auth_with_google]
//    private void firebaseAuthWithGoogle(String idToken) {
//        AuthCredential credential = GoogleAuthProvider.getCredential(idToken, null);
//        mAuth.signInWithCredential(credential)
//                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
//                    @Override
//                    public void onComplete(@NonNull Task<AuthResult> task) {
//                        if (task.isSuccessful()) {
//                            // Sign in success, update UI with the signed-in user's information
//                            Log.d(TAG, "signInWithCredential:success");
//                            FirebaseUser user = mAuth.getCurrentUser();
//                            updateUI(user);
//
//                        } else {
//                            // If sign in fails, display a message to the user.
//                            Log.w(TAG, "signInWithCredential:failure", task.getException());
//                            updateUI(null);
//                        }
//                    }
//                });
//    }
//    // [END auth_with_google]
//
//    // [START signin]
//    private void signIn() {
//        Intent signInIntent = mGoogleSignInClient.getSignInIntent();
//        startActivityForResult(signInIntent, RC_SIGN_IN);
//    }
//    // [END signin]
//
//    private void updateUI(FirebaseUser user) {
//        startActivity(new Intent(getApplicationContext(), MainActivity.class));
//    }
}







