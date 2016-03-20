package com.example.beach.kopie.activity;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ToggleButton;

import com.example.beach.kopie.service.OverlayService;
import com.example.beach.kopie.R;

import static com.example.beach.kopie.service.SharedPreferenceService.isKopieEnabled;
import static com.example.beach.kopie.service.SharedPreferenceService.setKopieEnabled;


public class MainActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ToggleButton toggleButton = (ToggleButton) findViewById(R.id.toggleButton);
        toggleButton.setChecked(isKopieEnabled(this));
    }

    public void startOverlayService(View view) {
        ToggleButton toggleButton = (ToggleButton) findViewById(R.id.toggleButton);

        if(toggleButton.isChecked()) {
            setKopieEnabled(this, true);
            Intent intent = new Intent(this, OverlayService.class);
            startService(intent);
        } else {
            setKopieEnabled(this, false);
            stopService(new Intent(this, OverlayService.class));
        }
    }

    public void editKopieList(View view) {
        Intent intent = new Intent(this, EditKopieListActivity.class);
        startActivity(intent);
    }
}
