package com.example.q.cs496_android;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.GridView;

public class gallery extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gallery);
        int img[] = {
                R.drawable.sample0,R.drawable.sample1,R.drawable.sample2,R.drawable.sample3,
                R.drawable.sample4, R.drawable.sample5,R.drawable.sample6, R.drawable.sample7
        };
        String names[] = {
                "sample0","sample1","sample2","sample3","sample4","sample5","sample6","sample7"

        };
        GridView gridview = (GridView) findViewById(R.id.gridView1);


        PictureAdapter adp = new PictureAdapter(img, names);
        gridview.setAdapter(adp);

    }
}
