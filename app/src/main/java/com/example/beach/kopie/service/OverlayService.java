package com.example.beach.kopie.service;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.graphics.PixelFormat;
import android.graphics.Point;
import android.os.IBinder;
import android.view.DragEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.beach.kopie.R;

import java.util.ArrayList;

import static android.view.Gravity.*;
import static android.view.View.*;
import static android.view.WindowManager.LayoutParams;
import static android.view.WindowManager.LayoutParams.*;
import static com.example.beach.kopie.util.FileReader.*;

public class OverlayService extends Service implements AdapterView.OnItemClickListener, OnLongClickListener {
    private WindowManager windowManager;
    private View overlayContainer;
    private LayoutParams kopieParams;
    private LayoutParams listParams;
    private View overlayListContainer;
    private boolean dialogDisplayed = false;
    private static LayoutInflater inflater;
    private Point screenSize;
    private Point defaultLocation;
    private Point storedLocation;


    public OverlayService() {
    }

    @Override
    public void onCreate() {
        inflater = (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);
        init();
        createOverlay();
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    private void init() {
        kopieParams = new LayoutParams(
                WRAP_CONTENT, WRAP_CONTENT, TYPE_SYSTEM_ALERT,
                FLAG_WATCH_OUTSIDE_TOUCH | FLAG_NOT_FOCUSABLE | FLAG_NOT_TOUCH_MODAL,
                PixelFormat.TRANSLUCENT);
        kopieParams.gravity = TOP | LEFT;
        listParams = new LayoutParams(
                WRAP_CONTENT, WRAP_CONTENT, TYPE_SYSTEM_ALERT,
                FLAG_WATCH_OUTSIDE_TOUCH | FLAG_NOT_FOCUSABLE | FLAG_NOT_TOUCH_MODAL,
                PixelFormat.TRANSLUCENT);
        listParams.gravity = CENTER_HORIZONTAL | CENTER_VERTICAL;
        windowManager = (WindowManager) getSystemService(WINDOW_SERVICE);

        setupDisplayVariables();

        storedLocation = SharedPreferenceService.getKopieLocation(inflater.getContext(), defaultLocation);

        dialogDisplayed = false;
    }

    private void setupDisplayVariables() {
        screenSize = new Point();
        defaultLocation = new Point();
        windowManager.getDefaultDisplay().getSize(screenSize);
        defaultLocation.x = 0;
        defaultLocation.y = 0;
    }

    @Override
    public void onDestroy() {
        destroyOverlay();
    }

    private void createOverlay() {
        overlayContainer = inflater.inflate(R.layout.overlay_layout, null);
        kopieParams.x = storedLocation.x;
        kopieParams.y = storedLocation.y;
        windowManager.addView(overlayContainer, kopieParams);
        windowManager.updateViewLayout(overlayContainer, kopieParams);

        final View kopieButton = overlayContainer.findViewById(R.id.kopieButton);
        kopieButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!dialogDisplayed) {
                    buildDialog();
                } else {
                    destroyDialog();
                }
            }
        });
        kopieButton.setOnLongClickListener(this);
    }

    private void buildDialog() {
        dialogDisplayed = true;
        inflater = (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);
        overlayListContainer = inflater.inflate(R.layout.overlay_list_layout, null);

        ListView listView = (ListView) overlayListContainer.findViewById(R.id.itemList);
        TextView textView = (TextView) overlayListContainer.findViewById(R.id.emptyElement);
        textView.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                destroyDialog();
            }
        });
        createAdaptor(inflater.getContext(), generateList(), listView);
        listView.setOnItemClickListener(this);
        listView.setEmptyView(overlayListContainer.findViewById(R.id.emptyElement));

        windowManager.addView(overlayListContainer, listParams);
    }

    private void createAdaptor(Context context, ArrayList<String> list, ListView listView) {
        ArrayAdapter<String> arrayAdaptor = new ArrayAdapter<>(context, android.R.layout.simple_list_item_1, list);
        listView.setAdapter(arrayAdaptor);
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

    @Override
    public boolean onLongClick(View view) {
        final View kopieButton = overlayContainer.findViewById(R.id.kopieButton);
        if (view == kopieButton) {
            Point screenPoint = new Point();
            windowManager.getDefaultDisplay().getSize(screenPoint);
            overlayContainer.setMinimumWidth(screenPoint.x);
            overlayContainer.setMinimumHeight(screenPoint.y);

            kopieButton.setVisibility(GONE);

            overlayContainer.setOnDragListener(new OnDragListener() {
                public int lastX;
                public int lastY;

                @Override
                public boolean onDrag(View view, DragEvent dragEvent) {
                    int action = dragEvent.getAction();
                    if (action == DragEvent.ACTION_DRAG_ENDED || action == DragEvent.ACTION_DROP) {
                        kopieParams.x = lastX - kopieButton.getWidth() / 2;
                        kopieParams.y = lastY - kopieButton.getHeight() / 2;

                        overlayContainer.setMinimumHeight(0);
                        overlayContainer.setMinimumWidth(0);

                        windowManager.updateViewLayout(overlayContainer, kopieParams);
                        kopieButton.setVisibility(VISIBLE);

                        SharedPreferenceService.saveKopieLocation(inflater.getContext(), new Point(kopieParams.x, kopieParams.y));
                    } else {
                        lastX = (int) dragEvent.getX();
                        lastY = (int) dragEvent.getY();
                    }
                    return true;
                }
            });
            kopieButton.startDrag(null, new DragShadowBuilder(kopieButton), null, 0);
            return true;
        }
        return false;
    }
}
