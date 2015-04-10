package com.example.beach.kopie;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ToggleButton;

import static com.example.beach.kopie.OverlayService.isEnabled;
import static com.example.beach.kopie.OverlayService.setEnabled;


public class MainActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ToggleButton toggleButton = (ToggleButton) findViewById(R.id.toggleButton);
        toggleButton.setChecked(isEnabled());
    }

    public void startOverlayService(View view) {
        ToggleButton toggleButton = (ToggleButton) findViewById(R.id.toggleButton);

        if(toggleButton.isChecked()) {
            setEnabled(true);
            Intent intent = new Intent(this, OverlayService.class);
            startService(intent);
        } else {
            setEnabled(false);
            stopService(new Intent(this, OverlayService.class));
        }
    }

    public void editKopieList(View view) {
        Intent intent = new Intent(this, EditKopieListActivity.class);
        startActivity(intent);
    }
}
