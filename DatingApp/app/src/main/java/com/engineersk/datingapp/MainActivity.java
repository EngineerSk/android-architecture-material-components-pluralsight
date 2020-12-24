package com.engineersk.datingapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.FragmentTransaction;

import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.engineersk.datingapp.models.FragmentTag;
import com.engineersk.datingapp.models.Message;
import com.engineersk.datingapp.models.User;
import com.engineersk.datingapp.settings.SettingsFragment;
import com.engineersk.datingapp.util.PreferenceKeys;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.android.material.navigation.NavigationView;
import com.ittianyu.bottomnavigationviewex.BottomNavigationViewEx;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements IMainActivity,
        BottomNavigationViewEx.OnNavigationItemSelectedListener,
        NavigationView.OnNavigationItemSelectedListener {

    //constants
    private static final String TAG = "MainActivity";
    public static final int HEADER_VIEW_INDEX = 0;
    public static final int HOME_FRAGMENT = 0;
    public static final int CONNECTIONS_FRAGMENT = 1;
    public static final int MESSAGES_FRAGMENT = 2;

    //widgets
    private BottomNavigationViewEx mBottomNavigationViewEx;
    private ImageView mHeaderImage;
    private DrawerLayout mDrawerLayout;

    //fragments
    private ChatFragment mChatFragment;
    private ViewProfileFragment mViewProfileFragment;
    private MessagesFragment mMessagesFragment;
    private SavedConnectionsFragment mSavedConnectionsFragment;
    private HomeFragment mHomeFragment;
    private AgreementFragment mAgreementFragment;
    private SettingsFragment mSettingsFragment;

    //vars
    private ArrayList<String> mFragmentTags = new ArrayList<>();
    private ArrayList<FragmentTag> mFragments = new ArrayList<>();
    private int mExitCount = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mBottomNavigationViewEx = findViewById(R.id.bottom_nav_view);
        mBottomNavigationViewEx.setOnNavigationItemSelectedListener(this);
        NavigationView navigationView = findViewById(R.id.navigation_view);
        View headerView = navigationView.getHeaderView(HEADER_VIEW_INDEX);
        mHeaderImage = headerView.findViewById(R.id.header_image);
        mDrawerLayout = findViewById(R.id.drawer_layout);
        setNavigationViewListener();
        setHeaderImage();
        isFirstLogin();
        initBottomNavigationView();
        init();
    }

    private void setHeaderImage() {
        Log.d(TAG, "setHeaderImage: setting header image for navigation drawer...");
        Glide.with(this)
                .load(R.drawable.couple)
                .into(mHeaderImage);
    }

    private void hideBottomNavigation(){
        if(mBottomNavigationViewEx != null)
            mBottomNavigationViewEx.setVisibility(View.GONE);
    }

    private void showBottomNavigation(){
        if(mBottomNavigationViewEx != null)
            mBottomNavigationViewEx.setVisibility(View.VISIBLE);
    }

    private void initBottomNavigationView(){
        Log.d(TAG, "initBottomNavigationView: Initializing the bottom navigation view");
        mBottomNavigationViewEx.enableAnimation(false);
    }

    private void init(){
        if(mHomeFragment == null) {
            mHomeFragment = new HomeFragment();
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            transaction.add(R.id.main_content_frame, mHomeFragment,
                    getString(R.string.tag_fragment_home));
            transaction.commit();
            mFragmentTags.add(getString(R.string.tag_fragment_home));
            mFragments.add(new FragmentTag(mHomeFragment,
                    getString(R.string.tag_fragment_home)));
        }else{
            mFragmentTags.remove(getString(R.string.tag_fragment_home));
            mFragmentTags.add(getString(R.string.tag_fragment_home));
        }
        setFragmentVisibilities(getString(R.string.tag_fragment_home));
    }

    private void setNavigationViewListener(){
        Log.d(TAG, "setNavigationViewListener: Initializing navigation drawer listener...");
        NavigationView navigationView = findViewById(R.id.navigation_view);
        navigationView.setNavigationItemSelectedListener(this);
    }

    private void isFirstLogin(){
        Log.d(TAG, "isFirstLogin: Checking if this is the first login.");
        final SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
        boolean isFirstLogin = preferences.getBoolean(PreferenceKeys.FIRST_TIME_LOGIN, true);

        if(isFirstLogin){
            Log.d(TAG, "isFirstLogin: Launching alert Dialog");

            MaterialAlertDialogBuilder alertDialogBuilder = new MaterialAlertDialogBuilder(this);
            alertDialogBuilder.setMessage(getString(R.string.first_time_user_message))
                    .setTitle(" ")
                    .setIcon(R.drawable.tabian_dating)
                    .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            Log.d(TAG, "onClick: closing dialog.");
                            SharedPreferences.Editor editor = preferences.edit();
                            editor.putBoolean(PreferenceKeys.FIRST_TIME_LOGIN, false);
                            editor.apply();
                            dialogInterface.dismiss();
                        }
                    }).show();
        }
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        Log.d(TAG, "onNavigationItemSelected: Bottom navigation item selected");
        int itemId = item.getItemId();
        if(itemId == R.id.nav_home){
            Log.d(TAG, "onNavigationItemSelected: Home Navigation menu item selected...");
            mFragmentTags.clear();
            mFragmentTags = new ArrayList<>();
            init();
        }
        else if(itemId == R.id.nav_settings){
            Log.d(TAG, "onNavigationItemSelected: Settings Navigation menu item selected...");
            if(mSettingsFragment == null) {
                mSettingsFragment = new SettingsFragment();
                FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                transaction.add(R.id.main_content_frame, mSettingsFragment,
                        getString(R.string.tag_fragment_settings));

                transaction.commit();
                mFragmentTags.add(getString(R.string.tag_fragment_settings));
                mFragments.add(new FragmentTag(mSettingsFragment,
                        getString(R.string.tag_fragment_settings)));
            }else{
                mFragmentTags.remove(getString(R.string.tag_fragment_settings));
                mFragmentTags.add(getString(R.string.tag_fragment_settings));
            }
            setFragmentVisibilities(getString(R.string.tag_fragment_settings));
        }
        else if(itemId == R.id.nav_agreement){
            Log.d(TAG, "onNavigationItemSelected: Agreement Navigation menu item selected...");
            if(mAgreementFragment == null) {
                mAgreementFragment = new AgreementFragment();
                FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                transaction.add(R.id.main_content_frame, mAgreementFragment,
                        getString(R.string.tag_fragment_agreement));
                transaction.commit();
                mFragmentTags.add(getString(R.string.tag_fragment_agreement));
                mFragments.add(new FragmentTag(mAgreementFragment,
                        getString(R.string.tag_fragment_agreement)));
            }else{
                mFragmentTags.remove(getString(R.string.tag_fragment_agreement));
                mFragmentTags.add(getString(R.string.tag_fragment_agreement));
            }
            setFragmentVisibilities(getString(R.string.tag_fragment_agreement));
        }
        else if(itemId == R.id.bottom_nav_home){
            Log.d(TAG, "onNavigationItemSelected: Home fragment");
            if(mHomeFragment == null) {
                mHomeFragment = new HomeFragment();
                FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                transaction.add(R.id.main_content_frame, mHomeFragment,
                        getString(R.string.tag_fragment_home));
                mFragmentTags.add(getString(R.string.tag_fragment_home));
                mFragments.add(new FragmentTag(mHomeFragment,
                        getString(R.string.tag_fragment_home)));
            }else{
                mFragmentTags.remove(getString(R.string.tag_fragment_home));
                mFragmentTags.add(getString(R.string.tag_fragment_home));
            }
            setFragmentVisibilities(getString(R.string.tag_fragment_home));
        }
        else if(itemId == R.id.bottom_nav_connections){
            Log.d(TAG, "onNavigationItemSelected: Connections Fragment");
            if(mSavedConnectionsFragment == null) {
                mSavedConnectionsFragment = new SavedConnectionsFragment();
                FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                transaction.add(R.id.main_content_frame, mSavedConnectionsFragment,
                        getString(R.string.tag_fragment_saved_connections));
                mFragmentTags.add(getString(R.string.tag_fragment_saved_connections));
                mFragments.add(new FragmentTag(mSavedConnectionsFragment,
                        getString(R.string.tag_fragment_saved_connections)));
                transaction.commit();
            }else{
                mSavedConnectionsFragment.onRefresh();
                mFragmentTags.remove(getString(R.string.tag_fragment_saved_connections));
                mFragmentTags.add(getString(R.string.tag_fragment_saved_connections));
            }

            setFragmentVisibilities(getString(R.string.tag_fragment_saved_connections));
        }
        else if(itemId == R.id.bottom_nav_messages){
            Log.d(TAG, "onNavigationItemSelected: Messages Fragment");
            if(mMessagesFragment == null) {
                mMessagesFragment = new MessagesFragment();
                FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                transaction.add(R.id.main_content_frame, mMessagesFragment,
                        getString(R.string.tag_fragment_messages));
                transaction.commit();
                mFragmentTags.add(getString(R.string.tag_fragment_messages));
                mFragments.add(new FragmentTag(mMessagesFragment,
                        getString(R.string.tag_fragment_messages)));

            }else{
                mFragmentTags.remove(getString(R.string.tag_fragment_messages));
                mFragmentTags.add(getString(R.string.tag_fragment_messages));
            }
            setFragmentVisibilities(getString(R.string.tag_fragment_messages));
        }
        else
            return false;

        mDrawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }

    private void setFragmentVisibilities(String tagName){

        if(tagName.equals(getString(R.string.tag_fragment_home))
                || tagName.equals(getString(R.string.tag_fragment_saved_connections))
                || tagName.equals(getString(R.string.tag_fragment_messages)))
            showBottomNavigation();
        else
            hideBottomNavigation();

        for(int i=0; i<mFragments.size(); ++i){
            if(tagName.equals(mFragments.get(i).getTag())){
                getSupportFragmentManager()
                        .beginTransaction()
                        .show(mFragments.get(i).getFragment())
                        .commit();
            }else{
                getSupportFragmentManager()
                        .beginTransaction()
                        .hide(mFragments.get(i).getFragment())
                        .commit();
            }
        }
        setNavigationIcon(tagName);
    }

    @Override
    public void onBackPressed() {

        int backStackCount = mFragmentTags.size();
        if(backStackCount > 1){
            String topFragmentTag = mFragmentTags.get(backStackCount - 1);
            String newTopFragmentTag = mFragmentTags.get(backStackCount - 2);
            mFragmentTags.remove(topFragmentTag);
            setFragmentVisibilities(newTopFragmentTag);
            mExitCount = 0;
        }else if(backStackCount == 1){
            String topFragmentTag = mFragmentTags.get(backStackCount - 1);
            if(topFragmentTag.equals(getString(R.string.tag_fragment_home)))
                mHomeFragment.scrollToTop();
            mExitCount++;
            Toast.makeText(this, "1 more click to exit", Toast.LENGTH_SHORT).show();
        }
        if(mExitCount >= 2)
            super.onBackPressed();
    }

    private void setNavigationIcon(String tagName){
        Menu menu = mBottomNavigationViewEx.getMenu();
        MenuItem menuItem = null;

        if(tagName.equals(getString(R.string.tag_fragment_home))){
            Log.d(TAG, "setNavigationIcon: Home Icon checked...");
            menuItem = menu.getItem(HOME_FRAGMENT);
        }else if(tagName.equals(getString(R.string.tag_fragment_saved_connections))){
            Log.d(TAG, "setNavigationIcon: Connections Icon checked...");
            menuItem = menu.getItem(CONNECTIONS_FRAGMENT);
        }else if(tagName.equals(getString(R.string.tag_fragment_messages))){
            Log.d(TAG, "setNavigationIcon: Messages Icon checked...");
            menuItem = menu.getItem(MESSAGES_FRAGMENT);
        }

        if(menuItem != null)
            menuItem.setChecked(true);
    }

    @Override
    public void inflateViewProfileFragment(User user) {

        if(mViewProfileFragment != null) {
            getSupportFragmentManager().beginTransaction()
                    .remove(mViewProfileFragment)
                    .commitAllowingStateLoss();
        }

        mViewProfileFragment = new ViewProfileFragment();
        Bundle args = new Bundle();
        args.putParcelable(getString(R.string.intent_user), user);
        mViewProfileFragment.setArguments(args);
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.add(R.id.main_content_frame, mViewProfileFragment,
                getString(R.string.tag_fragment_view_profile));
        transaction.commit();
        mFragmentTags.add(getString(R.string.tag_fragment_view_profile));
        mFragments.add(new FragmentTag(mViewProfileFragment,
                getString(R.string.tag_fragment_view_profile)));
        setFragmentVisibilities(getString(R.string.tag_fragment_view_profile));
    }

    @Override
    public void onMessageSelected(Message message) {

        if(mChatFragment != null){
            getSupportFragmentManager().beginTransaction()
                    .remove(mChatFragment)
                    .commitAllowingStateLoss();
        }

        mChatFragment = new ChatFragment();
        Bundle args = new Bundle();
        args.putParcelable(getString(R.string.intent_message), message);
        mChatFragment.setArguments(args);
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.add(R.id.main_content_frame, mChatFragment,
                getString(R.string.tag_fragment_chats));
        transaction.commit();
        mFragmentTags.add(getString(R.string.tag_fragment_chats));
        mFragments.add(new FragmentTag(mChatFragment, getString(R.string.tag_fragment_chats)));
        setFragmentVisibilities(getString(R.string.tag_fragment_chats));
    }
}