package com.myphone.fragment;

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

import com.myphone.R;
import com.myphone.adapter.FavoriteAdapter;
import com.myphone.collector.Contacts;
import com.myphone.collector.Favorite;

import java.util.ArrayList;

/**
 * Created by ashkingsharma on 9/8/15.
 */
public class GetFavoriteFragment extends Fragment {

    private ListView listFavorite;
    private Favorite favorite;
    private ArrayList<Favorite> myFavorite;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.contacts_fragment, container, false);
        listFavorite = (ListView) rootView.findViewById(R.id.lstContacts);
        myFavorite = new ArrayList<>();

        new GetFavoritecontacts().execute();
        return rootView;
    }

    private class GetFavoritecontacts extends AsyncTask<String, Void, String>{

        @Override
        protected String doInBackground(String... params) {


            Uri queryUri = ContactsContract.Contacts.CONTENT_URI;

            String[] projection = new String[]{
                    ContactsContract.Contacts._ID,
                    ContactsContract.Contacts.DISPLAY_NAME,
                    ContactsContract.Contacts.STARRED};

            String selection =ContactsContract.Contacts.STARRED + "="+"1";

            Cursor cursor = getActivity().managedQuery(queryUri, projection, selection, null, null);

            while (cursor.moveToNext()) {
                String contactID = cursor.getString(cursor
                        .getColumnIndex(ContactsContract.Contacts._ID));

                String title = cursor.getString(
                        cursor.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME));


                Cursor result = getActivity().managedQuery(ContactsContract.Contacts.CONTENT_URI, null,
                        ContactsContract.Contacts._ID + " = ?",
                        new String[]{contactID}, null);
                if (result.moveToFirst()) {

                    for(int i=0; i< result.getColumnCount(); i++){

                        String type = result.getColumnName(i);

                        if(type.equalsIgnoreCase("contact_account_type")){
                            myFavorite.add(new Favorite(result.getString(i),title));
                            Log.i("CONTACTSTAG", result.getColumnName(i) + ": "
                                    + result.getString(i));
                        }

                    }
                }



            }
            return "got";
        }

        @Override
        protected void onPostExecute(String s) {
            setUpListView();
            super.onPostExecute(s);
        }
    }

    private void setUpListView(){

        FavoriteAdapter adapter = new FavoriteAdapter(getActivity().
                getApplicationContext(), myFavorite);

        listFavorite.setAdapter(adapter);

    }
}
