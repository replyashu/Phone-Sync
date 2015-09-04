package com.myphone;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.zip.Inflater;

/**
 * Created by ashkingsharma on 9/4/15.
 */
public class NavigationListAdapter extends BaseAdapter {

    private Context context;
    private TextView txtSide;
    private String [] arr = {"Home", "Share", "About"};

    public void BaseAdapter(Context context){
        this.context = context;
    }

    @Override
    public int getCount() {
        return arr.length;
    }

    @Override
    public Object getItem(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if(convertView == null){
            convertView = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.navigation_item, null);
        }

        txtSide = (TextView) convertView.findViewById(R.id.textView);
        txtSide.setText(arr[position]);

        return convertView;
    }
}
