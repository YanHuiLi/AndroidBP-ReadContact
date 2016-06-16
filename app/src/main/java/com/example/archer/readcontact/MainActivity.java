package com.example.archer.readcontact;

import android.database.Cursor;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import java.util.ArrayList;
import java.util.HashMap;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ListView mListView = (ListView) findViewById(R.id.lv_readContact);
        ArrayList<HashMap<String,String>> readContact=  readContact();
//        System.out.println(readContact);
        assert mListView != null;
        mListView.setAdapter(new SimpleAdapter(this,readContact,R.layout.contact_list_item,
                new String[]{"name","phone"},new int[]{R.id.tv_name,R.id.tv_phone}));

    }
    private ArrayList<HashMap<String,String>> readContact(){
        //首先从raw_contacts读取联系人的Id（"contact_id"）
        //根据（"contact_id"）从data表中查出相应的数据，电话号码和联系人名称
        //然后根据mintape来区分哪个是联系人，哪个是电话号码

        Uri rawContactUri=Uri.parse("content://com.android.contacts/raw_contacts");
        Uri dataUri=Uri.parse("content://com.android.contacts/data");

        ArrayList<HashMap<String,String>>list=new ArrayList<>();


        Cursor rawContactCusor = getContentResolver().query(rawContactUri,
                new String[]{"contact_id"}, null, null, null);

        if (rawContactCusor!=null){
            while(rawContactCusor.moveToNext()){
                String rawContactId = rawContactCusor.getString(0);
//                System.out.println(rawContactId);

            //根据查询到的contact_id去查询出相应的联系人的号码和名称,实际上查询是view_data里面去查，
                //我考虑的应该是因为安全性和高效性的方式去考虑，为什么要从view_data去查询

                Cursor DataCursor = getContentResolver().query(dataUri, new String[]{"data1", "mimetype"},
                        "contact_id=?", new String[]{rawContactId}, null);

                if (DataCursor!=null){
                    HashMap<String,String>map= new HashMap<>();
                    while (DataCursor.moveToNext()){
                        String data1 = DataCursor.getString(0);
                        String  mimetype=DataCursor.getString(1);
//                        System.out.println(rawContactId+";"+data1+";"+mimetype);

                        if ("vnd.android.cursor.item/phone_v2".equals(mimetype)){
                            map.put("phone",data1);
                        }else if ("vnd.android.cursor.item/name".equals(mimetype)){
                            map.put("name",data1);
                        }

                    }
                    list.add(map);
                    DataCursor.close();
                }

            }
            rawContactCusor.close();
        }
        return list;

    }


}
