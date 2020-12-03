package com.engineersk.datingapp;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.constraintlayout.solver.state.State;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.engineersk.datingapp.models.Message;
import com.engineersk.datingapp.models.User;
import com.engineersk.datingapp.util.Resources;
import com.google.android.material.textfield.TextInputEditText;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class ChatFragment extends Fragment implements View.OnClickListener {

    private static final String TAG = "ChatFragment";

    //widgets
    private TextView mFragmentHeading;
    private ConstraintLayout mBackArrowConstraintLayout;
    private ConstraintLayout mProfileConstraintLayout;
    private CircleImageView mCircleProfileImageView;
    private TextInputEditText mNewMessageEditText;
    private TextView mSendMessageTextView;


    //vars
    private Message mMessage;
    private ArrayList<Message> mMessages = new ArrayList<>();
    private User mCurrentUser = new User();
    private RecyclerView mRecyclerView;
    private ChatsRecyclerViewAdapter mChatsRecyclerViewAdapter;
    private IMainActivity mMainInterface;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = this.getArguments();
        if(bundle != null){
            mMessage = bundle.getParcelable(getString(R.string.intent_message));
            mMessages.add(mMessage);
            Log.d(TAG, "onCreate: got incoming bundle "+mMessage.getUser().getName());
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_chat, container, false);
        mFragmentHeading = view.findViewById(R.id.fragment_heading);
        mBackArrowConstraintLayout = view.findViewById(R.id.back_arrow_layout);
        mProfileConstraintLayout = view.findViewById(R.id.profile_constraint_layout);
        mCircleProfileImageView = view.findViewById(R.id.circle_profile_image);
        mNewMessageEditText = view.findViewById(R.id.input_message);
        mSendMessageTextView = view.findViewById(R.id.post_message);
        mRecyclerView = view.findViewById(R.id.recycler_view);

        mSendMessageTextView.setOnClickListener(this);
        mProfileConstraintLayout.setOnClickListener(this);

        initToolbar();
        initRecyclerView();
        setBackgroundImage(view);

        return view;
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        mMainInterface = (IMainActivity)getActivity();
    }

    private void initToolbar() {
        Log.d(TAG, "initToolbar: Initializing toolbar...");
        mBackArrowConstraintLayout.setOnClickListener(this);
        mFragmentHeading.setText(mMessage.getUser().getName());
        Glide.with(getActivity())
                .load(mMessage.getUser().getProfile_image())
                .into(mCircleProfileImageView);
    }

    private void setBackgroundImage(View view) {
        Log.d(TAG, "setBackgroundImage: Setting background image...");
        ImageView backgroundImage = view.findViewById(R.id.background);
        Glide.with(getActivity()).load(Resources.BACKGROUND_HEARTS).into(backgroundImage);
    }

    private void initRecyclerView() {
        Log.d(TAG, "initRecyclerView: Initializing Recycler View...");
        LinearLayoutManager linearLayoutManager= new LinearLayoutManager(this.getActivity());
        mChatsRecyclerViewAdapter = new ChatsRecyclerViewAdapter(this.getActivity(), mMessages);
        mRecyclerView.setLayoutManager(linearLayoutManager);
        mRecyclerView.setAdapter(mChatsRecyclerViewAdapter);
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        if(id == R.id.back_arrow_layout){
            Log.d(TAG, "onClick: Navigating Backwards...");
            mMainInterface.onBackPressed();
        }

        else if(id == R.id.profile_constraint_layout){
            Log.d(TAG, "onClick: User profile clicked...");
            mMainInterface.inflateViewProfileFragment(mMessage.getUser());
        }

        else if(id == R.id.post_message){
            Log.d(TAG, "onClick: Posting new message...");
            if(!mNewMessageEditText.getText().toString().trim().isEmpty()) {
                mMessages.add(new Message(mCurrentUser, mNewMessageEditText.getText().toString()));
                mChatsRecyclerViewAdapter.notifyDataSetChanged();
                mNewMessageEditText.setText("");
                mRecyclerView.smoothScrollToPosition(mMessages.size() - 1);
            }
        }
    }
}