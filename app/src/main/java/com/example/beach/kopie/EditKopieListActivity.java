package com.example.beach.kopie;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

import static android.view.View.*;
import static com.example.beach.kopie.FileService.*;


public class EditKopieListActivity extends ActionBarActivity implements AdapterView.OnItemClickListener {

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
        ArrayAdapter<String> editTextAdaptor = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, words);
        view.setAdapter(editTextAdaptor);
        view.setOnItemClickListener(this);
    }

    public void addToList(View v) {
        String inputText = String.valueOf(((EditText) findViewById(R.id.inputText)).getText());
        words.add(inputText);
        persistWordsList();
        resetView();
    }

    public void editInList(View v) {
        String editText = String.valueOf(((EditText) findViewById(R.id.inputText)).getText());
        words.add(indexToRemove, editText);
        persistWordsList();
        setButtonVisibility(true);
        resetView();
    }

    private void resetView() {
        EditText editTextView = (EditText) findViewById(R.id.inputText);
        editTextView.setText("");
        setupList();
    }

    private ArrayList<String> persistWordsList() {
        write(this, words);
        return words;
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        if(parent == findViewById(R.id.editList)) {
            loadToEdit(position);
        }
    }

    private void loadToEdit(int index) {
        EditText editText = (EditText) findViewById(R.id.inputText);
        indexToRemove = index;
        editText.setText(words.get(index), TextView.BufferType.EDITABLE);
        editText.requestFocus();
        setButtonVisibility(false);
    }

    private void setButtonVisibility(boolean isAddVisible) {
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
}
