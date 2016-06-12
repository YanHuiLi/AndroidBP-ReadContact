package com.example.archer.readcontact;

import android.database.Cursor;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;

public class MainActivity extends AppCompatActivity {

    private ListView mListView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
         mListView= (ListView) findViewById(R.id.lv_readContact);
        readContact();

    }
    private void readContact(){
        //首先从raw_contacts读取联系人的Id（"contact_id"）
        //根据（"contact_id"）从data表中查出相应的数据，电话号码和联系人名称
        //然后根据mintape来区分哪个是联系人，哪个是电话号码

        Uri rawContactUri=Uri.parse("content://com.android.contacts/raw_contacts");
        Cursor rawContactCusor = getContentResolver().query(rawContactUri, new String[]{"contact_id"}, null, null, null);

        if (rawContactCusor!=null){
            while(rawContactCusor.moveToNext()){
                String rawContactId = rawContactCusor.getString(0);
                System.out.println(rawContactId);
                System.out.println("ceshiceshi ");

            }
        }

    }


}
