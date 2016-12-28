package com.example.q.cs496_android;

import android.app.Activity;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.JavascriptInterface;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.TextView;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;


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
        // Inflate the layout for this fragment
        // Dialog

//        return inflater.inflate(R.layout.fragment_our, container, false);
        View view = inflater.inflate(R.layout.fragment_our, container, false);

        final WebView webView = new WebView(view.getContext());
        webView.getSettings().setJavaScriptEnabled(true);
        webView.addJavascriptInterface(new MyJavascriptInterface(view.getContext()), "HtmlViewer");
        webView.setWebViewClient(new WebViewClient() {
            @Override
            public void onPageFinished(WebView view, String url) {
                webView.loadUrl("javascript:HtmlViewer.showHTML" +
                        "('<head>'+document.getElementsByTagName('html')[0].innerHTML+'</head>');");
            }
        });

        String newUA = "Mozilla/5.0 (X11; U; Linux i686; en-US; rv:1.9.0.4) Gecko/20100101 Firefox/4.0";
        webView.getSettings().setUserAgentString(newUA);

        // Executed at startup & clicked
        webView.loadUrl("https://search.naver.com/search.naver?sm=tab_hty.top&where=image&oquery=%EB%AA%A8%EB%AA%A8&ie=utf8&query=%EB%AA%A8%EB%AA%A8");
        //webView.loadUrl(generate_query(i));

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

class MyJavascriptInterface {
    private Context ctx;
    MyJavascriptInterface(Context ctx) {
        this.ctx = ctx;
    }
    @JavascriptInterface
    public void showHTML(String html) {
        Log.d("Here is", Integer.toString(html.length()));
        Document doc = Jsoup.parse(html);
        Elements imgs = doc.select("img._img");

        List<String> sources = new ArrayList<>();

        for (int i=0 ; i<imgs.size() ; i++) {
            sources.add(imgs.get(i).attr("data-source").toString());
        };

        View view = ((Activity)ctx).getWindow().getDecorView().findViewById(android.R.id.content);

    }
}



