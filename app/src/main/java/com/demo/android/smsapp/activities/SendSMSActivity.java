package com.demo.android.smsapp.activities;


import android.Manifest;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.v4.app.ActivityCompat;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.telephony.SmsManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.support.v7.widget.Toolbar;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.demo.android.smsapp.R;
import com.demo.android.smsapp.adapters.ListContactsAdapter;
import com.demo.android.smsapp.listeners.ContactsItemClickListener;


import java.util.ArrayList;

public class SendSMSActivity extends ParentActivity implements View.OnClickListener {

    EditText etMsg;
    Button btnSend;
    RecyclerView recyclerView;
    ArrayList<String> ContactsName;
    ArrayList<String> ContactsPhone;
    Cursor cursor;
    String name, phonenumber;
    public static final int RequestPermissionCode = 1;
    SmsManager smsManager;
    ListContactsAdapter listContactsAdapter;

    public void onCreate(Bundle onSaveStateInstanceState) {
        super.onCreate(onSaveStateInstanceState);
        setContentView(R.layout.activity_send_sms);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setTitle("Send SMS");


        etMsg = (EditText) findViewById(R.id.et_msg);
        btnSend = (Button) findViewById(R.id.btn_send);

        btnSend.setClickable(false);
        ContactsName = new ArrayList<String>();
        ContactsPhone = new ArrayList<String>();


        GetContactsIntoArrayList();
        recyclerView = (RecyclerView) findViewById(R.id.reListContact);
        listContactsAdapter = new ListContactsAdapter(this, ContactsName, ContactsPhone);
        //  Log.e("total:",ContactsName.size()+" "+ ContactsPhone.size());
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setAdapter(listContactsAdapter);
        recyclerView.addOnItemTouchListener(
                new ContactsItemClickListener(SendSMSActivity.this, new ContactsItemClickListener.OnItemClickListener() {
                    @Override
                    public void onItemClick(View view, int position) {
                        // TODO Handle item click
                        btnSend.setClickable(true);

                        name = ContactsName.get(position);
                        phonenumber = ContactsPhone.get(position);
                        btnSend.setOnClickListener(SendSMSActivity.this);
                        smsManager = SmsManager.getDefault();

                    }
                })
        );
    }


    public void GetContactsIntoArrayList() {

        ContentResolver contentResolver = getContentResolver();
        cursor = contentResolver.query(ContactsContract.Contacts.CONTENT_URI, null, null, null, ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME + " ASC");
        if (cursor.getCount() > 0) {
            while (cursor.moveToNext()) {

                int hasPhoneNumber = Integer.parseInt(cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts.HAS_PHONE_NUMBER)));
                if (hasPhoneNumber > 0) {
                    String id = cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts._ID));
                    String name = cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME));
                    String phoneNumber = null;
                    ContactsName.add(name);

                    Cursor phoneCursor = contentResolver.query(
                            ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
                            null,
                            ContactsContract.CommonDataKinds.Phone.CONTACT_ID + " = ?",
                            new String[]{id},
                            null);
                    if (phoneCursor.moveToNext()) {
                        phoneNumber = phoneCursor.getString(phoneCursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
                        ContactsPhone.add(phoneNumber);
                    }

                    phoneCursor.close();

                }
            }
            cursor.close();

        }
    }

    @Override
    public void onRequestPermissionsResult(int RC, String per[], int[] PResult) {

        switch (RC) {

            case RequestPermissionCode:

                if (PResult.length > 0 && PResult[0] == PackageManager.PERMISSION_GRANTED) {

                    Toast.makeText(SendSMSActivity.this, "Permission Granted, Now your application can access CONTACTS.", Toast.LENGTH_LONG).show();

                } else {

                    Toast.makeText(SendSMSActivity.this, "Permission Canceled, Now your application cannot access CONTACTS.", Toast.LENGTH_LONG).show();

                }
                break;
        }
    }


    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.btn_send) {
            if (etMsg.getText().length() == 0) {
                Toast.makeText(SendSMSActivity.this, "error!! enter text", Toast.LENGTH_LONG);

            } else {
                smsManager.sendTextMessage(phonenumber, null, etMsg.getText().toString(), null, null);
                etMsg.setText("");

                Intent intent = new Intent(this, SMSChatActivity.class);
                intent.putExtra("number", phonenumber);
                startActivity(intent);
            }
        }
    }
}

