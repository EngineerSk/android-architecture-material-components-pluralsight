package com.engineersk.datingapp;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.preference.PreferenceManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.prefill.PreFillType;
import com.engineersk.datingapp.models.User;
import com.engineersk.datingapp.util.PreferenceKeys;
import com.engineersk.datingapp.util.Resources;
import com.like.LikeButton;
import com.like.OnLikeListener;

import java.util.HashSet;
import java.util.Set;

import de.hdodenhof.circleimageview.CircleImageView;

public class ViewProfileFragment extends Fragment implements OnLikeListener, View.OnClickListener {

    private static final String TAG = "ViewProfileFragment";

    //widgets
    private ImageView mBackArrow;
    private TextView mFragmentHeading;
    private CircleImageView mProfileImage;
    private LikeButton mLikeButton;
    private TextView mNameTextView;
    private TextView mGenderValueTextView;
    private TextView mInterestedInTextView;
    private TextView mStatusValueTextView;

    //vars
    private User mUser;
    private IMainActivity mMainActivityInterface;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG, "onCreate: called.");
        Bundle bundle = this.getArguments();
        if(bundle != null){
            mUser = bundle.getParcelable(getString(R.string.intent_user));
            Log.d(TAG, "onCreate: got incoming bundle "+mUser.getName());
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        Log.d(TAG, "onCreateView: started.");

        View view = inflater.inflate(R.layout.fragment_view_profile, container, false);

        mBackArrow = view.findViewById(R.id.image_back_arrow);
        mFragmentHeading = view.findViewById(R.id.fragment_heading);
        mProfileImage = view.findViewById(R.id.circle_image_view);
        mLikeButton = view.findViewById(R.id.like_button);
        mNameTextView = view.findViewById(R.id.name_txt_value);
        mGenderValueTextView = view.findViewById(R.id.gender_txt_value);
        mInterestedInTextView = view.findViewById(R.id.interested_in_txt_value);
        mStatusValueTextView = view.findViewById(R.id.status_txt_value);

        init();
        setBackgroundImage(view);
        checkIfConnected();
        mLikeButton.setOnLikeListener(this);
        mBackArrow.setOnClickListener(this);
        return view;
    }

    private void checkIfConnected() {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getActivity());
        Set<String> savedNames = preferences.getStringSet(PreferenceKeys.SAVED_CONNECTIONS,
                new HashSet<>());
        if(savedNames.contains(mUser.getName()))
            mLikeButton.setLiked(true);
        else
            mLikeButton.setLiked(false);
    }

    private void setBackgroundImage(View view) {
        ImageView backgroundImage = view.findViewById(R.id.background);
        Glide.with(getActivity())
                .load(Resources.BACKGROUND_HEARTS)
                .into(backgroundImage);
    }

    private void init() {
        Log.d(TAG, "init: Initializing ViewProfileFragment...");
        if(mUser != null){
            Glide.with(getActivity())
                    .load(mUser.getProfile_image())
                    .into(mProfileImage);
            mNameTextView.setText(mUser.getName());
            mGenderValueTextView.setText(mUser.getGender());
            mInterestedInTextView.setText(mUser.getInterested_in());
            mStatusValueTextView.setText(mUser.getStatus());
        }
    }

    @Override
    public void liked(LikeButton likeButton) {
        Log.d(TAG, "liked: Liked...");
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getActivity());
        SharedPreferences.Editor editor = preferences.edit();
        Set<String> savedNames = preferences.getStringSet(PreferenceKeys.SAVED_CONNECTIONS,
                new HashSet<>());
        savedNames.add(mUser.getName());
        editor.remove(PreferenceKeys.SAVED_CONNECTIONS);
        editor.apply();
        editor.putStringSet(PreferenceKeys.SAVED_CONNECTIONS, savedNames);
        editor.apply();
    }

    @Override
    public void unLiked(LikeButton likeButton) {
        Log.d(TAG, "unLiked: UnLiked...");
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getActivity());
        SharedPreferences.Editor editor = preferences.edit();
        Set<String> savedNames = preferences.getStringSet(PreferenceKeys.SAVED_CONNECTIONS,
                new HashSet<>());
        savedNames.remove(mUser.getName());
        editor.remove(PreferenceKeys.SAVED_CONNECTIONS);
        editor.apply();
        editor.putStringSet(PreferenceKeys.SAVED_CONNECTIONS,savedNames);
        editor.apply();
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        mMainActivityInterface = (IMainActivity) getActivity();
    }

    @Override
    public void onClick(View view) {
        if(view.getId() == R.id.image_back_arrow)
            mMainActivityInterface.onBackPressed();
    }
}