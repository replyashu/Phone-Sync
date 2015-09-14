package com.myphone.fragment;

import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.Context;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.myphone.adapter.ContactAdapter;
import com.myphone.R;
import com.myphone.collector.Contacts;

import java.io.BufferedInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Set;

/**
 * Created by ashkingsharma on 9/7/15.
 */
public class GetContactsFragment extends Fragment {

    private HashMap<String,String> contactInfo;
    private ListView listContacts;
    private ArrayList<String> person;
    private ArrayList<String> phoneNum;
    private Contacts contact;
    private ArrayList<Contacts> myContacts;
    private ArrayList<Contacts> temp;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.contacts_fragment, container, false);

        listContacts = (ListView) rootView.findViewById(R.id.lstContacts);

        myContacts = new ArrayList<>();

        temp = new ArrayList<>();

        new ReadPhoneContacts().execute();

        return rootView;
    }

    private class ReadPhoneContacts extends AsyncTask<String, Void, String>{

        private ProgressDialog progressDialog;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
//
//            progressDialog = new ProgressDialog(getActivity());
//            progressDialog.setMessage("Getting Contacts ...");
//            progressDialog.setIndeterminate(true);
//            progressDialog.setCancelable(true);
//            progressDialog.show();



        }


        protected String doInBackground(String... params) {
//            MatrixCursor mMatrixCursor = new MatrixCursor(new String[] { "_id","name","photo","details"} );


            Uri contactsUri = ContactsContract.Contacts.CONTENT_URI;

            String details = "Details";
            contactInfo = new HashMap<>();
            person = new ArrayList<>();
            phoneNum = new ArrayList<>();

            ContentResolver cr = getActivity().getContentResolver();


            Cursor phones = getActivity().getContentResolver().query
                    (ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null,null,null, null);
            while(phones.moveToNext()){
                String name = phones.getString(phones.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME));
                String phone = phones.getString(phones.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
                String nick = phones.getString(phones.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME_ALTERNATIVE));
                String timesContacted = phones.getString(phones.getColumnIndex(ContactsContract.CommonDataKinds.Phone.TIMES_CONTACTED));
                String timesUsed = phones.getString(phones.getColumnIndex(ContactsContract.CommonDataKinds.Phone.TIMES_USED));
                String presence = phones.getString(phones.getColumnIndex(ContactsContract.CommonDataKinds.Phone.CONTACT_PRESENCE));
                String status = phones.getString(phones.getColumnIndex(ContactsContract.CommonDataKinds.Phone.CONTACT_STATUS));
                String d = phones.getString(phones.getColumnIndex(ContactsContract.CommonDataKinds.Phone.CONTACT_STATUS_LABEL));
                String d1 = phones.getString(phones.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DATA2));
                Long Contact_Id = phones.getLong(phones.getColumnIndex(ContactsContract.CommonDataKinds.Photo.CONTACT_ID));
                Uri profilePic = Uri.withAppendedPath(ContactsContract.Contacts.CONTENT_URI, String.valueOf(Contact_Id));

//                try {
//                    Bitmap btm = getContactBitmapFromURI(getActivity().getApplicationContext(),
//                            profilePic);
//
//                    Log.d("Found", btm.toString());
//                } catch (FileNotFoundException e) {
//                    e.printStackTrace();
//                }

                myContacts.add(new Contacts(name, phone, profilePic, nick, timesContacted, timesUsed,
                        presence, status, d, d1));

                person.add(name);
                phoneNum.add(phone);
                contactInfo.put(phone,name);
            }

//            phones.close();


            // Querying the table ContactsContract.Contacts to retrieve all the contacts

            return details;
        }

        @Override
        protected void onPostExecute(String s) {
//            progressDialog.dismiss();
            setUpListView();
            super.onPostExecute(s);

        }
    }

    public static Bitmap getContactBitmapFromURI(Context context, Uri uri) throws FileNotFoundException {
        InputStream input = context.getContentResolver().openInputStream(uri);
        if (input == null) {
            return null;
        }
        return BitmapFactory.decodeStream(input);
    }

    private void setUpListView(){

        Set<String> names = new HashSet<>();

        for(Contacts con : myContacts){
            if(names.add(con.getContactName())){
                temp.add(con);
            }
        }
        Collections.sort(temp, sortBy);
        ContactAdapter adapter = new ContactAdapter(getActivity().
                getApplicationContext(), temp);
        listContacts.setAdapter(adapter);
    }

    private Comparator<Contacts> sortBy = new Comparator<Contacts>() {
        @Override
        public int compare(Contacts lhs, Contacts rhs) {
            return lhs.getContactName().compareTo(rhs.getContactName());
        }
    };


}
