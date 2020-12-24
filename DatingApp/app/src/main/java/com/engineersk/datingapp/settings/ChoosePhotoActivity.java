package com.engineersk.datingapp.settings;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;

import com.engineersk.datingapp.R;
import com.google.android.material.tabs.TabLayout;

import java.util.Objects;

public class ChoosePhotoActivity extends AppCompatActivity {

    private static final String TAG = "ChoosePhotoActivity";

    //constants
    private static final int GALLERY_FRAGMENT = 0;
    private static final int PHOTO_FRAGMENT = 1;

    //fragments
    private GalleryFragment mGalleryFragment;
    private PhotoFragment mPhotoFragment;

    //vars
    private ViewPager mViewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_photo);
        mViewPager = findViewById(R.id.view_pager_container);
        setupViewPager();
    }

    private void setupViewPager() {
        MyPagerAdapter myPagerAdapter = new MyPagerAdapter(getSupportFragmentManager());

        mGalleryFragment = new GalleryFragment();
        mPhotoFragment = new PhotoFragment();

        myPagerAdapter.addFragment(mGalleryFragment);
        myPagerAdapter.addFragment(mPhotoFragment);

        mViewPager.setAdapter(myPagerAdapter);
        TabLayout tabLayout =  findViewById(R.id.tabs_bottom);

        tabLayout.setupWithViewPager(mViewPager);

        Objects.requireNonNull(tabLayout.getTabAt(GALLERY_FRAGMENT))
                .setText(getString(R.string.tag_fragment_gallery));
        Objects.requireNonNull(tabLayout.getTabAt(PHOTO_FRAGMENT))
                .setText(getString(R.string.tag_fragment_photo));
    }
}