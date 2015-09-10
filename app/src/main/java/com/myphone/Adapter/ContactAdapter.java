package com.myphone.adapter;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

import com.myphone.R;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by ashkingsharma on 9/7/15.
 */
public class ContactAdapter extends BaseAdapter {

    private Context mContext;
    private LayoutInflater inflater;
    private HashMap<String, String> contactInfo;
    private ArrayList<String> person;
    private ArrayList<String> phone;

    public ContactAdapter(Context context, ArrayList<String> name,
                          ArrayList<String> phone){
        this.mContext = context;
        this.person = name;
        this.phone = phone;
    }

    @Override
    public int getCount() {
        return person.size();
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
    public View getView(final int position, View convertView, ViewGroup parent) {
        inflater = (LayoutInflater)mContext.
                getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        if(convertView == null)
            convertView = inflater.inflate(R.layout.list_contact_item, parent, false);

        TextView txtSample = (TextView) convertView.findViewById(R.id.txtContact);
        final TextView txtPhone = (TextView) convertView.findViewById(R.id.txtPhone);
        Button btnCall = (Button) convertView.findViewById(R.id.btnCall);
        btnCall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent callIntent = new Intent(Intent.ACTION_CALL);
                callIntent.setData(Uri.parse("tel:"+phone.get(position)));
                callIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

                mContext.startActivity(callIntent);
            }
        });

        txtSample.setText(person.get(position));
        txtPhone.setText(phone.get(position));


        return convertView;
    }
}
