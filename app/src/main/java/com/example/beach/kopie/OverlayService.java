package com.example.beach.kopie;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.graphics.PixelFormat;
import android.os.IBinder;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

import static android.view.WindowManager.LayoutParams;
import static android.view.WindowManager.LayoutParams.*;
import static com.example.beach.kopie.FileService.*;

public class OverlayService extends Service implements AdapterView.OnItemClickListener {
    private WindowManager windowManager;
    private View overlayContainer;
    private LayoutParams params;
    private View overlayListContainer;
    private boolean dialogDisplayed = false;
    private static LayoutInflater inflater;


    public OverlayService() {
    }

    @Override
    public void onCreate() {
        inflater = (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);
        System.out.println("service started");
        init();
        createOverlay();
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    private void init() {
        params = new LayoutParams(
                WRAP_CONTENT, WRAP_CONTENT, TYPE_SYSTEM_ALERT,
                FLAG_WATCH_OUTSIDE_TOUCH | FLAG_NOT_FOCUSABLE | FLAG_NOT_TOUCH_MODAL,
                PixelFormat.TRANSLUCENT);
        windowManager = (WindowManager) getSystemService(WINDOW_SERVICE);
        dialogDisplayed = false;
    }

    @Override
    public void onDestroy() {
        destroyOverlay();
    }

    private void createOverlay() {
        overlayContainer = inflater.inflate(R.layout.overlay_layout, null);

        overlayContainer.findViewById(R.id.kopieButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!dialogDisplayed) {
                    buildDialog();
                } else {
                    destroyDialog();
                }
            }
        });

        addViewToWindow(overlayContainer, Gravity.END | Gravity.TOP);
    }

    private void buildDialog() {
        dialogDisplayed = true;
        inflater = (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);
        overlayListContainer = inflater.inflate(R.layout.overlay_list_layout, null);

        ListView listView = (ListView) overlayListContainer.findViewById(R.id.itemList);
        createAdaptor(inflater.getContext(), generateList(), listView);
        listView.setOnItemClickListener(this);

        addViewToWindow(overlayListContainer, Gravity.CENTER_HORIZONTAL | Gravity.CENTER_VERTICAL);
    }

    private void createAdaptor(Context context, ArrayList<String> list, ListView listView) {
        ArrayAdapter<String> arrayAdaptor = new ArrayAdapter<String>(context, android.R.layout.simple_list_item_1, list);
        listView.setAdapter(arrayAdaptor);
    }

    private void addViewToWindow(View view, int flags) {
        params.gravity = flags;
        windowManager.addView(view, params);
    }

    private ArrayList<String> generateList() {
        return read(inflater.getContext());
    }

    private void destroyOverlay() {
        if (dialogDisplayed)
            destroyDialog();
        windowManager.removeView(overlayContainer);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        String textToCopy = parent.getItemAtPosition(position).toString();
        if (parent == overlayListContainer.findViewById(R.id.itemList)) {
            android.content.ClipboardManager clipboard = (android.content.ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
            android.content.ClipData clip = android.content.ClipData.newPlainText("kopie", textToCopy);
            clipboard.setPrimaryClip(clip);

            Toast.makeText(inflater.getContext(), getString(R.string.OnCopyMessage), Toast.LENGTH_SHORT).show();
            destroyDialog();
        }
    }

    private void destroyDialog() {
        windowManager.removeView(overlayListContainer);
        dialogDisplayed = false;
    }
}
