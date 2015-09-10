package com.myphone.fragment;

import android.app.ProgressDialog;
import android.database.Cursor;
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

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by ashkingsharma on 9/7/15.
 */
public class GetContactsFragment extends Fragment {

    private HashMap<String,String> contactInfo;
    private ListView listContacts;
    private ArrayList<String> person;
    private ArrayList<String> phoneNum;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.contacts_fragment, container, false);

        listContacts = (ListView) rootView.findViewById(R.id.lstContacts);

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

            String details = "";
            contactInfo = new HashMap<>();
            person = new ArrayList<>();
            phoneNum = new ArrayList<>();

            Cursor phones = getActivity().getContentResolver().query
                    (ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null,null,null, null);
            while(phones.moveToNext()){
                String name = phones.getString(phones.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME));
                String phone = phones.getString(phones.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));

                person.add(name);
                phoneNum.add(phone);
                contactInfo.put(phone,name);
                Log.d("data", "Name: " + name + "\nPhone: " + phone);
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

    private void setUpListView(){
        ContactAdapter adapter = new ContactAdapter(getActivity().
                getApplicationContext(), person, phoneNum);
        Log.d("size", person.size() + "");
        listContacts.setAdapter(adapter);
    }

}
