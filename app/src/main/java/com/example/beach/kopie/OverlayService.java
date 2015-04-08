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
    private static boolean enabled = false;
    private WindowManager windowManager;
    private View overlayView;

    public OverlayService() {
    }

    public static boolean isEnabled() {
        return enabled;
    }

    public static void setEnabled(boolean enabled) {
        OverlayService.enabled = enabled;
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
                WRAP_CONTENT, WRAP_CONTENT, TYPE_SYSTEM_ALERT,
                FLAG_WATCH_OUTSIDE_TOUCH | FLAG_NOT_FOCUSABLE | FLAG_NOT_TOUCH_MODAL,
                PixelFormat.TRANSPARENT);
        params.gravity = (Gravity.END | Gravity.TOP);

        LayoutInflater inflater = (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);
        overlayView = inflater.inflate(R.layout.overlay_layout, null);

        overlayView.findViewById(R.id.kopieButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                System.out.println("###clicked");
            }
        });

        windowManager = (WindowManager) getSystemService(WINDOW_SERVICE);
        windowManager.addView(overlayView, params);
    }

    private void destroyOverlay() {
        windowManager.removeView(overlayView);
    }
}
