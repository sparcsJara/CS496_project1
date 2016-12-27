package com.example.q.cs496_android;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.media.Image;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by Q on 2016-12-27.
 */

public class PictureAdapter extends BaseAdapter {
   int img[];
   String name[];

   public PictureAdapter( int[] img, String[] names){

            this.img = img;
        this.name = names;

    }



    @Override
    public int getCount() {
       return img.length;

    }

    @Override
    public Object getItem(int position) {
        return img[position];

    }


    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final int pos = position;
        final Context cnt = parent.getContext();
        if(convertView==null){
            LayoutInflater inf = (LayoutInflater) cnt.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inf.inflate(R.layout.galleryview, parent, false);
        }
       else {

            convertView = convertView;
        }
      ImageView iv = (ImageView) convertView.findViewById(R.id.picture);
      iv.setImageResource(img[pos]);
        TextView tv = (TextView) convertView.findViewById(R.id.name);
        tv.setText(name[pos]);
        Log.d("UNITY","flag1");
       return convertView;


    }

    private Drawable resize(Drawable image) {
        Bitmap b = ((BitmapDrawable)image).getBitmap();
        Bitmap bitmapResized = Bitmap.createScaledBitmap(b, 120, 120, false);
        return new BitmapDrawable(bitmapResized);
    }


    }

