package com.example.beach.kopie.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.example.beach.kopie.service.OverlayService;

public class StartAtBootReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            if (Intent.ACTION_BOOT_COMPLETED.equals(intent.getAction())) {
                Intent serviceIntent = new Intent(context, OverlayService.class);
                context.startService(serviceIntent);
            }
        }
    }