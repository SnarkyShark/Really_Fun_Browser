package edu.temple.fspa;


import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.EditText;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 */
public class TabFragment extends Fragment {

    public static String URL_KEY = "website";

    WebView webView;
    EditText t;
    String currentUrl;

    public TabFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Bundle bundle = getArguments();

        this.currentUrl = bundle.getString(URL_KEY);
    }

    public static TabFragment newInstance(String url){
        TabFragment tabFragment = new TabFragment();

        Bundle bundle = new Bundle();
        bundle.putString(TabFragment.URL_KEY, url);

        return tabFragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_tab, container, false);

        webView = v.findViewById(R.id.webView);
        t = getActivity().findViewById(R.id.websiteEditText);

        // just because you put a webview in your app doesn't mean it will automatically respond
        // this is normally more involved
        webView.setWebViewClient(new WebViewClient() {
            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                //currentUrl = webView.getUrl();
            }

            @Override
            public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
                //visitedUrlStack.add(request.getUrl().toString());
                return super.shouldOverrideUrlLoading(view, request);
            }

            // how you get to follow links!!!!!!
            // set it as an instance variable in your fragment
            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                super.onPageStarted(view, url, favicon);
                /*Message msg = Message.obtain();
                msg.obj = currentUrl;
                loadHandler.sendMessage(msg); */
            }
        });

        new Thread() {
            public void run() {
                try {
                    URL url = new URL(currentUrl);

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

    public void changeUrl(String url) {
        currentUrl = url;
    }

    public void intentChange(String url) {
        currentUrl = url;
        webView.loadUrl(currentUrl);
    }

    public void historyNav(int direction) {
        if (direction < 0 && webView.canGoBack()) {
            webView.goBack();
        }
        else if (direction >=0 && webView.canGoForward()) {
            webView.goForward();
        }
    }

    Handler responseHandler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message msg) {
            //  ((TextView) findViewById(R.id.displayTextView)).setText((String) msg.obj);
            webView.loadData((String) msg.obj, "text/html", "UTF-8");
            return false;
        }
    });

    Handler loadHandler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message msg) {
            if (t != null)
                t.setText((String) msg.obj);
            return false;
        }
    });
}
