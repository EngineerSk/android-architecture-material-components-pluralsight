package com.engineersk.datingapp.util;

import java.io.File;
import java.util.ArrayList;


public class FileSearch {

    private static final String TAG = "FileSearch";

    public static ArrayList<String> getDirectoryPaths(String directory){
        ArrayList<String> pathArray = new ArrayList<>();
        File file;
        File[] listFiles = null;
        try{
            file = new File(directory);
            listFiles = file.listFiles();
        }catch (Exception e){
            e.printStackTrace();
        }
        try{
            for (File listFile : listFiles) {
                if (listFile.isDirectory()) {
                    pathArray.add(listFile.getAbsolutePath());
                }
            }
        }catch (NullPointerException e){
            e.printStackTrace();
        }
        return pathArray;
    }

    public static ArrayList<String> getFilePaths(String directory){
        ArrayList<String> pathArray = new ArrayList<>();
        File file = null;
        File[] listfiles = null;
        try{
            file = new File(directory);
            listfiles = file.listFiles();
        }catch (Exception e){
            e.printStackTrace();
        }
        try{
            for(int i = 0; i < listfiles.length; i++){
                if(listfiles[i].isFile()){
                    pathArray.add(listfiles[i].getAbsolutePath());
                }
            }
        }catch (NullPointerException e){
            e.printStackTrace();
        }
        return pathArray;
    }
}












