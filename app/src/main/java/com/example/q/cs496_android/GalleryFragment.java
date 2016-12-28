package com.example.q.cs496_android;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.GridView;
import android.widget.Toast;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link GalleryFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link GalleryFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class GalleryFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    final int REQ_CODE_SELECT_IMAGE = 100;
    private OnFragmentInteractionListener mListener;
    private ImageAdapter imageAdapter = null;

    public GalleryFragment() {
        // Required empty public constructor
    }

    public void addToAdapter(Uri image_uri, String title) {
        try {
            InputStream inputStream = this.getActivity().getContentResolver().openInputStream(image_uri);
            Drawable yourChoice = Drawable.createFromStream(inputStream, image_uri.toString() );

            imageAdapter.addItem(yourChoice, title);

        } catch (FileNotFoundException e) {
            Drawable yourChoice = getResources().getDrawable(R.drawable.sample0);
            imageAdapter.addItem(yourChoice, "testing");

        }

        imageAdapter.notifyDataSetChanged();
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment GalleryFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static GalleryFragment newInstance() {
        GalleryFragment fragment = new GalleryFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
//        return inflater.inflate(R.layout.fragment_gallery, container, false);
        View view = inflater.inflate(R.layout.fragment_gallery, container, false);





        //GridView & adapter
        GridView gridView = (GridView) view.findViewById(R.id.grid1);
        imageAdapter = new ImageAdapter();
        gridView.setAdapter(imageAdapter);

        Button gallery_btn = (Button) view.findViewById(R.id.append);
        gallery_btn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(Intent.ACTION_PICK);
                intent.setType(android.provider.MediaStore.Images.Media.CONTENT_TYPE);
                intent.setData(android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(intent, REQ_CODE_SELECT_IMAGE);

            }
        });
        return view;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }




    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data){

        if(requestCode == REQ_CODE_SELECT_IMAGE){
            if(resultCode== Activity.RESULT_OK){
                Uri image_uri = data.getData();

                try {
                    InputStream inputStream = this.getActivity().getContentResolver().openInputStream(image_uri);
                    Drawable yourChoice = Drawable.createFromStream(inputStream, image_uri.toString() );

                    imageAdapter.addItem(yourChoice, "gallery");

                } catch (FileNotFoundException e) {
                    Drawable yourChoice = getResources().getDrawable(R.drawable.sample0);
                    imageAdapter.addItem(yourChoice, "testing");

                }

                imageAdapter.notifyDataSetChanged();
            }

        }
    }




    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
