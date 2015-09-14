package com.myphone.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.myphone.R;
import com.myphone.collector.Favorite;

import org.w3c.dom.Text;

import java.util.ArrayList;

/**
 * Created by ashkingsharma on 9/8/15.
 */
public class FavoriteAdapter extends BaseAdapter {

    private Context context;
    private ArrayList<Favorite> favorites;

    public FavoriteAdapter(Context context, ArrayList<Favorite> favorites) {
        this.context = context;
        this.favorites = favorites;

    }

    @Override
    public int getCount() {
        return favorites.size();
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

        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        if(convertView == null)
            convertView = inflater.inflate(R.layout.favorite_item, parent, false);

        TextView txtName = (TextView) convertView.findViewById(R.id.txtName);
        TextView txtType = (TextView) convertView.findViewById(R.id.txtType);

        txtName.setText(favorites.get(position).getDisplayName());
        txtType.setText("Source: "+ favorites.get(position).getEmailOfUser());

        return convertView;
    }
}
