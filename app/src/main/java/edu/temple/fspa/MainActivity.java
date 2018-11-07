package edu.temple.fspa;

import android.graphics.Color;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    ViewPager viewPager;
    ArrayList<String> categories;
    MyFragmentPageAdapter mPageAdapter;
    TextView urlTextView;
    public int tabCounter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tabCounter = 0;

        viewPager = findViewById(R.id.viewPager);
        urlTextView = findViewById(R.id.websiteEditText);

        categories = new ArrayList<String>();

        List<Fragment> fragments = buildFragments();

        mPageAdapter = new MyFragmentPageAdapter(this,getSupportFragmentManager(), fragments, categories);
        viewPager.setAdapter(mPageAdapter);

        //Add a new Fragment to the list with bundle
        Bundle b = new Bundle();
        b.putInt("position", tabCounter);    // put a thing at position 1
        String title = "steve"; // name it steve
        mPageAdapter.add(TabFragment.class, title, b);
        mPageAdapter.notifyDataSetChanged();

        // Change current website with GO BUTTON
        findViewById(R.id.goButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //TELL FRAGMENT TO CHANGE CURRENT URL
                TabFragment current = (TabFragment) getSupportFragmentManager().findFragmentByTag("android:switcher:" + R.id.viewPager + ":" + viewPager.getCurrentItem());
                current.changeUrl(urlTextView.getText().toString());
                getSupportFragmentManager()
                        .beginTransaction()
                        .detach(current)
                        .attach(current)
                        .commit();
            }
        });

        // History navigation
        findViewById(R.id.backButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TabFragment current = (TabFragment) getSupportFragmentManager().findFragmentByTag("android:switcher:" + R.id.viewPager + ":" + viewPager.getCurrentItem());
                current.historyNav(-1);
            }
        });

        findViewById(R.id.nextButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TabFragment current = (TabFragment) getSupportFragmentManager().findFragmentByTag("android:switcher:" + R.id.viewPager + ":" + viewPager.getCurrentItem());
                current.historyNav(1);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.browser_control, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Bundle b = new Bundle();
        String title;
        switch (item.getItemId()) {
            case R.id.action_previous:
                if(viewPager.getCurrentItem() > 0)
                    viewPager.setCurrentItem(viewPager.getCurrentItem() - 1, true);
                return true;
            case R.id.action_new:
                tabCounter++;
                b.putInt("position", tabCounter);    // put a thing at next position
                title = ("charles #" + tabCounter); // name it charles #
                mPageAdapter.add(TabFragment.class, title, b);
                mPageAdapter.notifyDataSetChanged();
                return true;
            case R.id.action_next:
                if(viewPager.getCurrentItem() < tabCounter + 1)
                    viewPager.setCurrentItem(viewPager.getCurrentItem() + 1, true);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }

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
