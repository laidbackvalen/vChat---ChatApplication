package com.example.firebasefirestoredatabase.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.chaos.view.PinView;
import com.example.firebasefirestoredatabase.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthOptions;
import com.google.firebase.auth.PhoneAuthProvider;

import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.TimeUnit;

public class UserVerifyingActivity extends AppCompatActivity {
    PinView pinView;  //this will be used to take OTP
    TextView setNumber; //this is the TextView Where we will show user's phone number entered in the GenerateOTPActivity to be displayed in this Activity
    Button verifyButton;  //on clicking on this button we will verify the code
    String phoneNumber;   //we will take user's phone number from intent and store it in this String phoneNumber

    View resendCodeView;

    String verificationCode;  //WE NEED THIS TO VERIFY THE OTP
    PhoneAuthProvider.ForceResendingToken resendingToken;  //THIS IS USE TO RE-SEND THE OTP TO THE USER
    FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();

    public Long timeOutSeconds = 60L;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_verifying);


        //LINKING WITH XML
        pinView = findViewById(R.id.firstPinView);
        verifyButton = findViewById(R.id.verifyAndContinueButton);
        setNumber = findViewById(R.id.numberRetrieved);
        resendCodeView = findViewById(R.id.viewDidntRecieveCode);


        //Getting value of phone number using intent coming from GenerateOTPActivity
        phoneNumber = getIntent().getExtras().getString("phone");

//        Map<String, String> data = new HashMap<>();
//        FirebaseFirestore.getInstance().collection("test").add(data);

        verifyPhoneNumber(phoneNumber);
        setNumber.setText(phoneNumber);

        //VERIFY AND CLICK BUTTON ON CLICK
        verifyButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //TAKING CODE AS A INPUT FROM PIN VIEW AND STORING IT IN STRING CODE
                String enteredOTP = pinView.getText().toString();
                //PASSING CODE VALUE TO FUNCTION verifycode()
                verifyCode(enteredOTP);
            }
        });

        startResendTimer();


//        resendCodeView.setOnClickListener(view -> {
//
//        });


        //USING LAMBDA FUNCTION
//        resendCodeView.setOnClickListener(view -> verifyPhoneNumber(phoneNuber));
    }


    //mCallbacks
    private PhoneAuthProvider.OnVerificationStateChangedCallbacks mCallbacks = new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {

        @Override
        public void onVerificationCompleted(@NonNull PhoneAuthCredential phoneAuthCredential) { //VALIDATING, VERIFYING NUMBER, IF NUMBER  EXIST
        }

        @Override
        public void onVerificationFailed(@NonNull FirebaseException e) {  //IF VALIDATING FAILED //VERIFYING FAILED
            Toast.makeText(getApplicationContext(), "" + e.getMessage(), Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onCodeSent(@NonNull String verificationId, @NonNull PhoneAuthProvider.ForceResendingToken forceResendingToken) {  //SEND CODE
            //THIS METHOD IS USED TO SEND CODE TO THE USER
            //with this code we can verify with the OTP
            super.onCodeSent(verificationId, forceResendingToken);

            verificationCode = verificationId;  //OTP
            resendingToken = forceResendingToken; //to resend OTP

        }

        @Override
        public void onCodeAutoRetrievalTimeOut(@NonNull String s) {
            super.onCodeAutoRetrievalTimeOut(s);
            Log.i("TAG_timeout", "onCodeAutoRetrievalTimeOut: " + s);
        }
    };


    //RESEND TIMER
    private void startResendTimer() {


        //Declare the timer
        Timer timer = new Timer();
        //Set the schedule function and rate
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                runOnUiThread(new Runnable() {
                    @SuppressLint("ResourceAsColor")
                    @Override
                    public void run() {

                        TextView tv = (TextView) findViewById(R.id.resendingCodeTimer);
                        tv.setText("Sending new OTP request in 00" + ":" + String.valueOf(timeOutSeconds));

                        timeOutSeconds--;

                        if (timeOutSeconds < 0) {

                            timeOutSeconds = 60L;
                            timer.cancel();

                            tv.setText(null);
                            tv.setTextColor(R.color.white);

                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    //RESEND ON CLICK   //58 Sec

                                    resendCodeView.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View view) {
                                            verifyPhoneNumber(phoneNumber);
                                            startResendTimer();  //recursion
//            String smsCode = phoneAuthCredential.getSmsCode(); //Gets the auto-retrieved SMS verification code
//
//            if (smsCode != null) {  //If code is non null
//                verifyCode(smsCode);
//                pinView.setText(smsCode);
//            }
                                        }
                                    });

                                }
                            });

                        }
                    }
                });
            }
        }, 0, 1000);
    }

    private void verifyPhoneNumber(String phoneNumber) {
        PhoneAuthProvider.verifyPhoneNumber(PhoneAuthOptions.newBuilder(firebaseAuth)
                .setPhoneNumber(phoneNumber)
                .setTimeout(timeOutSeconds, TimeUnit.SECONDS)
                .setActivity(this)
                .setCallbacks(mCallbacks)
                .setForceResendingToken(resendingToken)
                .build());
    }


    private void verifyCode(String sms) {

        PhoneAuthCredential credential = PhoneAuthProvider.getCredential(verificationCode, sms);  //verifies token  //OTP sent to user and Token generated

        firebaseAuth.signInWithCredential(credential).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
            @Override
            public void onSuccess(AuthResult authResult) {
                Toast.makeText(getApplicationContext(), "User Created", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(UserVerifyingActivity.this, UserInfoActivity.class);
                intent.putExtra("phone", phoneNumber);
                startActivity(intent);
                finish();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(getApplicationContext(), "invalid" + e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

    }
}