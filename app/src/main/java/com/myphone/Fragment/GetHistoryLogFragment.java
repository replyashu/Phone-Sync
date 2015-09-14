package com.myphone.fragment;

import android.app.ProgressDialog;
import android.database.Cursor;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.CallLog;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.myphone.R;
import com.myphone.adapter.HistoryLogAdapter;
import com.myphone.collector.HistoryLog;

import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by ashkingsharma on 9/8/15.
 */
public class GetHistoryLogFragment extends Fragment {

    private ListView lstCallLogs;
    private List<HistoryLog> callLog;

    private List<String> calName;
    private List<String> calNum;
    private List<String> calDuration;
    private List<String> calType;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.contacts_fragment, container, false);

        lstCallLogs = (ListView) rootView.findViewById(R.id.lstContacts);
        callLog = new LinkedList<>();
        calNum = new LinkedList<>();
        calName = new LinkedList<>();
        calDuration = new LinkedList<>();
        calType = new LinkedList<>();
        new ReadHistoryLog().execute();
        return rootView;
    }

    private class ReadHistoryLog extends AsyncTask<Void, Void, String>{

        private ProgressDialog progressDialog;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
//            progressDialog = new ProgressDialog(getActivity());
//            progressDialog.setMessage("Getting Call Logs ...");
//            progressDialog.setIndeterminate(true);
//            progressDialog.setCancelable(true);
//            progressDialog.show();

        }

        @Override
        protected String doInBackground(Void... params) {

            Cursor managedCursor = getActivity().managedQuery(CallLog.Calls.CONTENT_URI, null,null, null, null);

            int number = managedCursor.getColumnIndex(CallLog.Calls.NUMBER);
            int type = managedCursor.getColumnIndex(CallLog.Calls.TYPE);
            int date = managedCursor.getColumnIndex(CallLog.Calls.DATE);
            int duration = managedCursor.getColumnIndex(CallLog.Calls.DURATION);
            int name = managedCursor.getColumnIndex(CallLog.Calls.CACHED_NAME);
            int photo = managedCursor.getColumnIndex(CallLog.Calls.CACHED_PHOTO_ID);

            while (managedCursor.moveToNext()) {
                String phNum = managedCursor.getString(number);
                String callTypeCode = managedCursor.getString(type);
                String strcallDate = managedCursor.getString(date);
                Date callDate = new Date(Long.valueOf(strcallDate));
                String callDuration = managedCursor.getString(duration);
                String person = managedCursor.getString(name);
                String callType = null;
                int callcode = Integer.parseInt(callTypeCode);
                switch (callcode) {
                    case CallLog.Calls.OUTGOING_TYPE:
                        callType = "Outgoing";
                        break;
                    case CallLog.Calls.INCOMING_TYPE:
                        callType = "Incoming";
                        break;
                    case CallLog.Calls.MISSED_TYPE:
                        callType = "Missed";
                        break;
                }



                if(person == null)
                    person ="Number Only";

                Long dat = callDate.getTime();

                callLog.add(new HistoryLog(callDuration, callType, phNum, person, callDate, dat));
            }
//                managedCursor.close();
            Log.d("HistorySize: ", "" + callLog.size());
            return null;
        }

        @Override
        protected void onPostExecute(String s) {
//            progressDialog.dismiss();
            setUpListView();
            super.onPostExecute(s);
        }
    }

    private void setUpListView(){

        Collections.reverse(callLog);
        HistoryLogAdapter adapter = new HistoryLogAdapter(getActivity().
                getApplicationContext(), callLog);


        lstCallLogs.setAdapter(adapter);
    }
}
