package com.example.firebasefirestoredatabase.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.firebasefirestoredatabase.AndroidUtil.AndroidUtils;
import com.example.firebasefirestoredatabase.activities.ChatActivity;
import com.example.firebasefirestoredatabase.R;
import com.example.firebasefirestoredatabase.model.UserModelData;
import com.example.firebasefirestoredatabase.firebaseutils.FirebaseUtil;
import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;

public class SearchUserRecyclerAdapter extends FirestoreRecyclerAdapter<UserModelData, SearchUserRecyclerAdapter.UserModelViewHolder> {
    Context context;

    public SearchUserRecyclerAdapter(@NonNull FirestoreRecyclerOptions<UserModelData> options, Context context) {
        super(options);
        this.context = context;
    }

    class UserModelViewHolder extends RecyclerView.ViewHolder {
        TextView usernameText, phoneText;
        ImageView profilepic;

        public UserModelViewHolder(@NonNull View itemView) {
            super(itemView);
            usernameText = itemView.findViewById(R.id.userNameTextView);
            phoneText = itemView.findViewById(R.id.userContactTextView);
            profilepic = itemView.findViewById(R.id.profile_pic_image_view);
        }
    }

    @Override
    protected void onBindViewHolder(@NonNull UserModelViewHolder holder, int position, @NonNull UserModelData model) {
        holder.usernameText.setText(model.getUsername());
        holder.phoneText.setText(model.getPhone());
        if (model.getUserId().equals(FirebaseUtil.currentUserId())) {
            holder.usernameText.setText(model.getUsername() + " (You)");
        }
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Navigate To chat Activity
                Intent intent = new Intent(context, ChatActivity.class);
                AndroidUtils.passUserModelDataAsIntent(intent, model);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent);

            }
        });
    }

    @NonNull
    @Override
    public UserModelViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.search_user_recycler_view_row, parent, false);
        return new UserModelViewHolder(view);
    }
}
