package com.example.q.cs496_android;

import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Gallery;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity
implements ContactsFragment.OnFragmentInteractionListener,
        GalleryFragment.OnFragmentInteractionListener, OurFragment.OnFragmentInteractionListener,
        OurFragment.CustomOnClickListener{

    private GalleryFragment galleryFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        TabAdapter tabAdapter = new TabAdapter(getSupportFragmentManager());
        ViewPager viewPager = (ViewPager) findViewById(R.id.viewpager);

        galleryFragment = new GalleryFragment();
        tabAdapter.addFragment(new ContactsFragment(), "Contacts");
        tabAdapter.addFragment(galleryFragment, "Gallery");
        tabAdapter.addFragment(new OurFragment(), "MOMO Finder");
        viewPager.setAdapter(tabAdapter);

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);
    }

    public void onClicked(int id, Uri uri, String title) {
        Toast.makeText(this, uri.toString(), Toast.LENGTH_SHORT).show();
        galleryFragment.addToAdapter(uri, title);
    }

    public void onFragmentInteraction(Uri uri) {

    }
}
