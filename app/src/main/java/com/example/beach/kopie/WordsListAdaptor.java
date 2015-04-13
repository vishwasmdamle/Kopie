package com.example.beach.kopie;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class WordsListAdaptor extends BaseAdapter {
    private Context context;
    private ArrayList<String> words;

    public WordsListAdaptor(Context context, ArrayList<String> words) {

        this.context = context;
        this.words = words;
    }

    @Override
    public int getCount() {
        return words.size();
    }

    @Override
    public Object getItem(int position) {
        return words.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if(convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.list_item_layout, null);
        }
        ((TextView) convertView.findViewById(R.id.itemText)).setText(words.get(position));
        convertView.findViewById(R.id.editButton).setTag(position);
        convertView.findViewById(R.id.deleteButton).setTag(position);
        return convertView;
    }
}
