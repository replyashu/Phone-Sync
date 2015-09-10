package com.myphone;

import android.accounts.Account;
import android.accounts.AccountManager;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.net.Uri;
import android.os.AsyncTask;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.app.ActionBar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.support.v4.widget.DrawerLayout;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Toolbar;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;
import java.util.Random;

public class MainActivity extends ActionBarActivity
        implements NavigationDrawerFragment.NavigationDrawerCallbacks {

    /**
     * Fragment managing the behaviors, interactions and presentation of the navigation drawer.
     */
    private NavigationDrawerFragment mNavigationDrawerFragment;

    /**
     * Used to store the last screen title. For use in {@link #restoreActionBar()}.
     */
    private CharSequence mTitle;
    private static String isVerified="0";
    private SharedPreferences sp;
    private static SharedPreferences passCode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        android.support.v7.widget.Toolbar mToolbar = (android.support.v7.widget.Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);

        DrawerLayout mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle mDrawerToggle = new ActionBarDrawerToggle(
                this,  mDrawerLayout, mToolbar,
                R.string.navigation_drawer_open, R.string.navigation_drawer_close
        );
        mDrawerLayout.setDrawerListener(mDrawerToggle);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        mDrawerToggle.syncState();

        mNavigationDrawerFragment = (NavigationDrawerFragment)
                getSupportFragmentManager().findFragmentById(R.id.navigation_drawer);
        mTitle = getTitle();


        // Set up the drawer.
//        mNavigationDrawerFragment.setUp(
//                R.id.navigation_drawer,
//                (DrawerLayout) findViewById(R.id.drawer_layout));
    }

    @Override
    public void onNavigationDrawerItemSelected(int position) {
        // update the main content by replacing fragments
        FragmentManager fragmentManager = getSupportFragmentManager();
        passCode = getSharedPreferences("pass", MODE_PRIVATE);

        isVerified = passCode.getString("pass", "0");

        if(isVerified.equalsIgnoreCase("0")) {
            fragmentManager.beginTransaction()
                    .replace(R.id.container, PlaceholderFragment.newInstance(position + 1))
                    .commit();
        }
        else{
            Intent intent = new Intent(getApplicationContext(),HomeActivity.class);
            startActivity(intent);
            finish();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();

    }

    public void onSectionAttached(int number) {
        switch (number) {
            case 1:
                mTitle = "Home";
                break;
            case 2:
                mTitle = "Share With Friends";
                shareWithFriends();
                break;
            case 3:
                mTitle = "About Us";
                break;
        }
    }

    private void shareWithFriends(){
        String str = "I Started using PhoneCal, an intelligent phonebook. \n"+
                "https://play.google.com/store/apps/details?id=ashu.app.smstweak";
        Intent sendIntent = new Intent();
        sendIntent.setAction(Intent.ACTION_SEND);

        sendIntent.putExtra(Intent.EXTRA_TEXT, str);
        sendIntent.setType("text/plain");
        startActivityForResult(Intent.createChooser(
                        sendIntent, "Share With Your Friends"),
                Activity.RESULT_OK);
    }

    public void restoreActionBar() {
        ActionBar actionBar = getSupportActionBar();
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_STANDARD);
        actionBar.setDisplayShowTitleEnabled(true);
        actionBar.setTitle(mTitle);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        if (!mNavigationDrawerFragment.isDrawerOpen()) {
            // Only show items in the action bar relevant to this screen
            // if the drawer is not showing. Otherwise, let the drawer
            // decide what to show in the action bar.
            getMenuInflater().inflate(R.menu.main, menu);
            restoreActionBar();
            return true;
        }
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
//        if (id == R.id.action_settings) {
//            return true;
//        }

        return super.onOptionsItemSelected(item);
    }

    /**
     * A placeholder fragment containing a simple view.
     */
    public static class PlaceholderFragment extends Fragment {
        /**
         * The fragment argument representing the section number for this
         * fragment.
         */
        private static final String ARG_SECTION_NUMBER = "section_number";
        private Button btnCall;
        private EditText editPhone;
        private EditText editISO;
        private Spinner emailChose;
        private Button btnRegister;
        private String phoneNum;
        private String email;
        private String iso="";
        ArrayList<String> messages;


        /**
         * Returns a new instance of this fragment for the given section
         * number.
         */
        public static PlaceholderFragment newInstance(int sectionNumber) {
            PlaceholderFragment fragment = new PlaceholderFragment();
            Bundle args = new Bundle();
            args.putInt(ARG_SECTION_NUMBER, sectionNumber);
            fragment.setArguments(args);
            return fragment;
        }

        public PlaceholderFragment() {
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_main, container, false);
            btnCall = (Button) rootView.findViewById(R.id.button);
            editPhone = (EditText) rootView.findViewById(R.id.editPhone);
            emailChose = (Spinner) rootView.findViewById(R.id.dropEmail);
            btnRegister = (Button) rootView.findViewById(R.id.btnRegister);
            editISO = (EditText) rootView.findViewById(R.id.editISO);

            btnCall.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    performDial("123");
                }
            });

            passCode = getActivity().getSharedPreferences("pass", MODE_PRIVATE);

            String pass = passCode.getString("pass","0");
            if(pass.equalsIgnoreCase("1")){
                isVerified = "1";
                Intent intent = new Intent(getActivity().getApplicationContext(),
                        HomeActivity.class);
                startActivity(intent);
                getActivity().finish();

            }
            String isoNum = getISONumber();

            CountryPrefix countryPrefix= new CountryPrefix();
            try {
                iso = countryPrefix.prefixFor(isoNum.toUpperCase());
            }
            catch (Exception e){

            }

            final String phone = getPhoneNumber();
            List<String> emails = getUserName();


            if(!isoNum.isEmpty()){
                editISO.setText(""+iso);
            }

            if(!phone.isEmpty()) {
                editPhone.setText("" + phone);
                phoneNum = phone;
            }

            ArrayAdapter<String> spinnerAdapter = new ArrayAdapter<String>(getActivity(),
                    android.R.layout.simple_spinner_item, android.R.id.text1);
            spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            emailChose.setAdapter(spinnerAdapter);
            for(int i = 0;i<emails.size(); i++){
                spinnerAdapter.add(emails.get(i));
            }

            spinnerAdapter.notifyDataSetChanged();



            emailChose.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    email = emailChose.getItemAtPosition(position).toString();
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {

                }
            });


            btnRegister.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    phoneNum = editPhone.getText().toString();

                    final Intent intent = new Intent(getActivity().
                            getApplicationContext(),HomeActivity.class);


                        if (phoneNum.length() != 10 && phone.isEmpty())
                            Toast.makeText(getActivity().getApplicationContext(),
                                    "Please Enter Valid phone number", Toast.LENGTH_SHORT)
                                    .show();
                        else {
                            final String smsPasscode = String.valueOf(100000 + new Random().nextInt(89999));
                            SmsManager smsManager = SmsManager.getDefault();
                            smsManager.sendTextMessage(phoneNum, null, "Your SMS verification passcode is " + smsPasscode, null, null);


//                            startActivity(intent);

                            LayoutInflater layoutInflater = LayoutInflater.from(getActivity());
                            View promptView = layoutInflater.inflate(R.layout.input_box, null);
                            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(getActivity());
                            alertDialogBuilder.setView(promptView);
                            alertDialogBuilder.setTitle("Verify the 6 Digit OTP sent to your mobile" );

                            final EditText editText = (EditText) promptView.findViewById(R.id.editPasscode);

                            // setup a dialog window
                            alertDialogBuilder.setCancelable(false)
                                    .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialog, int id) {
                                            String num = editText.getText().toString();
                                            if (num.equalsIgnoreCase(smsPasscode)) {
                                                Toast.makeText(getActivity().getApplicationContext(),
                                                        "User Verified", Toast.LENGTH_LONG).show();
                                                passCode.edit().putString("pass", "1").commit();

                                                startActivity(intent);
                                                getActivity().finish();
                                            } else {
                                                Toast.makeText(getActivity().getApplicationContext(),
                                                        "Passcode/ OTP did not match", Toast.LENGTH_LONG).show();
                                            }
                                        }
                                    });


                            // create an alert dialog
                            AlertDialog alert = alertDialogBuilder.create();
                            alert.show();


                        }

                }
            });

            return rootView;
        }

        private String getPhoneNumber(){
            TelephonyManager mTelephonyMgr;
            mTelephonyMgr = (TelephonyManager) getActivity().
                    getSystemService(Context.TELEPHONY_SERVICE);

            String number = mTelephonyMgr.getLine1Number();


            return number;

        }


        private String getISONumber(){
            TelephonyManager mTelephonyMgr;
            mTelephonyMgr = (TelephonyManager) getActivity().
                    getSystemService(Context.TELEPHONY_SERVICE);

            String number = mTelephonyMgr.getSimCountryIso();
            Log.d("number",number);

            return number;
        }

        public List<String> getUserName(){
            String user = "";
            AccountManager manager = AccountManager.get(getActivity());
//            Account[] accounts = getActivity().manager.getAccountsByType("com.google");
            Account[] accounts = manager.getAccountsByType("com.google");
            List<String> possibleEmails = new LinkedList<>();


            for (Account account : accounts) {
                // TODO: Check possibleEmail against an email regex or treat
                // account.name as an email address only for certain account.type values.
                possibleEmails.add(account.name);
            }

            return  possibleEmails;
        }

        private void performDial(String numberString) {
            if (!numberString.equals("")) {
                Uri number = Uri.parse("tel:" + numberString);
                Intent dial = new Intent(Intent.ACTION_DIAL, number);
                startActivity(dial);
            }
        }

        @Override
        public void onAttach(Activity activity) {
            super.onAttach(activity);
            ((MainActivity) activity).onSectionAttached(
                    getArguments().getInt(ARG_SECTION_NUMBER));
        }

        private class ReadAndVerifyPassCode extends AsyncTask<String, Void, String>{

            private ProgressDialog progressDialog;

            @Override
            protected void onPreExecute() {
                super.onPreExecute();

                progressDialog = new ProgressDialog(getActivity());
                progressDialog.setMessage("Fetching Results For You");
                progressDialog.setIndeterminate(true);
                progressDialog.setCancelable(true);
                progressDialog.show();
            }

            @Override
            protected String doInBackground(String... params) {
                //Send the phone number with email to server

                Cursor cursor = getActivity().getApplicationContext().
                        getContentResolver().query(Uri.parse("content://sms/inbox"), null, null, null, null);

                if (cursor.moveToFirst()) { // must check the result to prevent exception
                    for(int i = 0; i<10; i++){
                        String msgData = cursor.getString(cursor.getColumnIndexOrThrow("body")).toString();
                    }
                }

                return null;
            }

            @Override
            protected void onPostExecute(String s) {
                progressDialog.dismiss();
                super.onPostExecute(s);

            }
        }


    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}
