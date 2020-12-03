package com.engineersk.datingapp;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.engineersk.datingapp.models.Message;
import com.engineersk.datingapp.models.User;
import com.engineersk.datingapp.util.Messages;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class MessagesRecyclerViewAdapter extends
        RecyclerView.Adapter<MessagesRecyclerViewAdapter.MessageViewHolder> implements Filterable {

    private static final String TAG = "MessagesRecyclerAdapter";

    private final ArrayList<User> mUsers;
    private ArrayList<User> mFilteredUsers;
    private final Context mContext;
    private IMainActivity mMainInterface;


    public MessagesRecyclerViewAdapter(ArrayList<User> users, Context context) {
        mUsers = users;
        mFilteredUsers = users;
        mContext = context;
    }

    @NonNull
    @Override
    public MessageViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view  = inflater.inflate(R.layout.layout_messages_list_item, parent, false);
        return new MessageViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MessageViewHolder holder, int position) {
        RequestOptions requestOptions = new RequestOptions()
                .placeholder(R.drawable.ic_launcher_background);

        final User user = mFilteredUsers.get(position);
        Glide.with(mContext)
                .load(user.getProfile_image())
                .apply(requestOptions)
                .into(holder.getCircleImageView());

        holder.getNameTextView().setText(user.getName());
        holder.getMessageTextView().setText(Messages.MESSAGES[position]);
        holder.getReplyTextView().setOnClickListener(view -> {

        });

        holder.getParentConstraintLayout().setOnClickListener(
                view -> {
                    Log.d(TAG, "onClick: User clicked...");
                    mMainInterface.onMessageSelected(
                            new Message(user, Messages.MESSAGES[position]));
                }
        );
    }

    @Override
    public int getItemCount() {
        return mFilteredUsers.size();
    }

    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence charSequence) {
                String charString = charSequence.toString();
                if(charString.isEmpty())
                    mFilteredUsers = mUsers;
                else{
                    ArrayList<User> filteredList = new ArrayList<>();
                    for(User row: mUsers){
                        if(row.getName().toLowerCase().contains(charString.toLowerCase()))
                            filteredList.add(row);
                    }
                    mFilteredUsers = filteredList;
                }
                FilterResults filterResults = new FilterResults();
                filterResults.values = mFilteredUsers;
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
                mFilteredUsers = (ArrayList<User>) filterResults.values;
                notifyDataSetChanged();
            }
        };
    }

    @Override
    public void onAttachedToRecyclerView(@NonNull RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
        mMainInterface = (IMainActivity) mContext;
    }

    protected static final class MessageViewHolder extends RecyclerView.ViewHolder {

        private final CircleImageView mCircleImageView;
        private final TextView mNameTextView;
        private final TextView mMessageTextView;
        private final TextView mReplyTextView;
        private final ConstraintLayout mParentConstraintLayout;

        public MessageViewHolder(@NonNull View itemView) {
            super(itemView);
            mCircleImageView = itemView.findViewById(R.id.circle_image_view);
            mNameTextView = itemView.findViewById(R.id.name);
            mMessageTextView = itemView.findViewById(R.id.message);
            mReplyTextView = itemView.findViewById(R.id.reply_txt);
            mParentConstraintLayout = itemView.findViewById(R.id.parent_view);
        }

        public CircleImageView getCircleImageView() {
            return mCircleImageView;
        }

        public TextView getNameTextView() {
            return mNameTextView;
        }

        public TextView getMessageTextView() {
            return mMessageTextView;
        }

        public TextView getReplyTextView() {
            return mReplyTextView;
        }

        public ConstraintLayout getParentConstraintLayout() {
            return mParentConstraintLayout;
        }

    }
}
