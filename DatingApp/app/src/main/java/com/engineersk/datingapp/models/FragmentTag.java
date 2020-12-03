package com.engineersk.datingapp.models;

import androidx.fragment.app.Fragment;

public class FragmentTag {
    private Fragment mFragment;
    private String mTag;

    public FragmentTag(Fragment fragment, String tag) {
        mFragment = fragment;
        mTag = tag;
    }

    public Fragment getFragment() {
        return mFragment;
    }

    public void setFragment(Fragment fragment) {
        mFragment = fragment;
    }

    public String getTag() {
        return mTag;
    }

    public void setTag(String tag) {
        mTag = tag;
    }
}
