package com.example.beach.kopie;

import android.app.Service;
import android.content.Intent;
import android.graphics.PixelFormat;
import android.os.IBinder;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;

import static android.view.WindowManager.LayoutParams;
import static android.view.WindowManager.LayoutParams.*;

public class OverlayService extends Service {
    private WindowManager windowManager;
    private View overlayView;

    public OverlayService() {
    }

    @Override
    public void onCreate() {
        createOverlay();
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onDestroy() {
        destroyOverlay();
    }

    private void createOverlay() {
        LayoutParams params = new LayoutParams(
                WRAP_CONTENT, WRAP_CONTENT, TYPE_SYSTEM_OVERLAY, 0, PixelFormat.TRANSLUCENT);
        params.gravity = (Gravity.END | Gravity.TOP);

        LayoutInflater inflater = (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);
        overlayView = inflater.inflate(R.layout.overlay_layout, null);

        windowManager = (WindowManager) getSystemService(WINDOW_SERVICE);
        windowManager.addView(overlayView, params);
    }

    private void destroyOverlay() {
        windowManager.removeView(overlayView);
    }
}
