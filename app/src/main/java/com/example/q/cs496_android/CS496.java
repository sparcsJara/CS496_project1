package com.example.q.cs496_android;

import android.app.Activity;
import android.content.Context;
import android.provider.ContactsContract;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Toast;
import android.database.Cursor;
import android.util.Log;
import android.widget.AdapterView.*;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;

import java.io.InputStreamReader;
import java.util.ArrayList;
import java.io.InputStream;

public class CS496 extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cs496);
        Button connectBtn = (Button) findViewById(R.id.btn2);
        connectBtn.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                InputStream inputData = getResources().openRawResource(R.raw.number_list);
                String result = "";
                String temp;
                try{
                    BufferedReader json_reader = new BufferedReader(new InputStreamReader(inputData,"EUC_KR"));

                    while(true){
                        temp = json_reader.readLine();
                        if(temp != null){
                            result = result + temp;

                        }else{
                            break;

                        }

                    }
                    Log.d("UNITY",result);


                }catch(IOException e){

                    e.printStackTrace();

                }
                try {
                    JSONArray phone_list = new JSONArray(result);
                    JSONObject temp_json;

                    phoneAdapter phone_Adapter = new phoneAdapter();

                    ListView phone_ListView = (ListView) findViewById(R.id.phone_number_list);

                    phone_ListView.setAdapter(phone_Adapter);

                    phone_Adapter.setJson(phone_list);



                    //phone_ListView.setOnItemClickListener(onClickListItem);







                }
                catch(JSONException e){
                    e.printStackTrace();

                }


            }
        });
    }


}
