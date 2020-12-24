package com.engineersk.datingapp;

import android.app.SearchManager;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.preference.PreferenceManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.engineersk.datingapp.models.User;
import com.engineersk.datingapp.util.PreferenceKeys;
import com.engineersk.datingapp.util.Users;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public class MessagesFragment extends Fragment {

    private static final String TAG = "MessagesFragment";

    //widgets
    private RecyclerView mRecyclerView;
    private MessagesRecyclerViewAdapter mMessagesRecyclerViewAdapter;
    private SearchView mSearchView;

    //vars
    private final ArrayList<User> mUsers = new ArrayList<>();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Log.d(TAG, "onCreateView: Loading messages...");
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_messages, container, false);
        mRecyclerView = view.findViewById(R.id.recycler_view);
        mSearchView = view.findViewById(R.id.search_view);
        getConnections();
        initSearchView();
        return view;
    }

    private void initSearchView() {
        SearchManager searchManager = (SearchManager) getActivity().getSystemService(Context.SEARCH_SERVICE);
        mSearchView.setSearchableInfo(searchManager.getSearchableInfo(getActivity().getComponentName()));
        mSearchView.setMaxWidth(Integer.MAX_VALUE);
        mSearchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                mMessagesRecyclerViewAdapter.getFilter().filter(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String query) {
                mMessagesRecyclerViewAdapter.getFilter().filter(query);
                return false;
            }
        });
    }

    private void getConnections() {
        Log.d(TAG, "getConnections: Loading connections...");
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getActivity());
        Set<String> savedNames = preferences.getStringSet(PreferenceKeys.SAVED_CONNECTIONS,
                new HashSet<>());

        mUsers.clear();

        Users users = new Users();
        for(User user : users.USERS){
            if(savedNames.contains(user.getName()))
                mUsers.add(user);
        }

        if(mMessagesRecyclerViewAdapter == null)
            initRecyclerView();
    }

    private void initRecyclerView() {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        mMessagesRecyclerViewAdapter = new MessagesRecyclerViewAdapter(mUsers, getActivity());
        mRecyclerView.setLayoutManager(linearLayoutManager);
        mRecyclerView.setAdapter(mMessagesRecyclerViewAdapter);
    }

    @Override
    public void onResume() {
        super.onResume();
        mMessagesRecyclerViewAdapter.notifyDataSetChanged();
        getConnections();
    }
}