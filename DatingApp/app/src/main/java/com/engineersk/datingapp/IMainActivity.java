package com.engineersk.datingapp;

import com.engineersk.datingapp.models.Message;
import com.engineersk.datingapp.models.User;

public interface IMainActivity {
    void inflateViewProfileFragment(User user);
    void onMessageSelected(Message message);
    void onBackPressed();
}
