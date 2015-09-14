package com.myphone.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.media.Image;
import android.net.Uri;
import android.provider.ContactsContract;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.myphone.R;
import com.myphone.collector.Contacts;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by ashkingsharma on 9/7/15.
 */
public class ContactAdapter extends BaseAdapter {

    private Context mContext;
    private LayoutInflater inflater;
    private ArrayList<Contacts> contact;
    private ArrayList<String> person;
    private ArrayList<String> phone;

    public ContactAdapter(Context context, ArrayList<Contacts> contact){
        this.mContext = context;
        this.contact = contact;
    }

    @Override
    public int getCount() {
        return contact.size();
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
        TextView txtNick = (TextView) convertView.findViewById(R.id.txtNick);
        TextView txtTimes = (TextView) convertView.findViewById(R.id.txtTimes);
        TextView txtUsed = (TextView) convertView.findViewById(R.id.txtUsed);
        TextView txtPresence = (TextView) convertView.findViewById(R.id.txtPresence);
        TextView txtStatus = (TextView) convertView.findViewById(R.id.txtStatus);
        TextView txtLabel = (TextView) convertView.findViewById(R.id.txtLabel);
        TextView txtD = (TextView) convertView.findViewById(R.id.txtD);

        ImageView imgProfilePic = (ImageView) convertView.findViewById(R.id.imgContact);
        final TextView txtPhone = (TextView) convertView.findViewById(R.id.txtPhone);
        Button btnCall = (Button) convertView.findViewById(R.id.btnCall);
        btnCall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent callIntent = new Intent(Intent.ACTION_CALL);
                callIntent.setData(Uri.parse("tel:" + phone.get(position)));
                callIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

                mContext.startActivity(callIntent);
            }
        });

        String name = contact.get(position).getContactName();
        String num = contact.get(position).getContactNum();
        String used = contact.get(position).getTimesUsed();
        if(used == null)
            used = "0";

        if(name.equalsIgnoreCase(num))
                name = "Number Only";

        txtSample.setText(name);
        txtPhone.setText(num);

        txtNick.setText("Nick:" + contact.get(position).getNickName());
        txtTimes.setText("Contacted: " + contact.get(position).getTimesContacted() + " times");
        txtUsed.setText("Used: " + used + " times");
        txtPresence.setText("Present: " + contact.get(position).getPresence());
        txtStatus.setText("Status:" + contact.get(position).getStatus());
        txtLabel.setText("Label: "+ contact.get(position).getLabel());
        txtD.setText("Custom: " +contact.get(position).getD());

        Uri pic = contact.get(position).getProfilePic();

        Log.d("pic", pic.toString());

        return convertView;
    }
}
