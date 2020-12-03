package com.engineersk.datingapp;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.engineersk.datingapp.models.Message;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class ChatsRecyclerViewAdapter extends RecyclerView.Adapter<ChatsRecyclerViewAdapter.ChatsViewHolder> {

    private static final String TAG = "ChatsRecyclerAdapter";

    private final Context mContext;
    private ArrayList<Message> mMessages;
    private IMainActivity mMainInterface;

    public ChatsRecyclerViewAdapter(Context context, ArrayList<Message> messages) {
        mContext = context;
        mMessages = messages;
    }

    @Override
    public void onAttachedToRecyclerView(@NonNull RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
        mMainInterface = (IMainActivity) mContext;
    }

    @NonNull
    @Override
    public ChatsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Log.d(TAG, "onCreateViewHolder: Inflating Chats Recycler Adapter...");
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.chat_messages_list_item, parent, false);
        return new ChatsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ChatsViewHolder holder, int position) {
        Log.d(TAG, "onBindViewHolder: Binding data to chats list items...");
        RequestOptions requestOptions = new RequestOptions()
                .placeholder(R.drawable.ic_launcher_background);

        Glide.with(mContext).load(mMessages.get(position).getUser().getProfile_image())
                .apply(requestOptions).into(holder.getProfileImage());

        holder.getMessageTextView().setText(mMessages.get(position).getMessage().toString());
    }

    @Override
    public int getItemCount() {
        return mMessages.size();
    }

    protected static class ChatsViewHolder extends RecyclerView.ViewHolder {

        private final TextView mMessageTextView;
        private final CircleImageView mProfileImage;

        public ChatsViewHolder(@NonNull View itemView) {
            super(itemView);
            mMessageTextView = itemView.findViewById(R.id.message);
            mProfileImage = itemView.findViewById(R.id.circle_profile_image);
        }

        public TextView getMessageTextView() {
            return mMessageTextView;
        }

        public CircleImageView getProfileImage() {
            return mProfileImage;
        }
    }
}
