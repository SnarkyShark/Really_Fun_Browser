package edu.temple.fspa;


import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.Random;


/**
 * A simple {@link Fragment} subclass.
 */
public class ColorFragment extends Fragment {


    public ColorFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_color, container, false);

        int i;

        int colors[] = {Color.RED, Color.BLACK, Color.BLUE, Color.GREEN, Color.MAGENTA};

        i = new Random().nextInt(5);

        v.setBackgroundColor(colors[i]);

        v.findViewById(R.id.button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setAction(Intent.ACTION_VIEW);

                // specify some information
                // URI - Uniform Resource Identifier
                // URL is a subset of URI - scheme, server, host, path, filename
                intent.setData(Uri.parse("https://www.temple.edu"));

                // try to resolve
                // but we'll just

                startActivity(intent);

            }
        });
        return v;
    }

}
