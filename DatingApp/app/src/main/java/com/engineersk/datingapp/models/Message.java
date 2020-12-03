package com.engineersk.datingapp.models;

import android.os.Parcel;
import android.os.Parcelable;

public class Message implements Parcelable {
    private User mUser;
    private String mMessage;

    public Message(User user, String message) {
        mUser = user;
        mMessage = message;
    }

    protected Message(Parcel in) {
        mUser = in.readParcelable(User.class.getClassLoader());
        mMessage = in.readString();
    }

    public User getUser() {
        return mUser;
    }

    public void setUser(User user) {
        mUser = user;
    }

    public String getMessage() {
        return mMessage;
    }

    public void setMessage(String message) {
        mMessage = message;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeParcelable(mUser, flags);
        dest.writeString(mMessage);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<Message> CREATOR = new Creator<Message>() {
        @Override
        public Message createFromParcel(Parcel in) {
            return new Message(in);
        }

        @Override
        public Message[] newArray(int size) {
            return new Message[size];
        }
    };

    @Override
    public String toString() {
        return "Message{" +
                "mUser=" + mUser +
                ", mMessage=" + mMessage +
                '}';
    }
}
