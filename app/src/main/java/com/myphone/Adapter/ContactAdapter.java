package com.myphone.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.myphone.R;

import org.w3c.dom.Text;

import java.util.zip.Inflater;

/**
 * Created by ashkingsharma on 9/7/15.
 */
public class ContactAdapter extends BaseAdapter {

    private Context mContext;
    private LayoutInflater inflater;

    public ContactAdapter(Context context){
        this.mContext = context;
    }

    @Override
    public int getCount() {
        return 2;
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

        if(convertView  == null)
            convertView = inflater.inflate(R.layout.list_contact_item, parent, false);

        TextView txtSample = (TextView) convertView.findViewById(R.id.txtContact);

        txtSample.setText("Item" + position);

        return null;
    }
}
