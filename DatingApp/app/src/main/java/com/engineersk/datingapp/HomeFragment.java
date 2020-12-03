package com.engineersk.datingapp;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.engineersk.datingapp.models.User;
import com.engineersk.datingapp.util.Users;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class HomeFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener {

    private static final String TAG = "HomeFragment";

    //constants
    private static final int NUM_OF_COLUMNS = 2;
    public static final int TOP_POSITION = 0;

    //widgets
    private RecyclerView mRecyclerView;

    //vars
    private final ArrayList<User> mMatches = new ArrayList<>();
    private StaggeredGridLayoutManager mStaggeredGridLayoutManager;
    private MainRecyclerViewAdapter mMainRecyclerViewAdapter;
    private SwipeRefreshLayout mSwipeRefreshLayout;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Log.d(TAG, "onCreateView: started.");
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        mRecyclerView = view.findViewById(R.id.recycler_view);
        mSwipeRefreshLayout = view.findViewById(R.id.swipe_refresh_layout);
        mSwipeRefreshLayout.setOnRefreshListener(this);
        findMatches();
        return view;
    }

    private void findMatches() {
        Users users = new Users();

        mMatches.clear();

        List<User> usersList = Arrays.asList(users.USERS);

        mMatches.addAll(usersList);


        if(mMainRecyclerViewAdapter == null)
            initRecyclerView();
    }

    private void initRecyclerView() {
        mStaggeredGridLayoutManager = new StaggeredGridLayoutManager(NUM_OF_COLUMNS,
                LinearLayoutManager.VERTICAL);
        mRecyclerView.setLayoutManager(mStaggeredGridLayoutManager);
        mMainRecyclerViewAdapter = new MainRecyclerViewAdapter(getActivity(), mMatches);
        mRecyclerView.setAdapter(mMainRecyclerViewAdapter);
    }

    public void scrollToTop(){
        mRecyclerView.smoothScrollToPosition(TOP_POSITION);
    }

    @Override
    public void onRefresh() {
        Log.d(TAG, "onRefresh: Finding matches!!!");
        findMatches();
        onItemsLoadComplete();
    }

    private void onItemsLoadComplete(){
        Log.d(TAG, "onItemsLoadComplete: items load complete...");
        mMainRecyclerViewAdapter.notifyDataSetChanged();
        mSwipeRefreshLayout.setRefreshing(false);
    }
}