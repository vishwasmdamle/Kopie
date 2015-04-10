package com.example.beach.kopie;

import android.content.Context;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.Arrays;

public class FileService {

    public static ArrayList<String> read(Context context) {
        StringBuilder dataString = new StringBuilder();
        try {
            FileInputStream fileInputStream = context.openFileInput(Constants.FILENAME);
            while (fileInputStream.available() > 0) {
                dataString.append((char) fileInputStream.read());
            }
        } catch (java.io.IOException e) {
            e.printStackTrace();
        }
        if(dataString.toString().isEmpty()) return new ArrayList<>();
        else return new ArrayList<>(Arrays.asList(dataString.toString().split("\n")));
    }

    public static void write(Context context, ArrayList<String> words) {
        StringBuilder dataString = new StringBuilder();
        try {
            FileOutputStream fileOutputStream = context.openFileOutput(Constants.FILENAME, Context.MODE_PRIVATE);
            for(String word : words) {
                dataString.append(word).append("\n");
            }
            fileOutputStream.write(dataString.toString().getBytes());
        } catch (java.io.IOException e) {
            e.printStackTrace();
        }
    }
}
