package com.example.firebasefirestoredatabase.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.firebasefirestoredatabase.AndroidUtil.AndroidUtils;
import com.example.firebasefirestoredatabase.R;
import com.example.firebasefirestoredatabase.firebaseutils.FirebaseUtil;
import com.example.firebasefirestoredatabase.model.ChatRoomModel;
import com.example.firebasefirestoredatabase.model.UserModelData;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.Timestamp;
import com.google.firebase.firestore.DocumentSnapshot;

import java.sql.Array;
import java.util.Arrays;

public class ChatActivity extends AppCompatActivity {
    TextView username;
    EditText messageInput;
    ImageView backIconImageView, sendMessage;
    UserModelData userModelData;
    String chatroomId;
    ChatRoomModel chatRoomModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

        //get UserModel
        userModelData = AndroidUtils.getUserModelDataFromIntent(getIntent());
        chatroomId = FirebaseUtil.getChatroomId(FirebaseUtil.currentUserId(), userModelData.getUserId());

        username = findViewById(R.id.userNameChatActivity);
        messageInput = findViewById(R.id.messageInputEdittext);
        backIconImageView = findViewById(R.id.backIconChatActivity);
        sendMessage = findViewById(R.id.sendMessageImageView);

        backIconImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        username.setText(userModelData.getUsername());

        getorCreateChatroomModel();
    }

    private void getorCreateChatroomModel() {
        FirebaseUtil.getChatroomReference(chatroomId).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    chatRoomModel = task.getResult().toObject(ChatRoomModel.class);
                    if (chatRoomModel == null) {
                        //first time chat
                        chatRoomModel = new ChatRoomModel(
                                chatroomId,
                                Arrays.asList(FirebaseUtil.currentUserId(), userModelData.getUserId()),
                                Timestamp.now(),
                                ""
                        );
                        FirebaseUtil.getChatroomReference(chatroomId).set(chatRoomModel);

                    }
                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {

            }
        });
    }
}