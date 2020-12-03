package com.engineersk.datingapp;

import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import android.preference.PreferenceManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.engineersk.datingapp.models.User;
import com.engineersk.datingapp.util.PreferenceKeys;
import com.engineersk.datingapp.util.Users;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public class SavedConnectionsFragment extends Fragment {
    //constants
    private static final String TAG = "ConnectionsFragment";
    private static final int NUM_COLUMNS = 2;

    //widgets
    private RecyclerView mRecyclerView;
    private MainRecyclerViewAdapter mMainRecyclerViewAdapter;

    //vars
    private final ArrayList<User> mUsers = new ArrayList<>();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Log.d(TAG, "onCreateView: Started.");
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_saved_connections, container, false);
        mRecyclerView = view.findViewById(R.id.recycler_view);
        getConnections();
        return view;
    }

    private void getConnections(){
        Log.d(TAG, "getConnections: Loading saved connections...");
        final SharedPreferences PREFERENCES = PreferenceManager.getDefaultSharedPreferences(getActivity());
        Set<String> savedNames = PREFERENCES.getStringSet(PreferenceKeys.SAVED_CONNECTIONS,
                new HashSet<>());
        Users users = new Users();

        mUsers.clear();

        for(User user: users.USERS) {
            if (savedNames.contains(user.getName()))
                mUsers.add(user);
        }

        if(mMainRecyclerViewAdapter == null)
            initRecyclerViewAdapter();

    }

    private void initRecyclerViewAdapter() {
        Log.d(TAG, "initRecyclerViewAdapter: Initializing recyclerview adapter...");
        StaggeredGridLayoutManager staggeredGridLayoutManager =
                new StaggeredGridLayoutManager(NUM_COLUMNS, LinearLayout.VERTICAL);
        mMainRecyclerViewAdapter = new MainRecyclerViewAdapter(getActivity(), mUsers);
        mRecyclerView.setLayoutManager(staggeredGridLayoutManager);
        mRecyclerView.setAdapter(mMainRecyclerViewAdapter);
    }
}