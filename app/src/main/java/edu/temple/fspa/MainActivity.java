package edu.temple.fspa;

import android.graphics.Color;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    ViewPager viewPager;
    ArrayList<String> categories;
    MyFragmentPageAdapter mPageAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        viewPager = findViewById(R.id.viewPager);

        List<Fragment> fragments = buildFragments();
        categories = new ArrayList<String>();
        categories.add("the best");
        mPageAdapter = new MyFragmentPageAdapter(this,getSupportFragmentManager(), fragments, categories);
        viewPager.setAdapter(mPageAdapter);

        //Add a new Fragment to the list with bundle
        Bundle b = new Bundle();
        b.putInt("position", 1);    // put a thing at position 1
        String title = "steve"; // name it steve
        mPageAdapter.add(TabFragment.class, title, b);
        mPageAdapter.notifyDataSetChanged();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.browser_control, menu);
        return super.onCreateOptionsMenu(menu);
    }

    private List<Fragment> buildFragments() {
        List<android.support.v4.app.Fragment> fragments = new ArrayList<Fragment>();
        for(int i = 0; i<categories.size(); i++) {
            Bundle b = new Bundle();
            b.putInt("position", i);
            fragments.add(Fragment.instantiate(this,TabFragment.class.getName(),b));
        }

        return fragments;
    }
}
