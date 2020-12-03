package com.engineersk.datingapp.settings;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.core.content.FileProvider;
import androidx.fragment.app.Fragment;

import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.engineersk.datingapp.BuildConfig;
import com.engineersk.datingapp.MainActivity;
import com.engineersk.datingapp.R;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class PhotoFragment extends Fragment {

    private static final String TAG = "PhotoFragment";

    //constant
    private static final int CAMERA_REQUEST_CODE = 5090;
    private static final int NEW_PHOTO_REQUEST = 3567;

    //widgets

    //vars
    private Uri mOutputUri;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        Log.d(TAG, "onCreateView: started.");
        View view = inflater.inflate(R.layout.fragment_photo, container, false);
        //open the camera to take a picture
        Button btnLaunchCamera = (Button) view.findViewById(R.id.btnLaunchCamera);
        btnLaunchCamera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "onClick: attempting to launch camera.");
                openCamera();
            }
        });


        return view;
    }

    private void openCamera() {
        Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        mOutputUri = FileProvider.getUriForFile(getActivity(),
                BuildConfig.APPLICATION_ID+".provider",
               getOutputMediaFile());
//        mOutputUri = Uri.fromFile(getOutputMediaFile());
        cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, mOutputUri);
        startActivityForResult(cameraIntent, CAMERA_REQUEST_CODE);
    }

    private static File getOutputMediaFile() {
        File mediaStorageDir = new File(Environment.getExternalStoragePublicDirectory(
                Environment.DIRECTORY_PICTURES), "DatingApp");

        if (!mediaStorageDir.exists()){
            if (!mediaStorageDir.mkdirs()){
                return null;
            }
        }

        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss", Locale.UK).format(new Date());
        return new File(mediaStorageDir.getPath() + File.separator +
                "IMG_"+ timeStamp + ".jpg");
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == CAMERA_REQUEST_CODE){
            if(data != null){
                Log.d(TAG, "onActivityResult: done taking a photo.");
                Log.d(TAG, "onActivityResult: output uri: "+mOutputUri);
                getActivity().setResult(NEW_PHOTO_REQUEST,
                        data.putExtra(getString(R.string.intent_new_camera_photo),
                                mOutputUri.toString()));
                getActivity().finish();
            }
        }
    }
}