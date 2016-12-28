package com.example.q.cs496_android;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.JavascriptInterface;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Ion;
import com.squareup.picasso.Picasso;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Random;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link OurFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link OurFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class OurFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    static List<String> sources = new ArrayList<>();

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public OurFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment OurFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static OurFragment newInstance(String param1, String param2) {
        OurFragment fragment = new OurFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        final View view = inflater.inflate(R.layout.fragment_our, container, false);
        final ImageView iv = (ImageView) view.findViewById(R.id.photo);

        final String[] query = {"https://search.naver.com/search.naver?where=image&sm=tab_jum&ie=utf8&query=%EB%AA%A8%EB%AA%A8"};

        Button input_btn = (Button) view.findViewById(R.id.inputBtn);
        final EditText edit = (EditText) view.findViewById(R.id.input);
        input_btn.setOnClickListener(new View.OnClickListener() {
                                         @Override
                                         public void onClick(View v) {
                                                String keyword = edit.getText().toString();
                                                query[0] = "https://search.naver.com/search.naver?where=image&sm=tab_jum&ie=utf8&query="+keyword;

                                         }
                                     }


        );
        Ion.with(getContext()).load(query[0]).asString().setCallback(new FutureCallback<String>() {
            @Override
            public void onCompleted(Exception e, String html) {
                Document doc = Jsoup.parse(html);
                Elements imgs = doc.select("img._img");
                final List<String> sources = new ArrayList<>();
                String testing;
                for (int i=0 ; i<imgs.size() ; i++) {
                    testing = imgs.get(i).attr("data-source").toString();
                    sources.add(testing);
                };
                Random random = new Random();
                int url_size = sources.size();
                final int position = random.nextInt(url_size-1);

                Picasso.with(view.getContext()).load(sources.get(position)).into(iv);
           }});

        Button reloadBtn = (Button) view.findViewById(R.id.reloadBtn);
        reloadBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Ion.with(getContext()).load(query[0]).asString().setCallback(new FutureCallback<String>() {
                    @Override
                    public void onCompleted(Exception e, String html) {
                        Document doc = Jsoup.parse(html);
                        Elements imgs = doc.select("img._img");
                        final List<String> sources = new ArrayList<>();
                        String testing;
                        for (int i=0 ; i<imgs.size() ; i++) {
                            testing = imgs.get(i).attr("data-source").toString();
                            sources.add(testing);
                        };
                        Random random = new Random();
                        int url_size = sources.size();
                        final int position = random.nextInt(url_size-1);

                        Picasso.with(view.getContext()).load(sources.get(position)).into(iv);
                    }});
            }
        });

        final String filePath = Environment.getExternalStorageDirectory().toString()+"/Pictures";
        Button downloadBtn = (Button) view.findViewById(R.id.downloadBtn);
        downloadBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(Calendar.getInstance().getTime());
                File imageFile = new File(filePath, timeStamp+".png");
                FileOutputStream fos = null;
                try {
                    fos = new FileOutputStream(imageFile);
                    iv.buildDrawingCache();
                    Bitmap bmp = iv.getDrawingCache();
                    bmp.compress(Bitmap.CompressFormat.PNG, 100, fos);
                    fos.close();
                    iv.destroyDrawingCache();
                    Toast.makeText(view.getContext(), "Done", Toast.LENGTH_SHORT).show();

                    Ion.with(getContext()).load(query).asString().setCallback(new FutureCallback<String>() {
                        @Override
                        public void onCompleted(Exception e, String html) {
                            Document doc = Jsoup.parse(html);
                            Elements imgs = doc.select("img._img");
                            final List<String> sources = new ArrayList<>();
                            String testing;
                            for (int i=0 ; i<imgs.size() ; i++) {
                                testing = imgs.get(i).attr("data-source").toString();
                                sources.add(testing);
                            };
                            Random random = new Random();
                            int url_size = sources.size();
                            final int position = random.nextInt(url_size-1);

                            Picasso.with(view.getContext()).load(sources.get(position)).into(iv);
                        }});

                    return;
                } catch(IOException e) {
                    Log.e("app", e.getMessage());
                    if (fos !=null) {
                        try {
                            fos.close();
                        } catch (IOException e1) {
                            e1.printStackTrace();
                        }
                    }
                }
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

    public void refreshPicture(View view) {
        // Load
        //setImageDrawable ~~~~
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





