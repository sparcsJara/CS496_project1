package com.example.q.cs496_android;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.provider.Settings;
import android.support.v4.app.ActivityCompat;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;


/**
 * Created by Q on 2016-12-27.
 */

public class phoneAdapter extends BaseAdapter {
    private ArrayList<String> name_list = new ArrayList<String>();
    private ArrayList<String> phone_list = new ArrayList<String>();

    @Override
    public int getCount() {
        return name_list.size();
    }

    @Override
    public Object getItem(int position) {
        return name_list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public void setJson(JSONArray arr) throws JSONException {
        JSONObject temp;
        name_list.clear();
        phone_list.clear();
        for (int i = 0; i < arr.length(); i++) {
            temp = arr.getJSONObject(i);

            name_list.add(temp.getString("name"));
            phone_list.add(temp.getString("phone_number"));
        }
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        final int pos = position;
        final Context context = parent.getContext();

        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.phone_list1, parent, false);
        }

        TextView nameView = (TextView) convertView.findViewById(R.id.name);
        nameView.setText(name_list.get(pos));

        TextView phoneView = (TextView) convertView.findViewById(R.id.phone);
        phoneView.setText(phone_list.get(pos));

        convertView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {

                Toast.makeText(context,"tel:" + phone_list.get(pos),Toast.LENGTH_SHORT).show();





                if (ActivityCompat.checkSelfPermission(context, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {



                        ActivityCompat.requestPermissions((Activity) context,
                                new String[]{Manifest.permission.CALL_PHONE},
                                1000);

                        return true;

                        // 필요한 권한과 요청 코드를 넣어서 권한허가요청에 대한 결과를 받아야 합니다



                }

                else {
                    Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + phone_list.get(pos)));
                    context.startActivity(intent);
                    return true;
                }
            }
        });

        return convertView;
    }
}
