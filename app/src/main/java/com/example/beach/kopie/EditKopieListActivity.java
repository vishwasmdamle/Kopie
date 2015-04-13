package com.example.beach.kopie;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

import static android.view.View.*;
import static com.example.beach.kopie.FileService.*;


public class EditKopieListActivity extends ActionBarActivity {

    public static final boolean ADD_BUTTON = true;
    public static final boolean EDIT_BUTTON = false;
    private int indexToRemove;
    private ArrayList<String> words;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_kopie_list);
        indexToRemove = -1;
        words = read(this);
        setupList();
    }

    private void setupList() {
        ListView view = (ListView) findViewById(R.id.editList);
        view.setAdapter(new WordsListAdaptor(this, words));
    }

    public void addToList(View v) {
        String inputText = String.valueOf(((EditText) findViewById(R.id.inputText)).getText());
        words.add(inputText);
        persistWordsList();
        resetView();
    }

    public void editInList(View v) {
        String editText = String.valueOf(((EditText) findViewById(R.id.inputText)).getText());
        words.set(indexToRemove, editText);
        persistWordsList();
        resetView();
    }

    private void resetView() {
        EditText editTextView = (EditText) findViewById(R.id.inputText);
        editTextView.setText("");
        setButtonType(ADD_BUTTON);
        setupList();
    }

    private ArrayList<String> persistWordsList() {
        write(this, words);
        return words;
    }

    private void loadToEdit(int index) {
        EditText editText = (EditText) findViewById(R.id.inputText);
        indexToRemove = index;
        editText.setText(words.get(index), TextView.BufferType.EDITABLE);
        editText.requestFocus();
        setButtonType(EDIT_BUTTON);
    }

    private void setButtonType(boolean isAddVisible) {
        Button addButton = (Button) findViewById(R.id.addButton);
        Button editButton = (Button) findViewById(R.id.editButton);
        if(isAddVisible) {
            addButton.setVisibility(VISIBLE);
            editButton.setVisibility(GONE);
        } else {
            addButton.setVisibility(GONE);
            editButton.setVisibility(VISIBLE);

        }
    }

    public void deleteListItem(View view) {
        words.remove((int) view.getTag());
        persistWordsList();
        resetView();
    }

    public void editListItem(View view) {
        loadToEdit((Integer) view.getTag());
    }
}
