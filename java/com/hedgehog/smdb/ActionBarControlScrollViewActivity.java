/*
 * Copyright 2014 Soichiro Kashima
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.hedgehog.smdb;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;
import android.widget.ViewFlipper;

import com.github.ksoichiro.android.observablescrollview.ObservableScrollView;
import com.github.ksoichiro.android.observablescrollview.ObservableScrollViewCallbacks;
import com.github.ksoichiro.android.observablescrollview.ScrollState;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.util.ArrayList;
import java.util.concurrent.ThreadPoolExecutor;

public class ActionBarControlScrollViewActivity extends Activity implements DownloadTask {

    private CustomPagerAdapter mCustomPagerAdapter;
    private ViewPager mViewPager;
    HorizontalScrollLayout horizontalScrollLayout, horizontalScrollLayoutcast;
    ArrayList<SingleShow> listOfUpcomingshows, listOfStarCast;
    ViewFlipper upcomingViewFlipper, starCastViewFlipper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_actionbarcontrolscrollview);

//        ObservableScrollView scrollView = (ObservableScrollView) findViewById(R.id.scroll);
//        scrollView.setScrollViewCallbacks(this);
        ImageLoader.getInstance().init(ImageLoaderConfiguration.createDefault(this));

        mCustomPagerAdapter = new CustomPagerAdapter(this);

        mViewPager = (ViewPager) findViewById(R.id.pager);
        upcomingViewFlipper = (ViewFlipper) findViewById(R.id.viewFlipperUpcoming);
        starCastViewFlipper = (ViewFlipper) findViewById(R.id.viewFlipperStarCast);
        mViewPager.setAdapter(mCustomPagerAdapter);

        CirclePageIndicator titleIndicator = (CirclePageIndicator) findViewById(R.id.titles);
        titleIndicator.setViewPager(mViewPager);
        horizontalScrollLayout = (HorizontalScrollLayout) findViewById(R.id.horizontalLayout);
        horizontalScrollLayoutcast = (HorizontalScrollLayout) findViewById(R.id.horizontalLayoutcast);
        horizontalScrollLayout.init(this);
        horizontalScrollLayoutcast.init(this);
        repeat();


        new TaskToDownload(this, this, Constants.UPCOMING_SHOWS).executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, Constants.URL_UPCOMING);
        new TaskToDownload(this, this, Constants.STARCAST).executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, Constants.URL_STARCAST);


//        ArrayList<SingleShow> list = new ArrayList<>();
//        SingleShow show = new SingleShow();
//        show.setName("Hello");
//        show.setImageUrl("http://tvmazecdn.com/uploads/images/medium_portrait/53/132622.jpg");
//        list.add(show);
//        show = new SingleShow();
//        show.setName("Hello");
//        show.setImageUrl("http://tvmazecdn.com/uploads/images/medium_portrait/53/132622.jpg");
//        list.add(show);
//        show = new SingleShow();
//        show.setName("Hello");
//        show.setImageUrl("http://tvmazecdn.com/uploads/images/medium_portrait/53/132622.jpg");
//        list.add(show);
//        show = new SingleShow();
//        show.setName("Hello");
//        show.setImageUrl("http://tvmazecdn.com/uploads/images/medium_portrait/53/132622.jpg");
//        list.add(show);
//        horizontalScrollLayout.addItems(list, 300, 450);
//        horizontalScrollLayoutcast.addItems(list, 300, 450);
    }

    private void showNotification() {
        Notification notification = null;
        Intent toLive = new Intent(this, ActionBarControlScrollViewActivity.class);
        PendingIntent pIntent = PendingIntent.getActivity(this, (int) System.currentTimeMillis(), toLive, 0);

        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.JELLY_BEAN) {
            notification = new Notification
                    .Builder(this)
                    .setContentTitle("Time to Check the new Show!")
                    .setContentInfo("")
                    .setSmallIcon(R.mipmap.ic_launcher)
                    .setContentIntent(pIntent)
                    .setPriority(Notification.PRIORITY_MAX)
                    .setDefaults(Notification.DEFAULT_VIBRATE)
                    .setAutoCancel(true)
                    .setOnlyAlertOnce(true)
//                    .setTicker("Show running!")
//                    .addAction(R.mipmap.ic_launcher, "View", pIntent)
                    .build();
        }
        NotificationManager NM = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);

        notification.flags |= Notification.FLAG_AUTO_CANCEL;
        NM.notify(0, notification);
    }

    private void repeat() {
        BroadcastReceiver receiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                showNotification();
            }
        };

        this.registerReceiver(receiver, new IntentFilter("TecxiDriverCheckingForConfirmedBids"));
        PendingIntent pintent = PendingIntent.getBroadcast(this, 0, new Intent("TecxiDriverCheckingForConfirmedBids"), 0);
        AlarmManager manager = (AlarmManager) (this.getSystemService(Context.ALARM_SERVICE));
        manager.setRepeating(AlarmManager.ELAPSED_REALTIME_WAKEUP, 0, 1000 * 60 * 30, pintent);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.info, menu);
        return super.onCreateOptionsMenu(menu);

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.search:
                startActivity(new Intent(this, SearchActivity.class));
                break;
            case R.id.settings:
                startActivity(new Intent(this, SettingsActivity.class));
                break;

        }
        return true;
    }

    @Override
    public void downloadComplete(String response, String source) {
        if (source.equalsIgnoreCase(Constants.UPCOMING_SHOWS)) {
            if (!TextUtils.isEmpty(response)) {
                try {
                    upcomingViewFlipper.setDisplayedChild(1);
                    listOfUpcomingshows = new ArrayList<>();
                    JSONArray array = new JSONArray(response);
                    for (int i = 0; i < 9; i++) {
                        JSONObject object = array.getJSONObject(i);
                        SingleShow show = new SingleShow();
                        if (object.has("_embedded"))
                            show.setName(object.getJSONObject("_embedded").getJSONObject("show").getString("name"));
                        else
                            show.setName(object.getJSONObject("show").getString("name"));
                        try {
                            show.setImageUrl(object.getJSONObject("show").getJSONObject("image").getString("medium"));
                            show.setRuntime("" + object.getString("airdate") + "  /  " + object.getString("airtime"));
                            show.setSummary("" + object.getJSONObject("show").getString("summary"));
                        } catch (JSONException e) {
                            show.setImageUrl("http://tvmazecdn.com/uploads/images/medium_portrait/18/45454.jpg");
                            e.printStackTrace();
                        }

                        listOfUpcomingshows.add(show);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                horizontalScrollLayout.addItems(listOfUpcomingshows, 300, 600, true);
            }
        } else if (source.equalsIgnoreCase(Constants.STARCAST)) {
            if (!TextUtils.isEmpty(response)) {
                try {
                    starCastViewFlipper.setDisplayedChild(1);
                    listOfStarCast = new ArrayList<>();
                    JSONArray array = new JSONArray(response);
                    for (int i = 0; i < 9; i++) {
                        JSONObject object = array.getJSONObject(i);
                        SingleShow show = new SingleShow();
                        show.setName(object.getJSONObject("person").getString("name"));
                        show.setImageUrl(object.getJSONObject("person").getJSONObject("image").getString("medium"));
                        listOfStarCast.add(show);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                horizontalScrollLayoutcast.addItems(listOfStarCast, 300, 450, false);
            }
        }
    }
}
