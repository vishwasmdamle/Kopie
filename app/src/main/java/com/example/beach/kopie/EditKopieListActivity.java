package com.example.beach.kopie;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;

import java.util.ArrayList;

import static com.example.beach.kopie.FileService.*;


public class EditKopieListActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_kopie_list);
    }

    public void addToList(View v) {
        String inputText = String.valueOf(((EditText) findViewById(R.id.inputText)).getText());
        ArrayList<String> words = read(this);
        words.add(inputText);
        write(this, words);
    }
}
