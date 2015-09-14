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

import java.util.Calendar;
import java.util.List;

/**
 * Created by ashkingsharma on 9/10/15.
 */
public class HistoryLogAdapter extends BaseAdapter {

    private Context context;
    private List<HistoryLog> historyLog ;

    public HistoryLogAdapter( Context context, List<HistoryLog> historyLog) {
        this.historyLog = historyLog;
        this.context = context;
    }


    @Override
    public int getCount() {
        return historyLog.size();
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
        TextView txtDate = (TextView) convertView.findViewById(R.id.txtCallDate);

        txtName.setText(historyLog.get(position).getName());
        txtContact.setText(historyLog.get(position).getContact());

        String type = historyLog.get(position).getType();
        String duration = historyLog.get(position).getDuration();

        Long dur = Long.parseLong(duration);

        if(dur == 0)
            duration = "Not Connected";
        else {

            int hours = (int) (dur / 3600);
            int mins = (int) ((dur % 3600) / 60);
            int secs = (int) ((dur % 3600) % 60);
            String dn = "";
            if(hours == 0 && mins == 0) {
                dn = "Sec";
            }
            else if(hours == 0 && mins !=0 )
                dn = "Mins";
            else
                dn = "Hours";

            duration = hours + ":" +
                    mins + ":" + secs + " " + dn;
        }

        Long milliSec = historyLog.get(position).getDat();

        Calendar cal = Calendar.getInstance();
        cal.setTimeInMillis(milliSec);

        int dayOfMonth = cal.get(Calendar.DAY_OF_MONTH);
        int monthOfYear = cal.get(Calendar.MONTH);
        int year = cal.get(Calendar.YEAR);

        int t = cal.get(Calendar.AM_PM);
        String ti = "";

        if(t== 0)
            ti ="AM";
        else
            ti = "PM";
        String time = cal.get(Calendar.HOUR) + ":" + cal.get(Calendar.MINUTE) + ":" +
                cal.get(Calendar.SECOND) + " " + ti;

        txtDuration.setText( type+ " : " + duration );
        txtDate.setText(time + "    " +dayOfMonth + "/" +
                monthOfYear +"/" + year  );

        return convertView;
    }
}
