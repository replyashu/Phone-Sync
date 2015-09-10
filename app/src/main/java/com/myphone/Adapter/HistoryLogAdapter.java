package com.myphone.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.myphone.R;
import com.myphone.collector.HistoryLog;

import java.util.List;

/**
 * Created by ashkingsharma on 9/10/15.
 */
public class HistoryLogAdapter extends BaseAdapter {

    private Context context;
    private List<HistoryLog> historyLog ;
    private List<String> calNum;
    private List<String> calType;
    private List<String> calName;
    private List<String> calDuration;

    public HistoryLogAdapter( Context context, List<String> calName, List<String>
            calNum, List<String> calDuration,List<String> calType) {
        this.calDuration = calDuration;
        this.calName = calName;
        this.calType = calType;
        this.calNum = calNum;
        this.context = context;
    }


    @Override
    public int getCount() {
        return calName.size();
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

        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        if(convertView == null)
            convertView = inflater.inflate(R.layout.history_log_item, parent, false);

        TextView txtName = (TextView) convertView.findViewById(R.id.txtName);
        TextView txtContact = (TextView) convertView.findViewById(R.id.txtContact);
        TextView txtDuration = (TextView) convertView.findViewById(R.id.txtDuration);

        txtName.setText(calName.get(position));
        txtContact.setText(calNum.get(position));

        String type = calType.get(position);
        String duration = calDuration.get(position);

        int dur = Integer.parseInt(duration);

        if(dur == 0)
            duration = "Not Connected";
        else
            duration = dur + " Seconds";


        txtDuration.setText( type+ " : " + duration);

        return convertView;
    }
}
