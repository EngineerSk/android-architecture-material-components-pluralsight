package com.engineersk.datingapp;

import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

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

public class SavedConnectionsFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener{
    //constants
    private static final String TAG = "ConnectionsFragment";
    private static final int NUM_COLUMNS = 2;

    //widgets
    private RecyclerView mRecyclerView;
    private MainRecyclerViewAdapter mMainRecyclerViewAdapter;
    private SwipeRefreshLayout mSwipeRefreshLayout;

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
        mSwipeRefreshLayout = view.findViewById(R.id.swipe_refresh_layout);
        mSwipeRefreshLayout.setOnRefreshListener(this);
        getConnections();
        return view;
    }

    private void getConnections(){
        Log.d(TAG, "getConnections: Loading saved connections...");
        final SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getActivity());
        Set<String> savedNames = preferences.getStringSet(PreferenceKeys.SAVED_CONNECTIONS,
                new HashSet<>());
        Users users = new Users();

        if(savedNames != null) {
            for (User user : users.USERS) {
                if (savedNames.contains(user.getName())) {
                    if (!mUsers.contains(user))
                        mUsers.add(user);
                } else
                    mUsers.remove(user);
            }
        }else
            mUsers.clear();

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

    @Override
    public void onResume() {
        super.onResume();
        onRefresh();
    }

    @Override
    public void onRefresh() {
        getConnections();
        onItemsLoadComplete();
    }

    private void onItemsLoadComplete() {
        mMainRecyclerViewAdapter.notifyDataSetChanged();
        if(mSwipeRefreshLayout.isRefreshing())
            mSwipeRefreshLayout.setRefreshing(false);
    }
}