package com.example.q.cs496_android;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by q on 2016-12-27.
 */

public class ImageAdapter extends BaseAdapter {
    private final ArrayList<Drawable> imgs = new ArrayList<>();
    private final ArrayList<String> names = new ArrayList<>();

    @Override
    public int getCount() {
        return imgs.size();
    }

    @Override
    public Object getItem(int position) {
        return imgs.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final int pos = position;
        final Context context = parent.getContext();

        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.item, parent, false);
        }

        ImageView imageView = (ImageView) convertView.findViewById(R.id.iconImg);
//        imageView.setImageDrawable(imgs.get(position));
        imageView.setImageDrawable(resize(imgs.get(position)));

        TextView textView = (TextView) convertView.findViewById(R.id.iconText);
        textView.setText(names.get(position));

        return convertView;
    }

    private Drawable resize(Drawable image) {
        Bitmap b = ((BitmapDrawable)image).getBitmap();
        Bitmap bitmapResized = Bitmap.createScaledBitmap(b, 120, 120, false);
        return new BitmapDrawable(bitmapResized);
    }



    public void addItem(Drawable img, String name) {
        imgs.add(img);
        names.add(name);
    }
}
