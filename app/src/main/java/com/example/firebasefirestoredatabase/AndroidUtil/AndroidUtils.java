package com.example.firebasefirestoredatabase.AndroidUtil;

import android.content.Intent;

import com.example.firebasefirestoredatabase.model.UserModelData;

public class AndroidUtils {
    public static void passUserModelDataAsIntent(Intent intent, UserModelData model) {
    intent.putExtra("username", model.getUsername());
    intent.putExtra("phone", model.getPhone());
    intent.putExtra("userId", model.getUserId());
    }
    public static UserModelData getUserModelDataFromIntent(Intent intent){
        UserModelData userModelData = new UserModelData();
        userModelData.setUsername(intent.getStringExtra("username"));
        userModelData.setPhone(intent.getStringExtra("phone"));
        userModelData.setUserId(intent.getStringExtra("userId"));
        return userModelData;
    }
}
