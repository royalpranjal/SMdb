package com.hedgehog.smdb;

import android.app.ActionBar;
import android.app.Activity;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.Window;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class MainActivity extends Activity {
    private ArrayAdapter<String> adapter;
    private CustomPagerAdapter mCustomPagerAdapter;
    private ViewPager mViewPager;
    HorizontalScrollLayout layout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
        mCustomPagerAdapter = new CustomPagerAdapter(this);

        mViewPager = (ViewPager) findViewById(R.id.pager);

        mViewPager.setAdapter(mCustomPagerAdapter);
        ArrayList<SingleShow> list = new ArrayList<>();
        SingleShow show = new SingleShow();
        show.setName("Hello");
        show.setImageUrl("http://tvmazecdn.com/uploads/images/medium_portrait/53/132622.jpg");
        list.add(show);
        show = new SingleShow();
        show.setName("Hello");
        show.setImageUrl("http://tvmazecdn.com/uploads/images/medium_portrait/53/132622.jpg");
        list.add(show);
        show = new SingleShow();
        show.setName("Hello");
        show.setImageUrl("http://tvmazecdn.com/uploads/images/medium_portrait/53/132622.jpg");
        list.add(show);
        show = new SingleShow();
        show.setName("Hello");
        show.setImageUrl("http://tvmazecdn.com/uploads/images/medium_portrait/53/132622.jpg");
        list.add(show);

    }
}
