package edu.temple.fspa;


import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;


/**
 * A simple {@link Fragment} subclass.
 */
public class TabFragment extends Fragment {

    WebView webView;

    public TabFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_tab, container, false);

        webView = v.findViewById(R.id.webView);

        // just because you put a webview in your app doesn't mean it will automatically respond
        // this is normally more involved
        webView.setWebViewClient(new WebViewClient() {
            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
            }

            // how you get to follow links!!!!!!
            // set it as an instance variable in your fragment
            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                super.onPageStarted(view, url, favicon);
            }
        });

        new Thread() {
            public void run() {
                try {
                    URL url = new URL("https://www.temple.edu");

                    BufferedReader reader = new BufferedReader(
                            new InputStreamReader(
                                    url.openStream()));

                    StringBuilder sb = new StringBuilder();
                    String tmpString;

                    while ((tmpString = reader.readLine()) != null) {
                        sb.append(tmpString);
                    }

                    Message msg = Message.obtain();
                    msg.obj = sb.toString();
                    responseHandler.sendMessage(msg);
                } catch (Exception e)

                {
                    e.printStackTrace();
                }
            }

        }.start();

        return v;
    }

    Handler responseHandler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message msg) {
            //  ((TextView) findViewById(R.id.displayTextView)).setText((String) msg.obj);
            webView.loadData((String) msg.obj, "text/html", "UTF-8");

            return false;
        }
    });

}
