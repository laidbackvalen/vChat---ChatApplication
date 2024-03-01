package com.example.firebasefirestoredatabase.firebaseutils;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

public class FirebaseUtil {
    public static String currentUserId(){
        return FirebaseAuth.getInstance().getUid();
    }
    public static DocumentReference currentUserDetails(){
        return FirebaseFirestore.getInstance().collection("users").document(currentUserId());
    }

    public static boolean isLoggedIn(){
        if(currentUserId() != null) {
            return true;
        }
        return false;
    }

    public  static CollectionReference allUserCollectionReference(){
        return FirebaseFirestore.getInstance().collection("users");
    }
    public static DocumentReference getChatroomReference(String chatroomId){
        return FirebaseFirestore.getInstance().collection("chatrooms").document(chatroomId);
    }
    public static  String getChatroomId(String userId, String userId2){
        if(userId.hashCode()<userId2.hashCode()){
            return userId+"_"+userId2;
        }
        else {
            return userId+"_"+userId2;
        }
    }
}
