package com.engineersk.datingapp;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.engineersk.datingapp.models.User;
import com.google.android.material.circularreveal.cardview.CircularRevealCardView;

import java.util.ArrayList;
import java.util.List;

public class MainRecyclerViewAdapter extends
        RecyclerView.Adapter<MainRecyclerViewAdapter.ViewHolder> {

    private static final String TAG = "MainRecyclerViewAdapter";

    private final Context mContext;
    private final ArrayList<User> mUsers;
    private IMainActivity mInterface;

    public MainRecyclerViewAdapter(Context context, ArrayList<User> users) {
        mContext = context;
        mUsers = users;
    }

    @Override
    public void onAttachedToRecyclerView(@NonNull RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
        mInterface = (IMainActivity)mContext;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_main_feed,
                parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Log.d(TAG, "onBindViewHolder: called.");
        RequestOptions requestOptions = new RequestOptions()
                .placeholder(R.drawable.ic_launcher_background);

        User user = mUsers.get(position);
        Glide.with(mContext).load(user.getProfile_image())
                .apply(requestOptions).into(holder.getImage());
        
        holder.getName().setText(user.getName());
        holder.getStatus().setText(user.getStatus());
        holder.getInterested_in().setText(user.getInterested_in());
        
        holder.getCardView().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d(TAG, "onClick: clicked on "+user.getName());
                mInterface.inflateViewProfileFragment(user);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mUsers.size();
    }

    protected static class ViewHolder extends RecyclerView.ViewHolder{

        private final ImageView mImage;
        private final TextView mName;
        private final TextView mStatus;
        private final TextView mInterested_in;
        private final CircularRevealCardView mCardView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            mImage = itemView.findViewById(R.id.image_view);
            mName = itemView.findViewById(R.id.name_txt_value);
            mStatus = itemView.findViewById(R.id.status_txt_value);
            mInterested_in = itemView.findViewById(R.id.interested_in_txt_value);
            mCardView = itemView.findViewById(R.id.card_view);
        }

        public ImageView getImage() {
            return mImage;
        }

        public TextView getName() {
            return mName;
        }

        public TextView getStatus() {
            return mStatus;
        }

        public TextView getInterested_in() {
            return mInterested_in;
        }

        public CircularRevealCardView getCardView() {
            return mCardView;
        }
    }
}
