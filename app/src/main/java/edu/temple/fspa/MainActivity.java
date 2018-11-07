package edu.temple.fspa;

import android.graphics.Color;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;

public class MainActivity extends AppCompatActivity {

    ViewPager viewPager;

    //ColorFragment fragments[];
    TabFragment fragments[];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        /*
        fragments = new ColorFragment[4];

        fragments[0] = new ColorFragment();
        fragments[1] = new ColorFragment();
        fragments[2] = new ColorFragment();
        fragments[3] = new ColorFragment(); */

        fragments = new TabFragment[4];
        fragments[0] = new TabFragment();
        fragments[1] = new TabFragment();
        fragments[2] = new TabFragment();
        fragments[3] = new TabFragment();


        viewPager = findViewById(R.id.viewPager);

        FragmentStatePagerAdapter fspa = new FragmentStatePagerAdapter(getSupportFragmentManager()) {
            @Override
            public Fragment getItem(int position) {
                return fragments[position];
            }

            @Override
            public int getCount() {
                return fragments.length;
            }
        };

        viewPager.setAdapter(fspa);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.browser_control, menu);
        return super.onCreateOptionsMenu(menu);
    }
}
