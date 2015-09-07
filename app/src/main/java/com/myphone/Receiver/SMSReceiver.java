package com.myphone.Receiver;

import android.app.AlertDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.telephony.SmsMessage;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;

import com.myphone.R;

/**
 * Created by ashkingsharma on 9/8/15.
 */
public class SMSReceiver extends BroadcastReceiver {

    final SmsManager sms = SmsManager.getDefault();
    private Cursor cursor;
    private SharedPreferences sp;
    private SharedPreferences sp1;

    @Override
    public void onReceive(Context context, Intent intent) {

        final Bundle bundle = intent.getExtras();
        try{
            if(bundle != null){
                final Object [] pdusObj = (Object []) bundle.get("pdus");
                SmsMessage[] msgs = new SmsMessage[pdusObj.length];
                String smsSender = "";
                String smsBody = "";
                Long timeStamp = 0L;
                sp = context.getSharedPreferences("pass", Context.MODE_PRIVATE);
                sp1 = context.getSharedPreferences("verify", Context.MODE_PRIVATE);
                String getNum = sp.getString("pass", "0");
                String isVerified = sp1.getString("verify", "0");

                Log.d("Verification", "Verification Reached" + getNum);
                for (int i = 0; i < pdusObj.length; i++) {

                    msgs[i] = SmsMessage.createFromPdu((byte[])pdusObj[i]);

                    String snd = msgs[i].getOriginatingAddress();
                    smsSender += snd;
                    smsBody += msgs[i].getMessageBody().toString();
                    Log.d("Verification", "Verification Reached" + smsBody);
                    if(smsBody.contains(getNum)){
                        sp1.edit().putString("verify","1").commit();
                        LayoutInflater inflater = LayoutInflater.from(context);

                        View promptView = inflater.inflate(R.layout.input_box, null);
                        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);
                        alertDialogBuilder.setView(promptView);
                        // setup a dialog window
                        alertDialogBuilder.setCancelable(false)
                                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int id) {
                                        dialog.dismiss();
                                    }
                                });

                        // create an alert dialog
                        AlertDialog alert = alertDialogBuilder.create();
                        alert.show();

                        Log.d("Verification", "Verification Succesfull");
                    }

                } // end for loop
            }
        }
        catch (Exception e){

        }


    }
}
