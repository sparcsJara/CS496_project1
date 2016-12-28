package com.example.q.cs496_android;

import android.net.Uri;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity
implements ContactsFragment.OnFragmentInteractionListener,
        GalleryFragment.OnFragmentInteractionListener, OurFragment.OnFragmentInteractionListener{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        TabAdapter tabAdapter = new TabAdapter(getSupportFragmentManager());
        ViewPager viewPager = (ViewPager) findViewById(R.id.viewpager);

        tabAdapter.addFragment(new ContactsFragment(), "Contacts");
        tabAdapter.addFragment(new GalleryFragment(), "Gallery");
        tabAdapter.addFragment(new OurFragment(), "MOMO Finder");
        viewPager.setAdapter(tabAdapter);

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);
    }

    public void onFragmentInteraction(Uri uri) {

    }
}
