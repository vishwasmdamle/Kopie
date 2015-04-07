package com.example.beach.kopie;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ToggleButton;


public class MainActivity extends ActionBarActivity {

    private boolean isEnabled = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ToggleButton toggleButton = (ToggleButton) findViewById(R.id.toggleButton);
        toggleButton.setChecked(isEnabled);
    }

    public void startOverlayService(View view) {
        ToggleButton toggleButton = (ToggleButton) findViewById(R.id.toggleButton);

        if(toggleButton.isChecked()) {
            isEnabled = true;
            startService(new Intent(this, OverlayService.class));
        } else {
            isEnabled = false;
            stopService(new Intent(this, OverlayService.class));
        }
    }
}
