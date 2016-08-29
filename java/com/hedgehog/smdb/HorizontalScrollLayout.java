package com.hedgehog.smdb;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ViewFlipper;

import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.ArrayList;

public class HorizontalScrollLayout extends LinearLayout implements View.OnClickListener {

    Activity mActivity;
    TextView hoverTitleText, hoverDesctext;
    ArrayList<SingleShow> list;
    //    ListenerForTask listenerForTask;
//    HotLinksScrollView hotLinksScrollView;
    private ArrayList<String> listOfAssetsImages;
    private String phase;

    public HorizontalScrollLayout(Context context) {
        super(context);
    }

    public HorizontalScrollLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public HorizontalScrollLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public void init(Activity mActivity) {
        this.mActivity = mActivity;
    }

    public void addItems(ArrayList<SingleShow> list, int width, int height, boolean b) {
        clearView();
        this.list = list;
        for (int i = 0; i < list.size(); i++) {
            View rootView = LayoutInflater.from(mActivity).inflate(R.layout.single_item_layout, null);
            RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(width, height);
            rootView.setLayoutParams(params);
            TextView textView = (TextView) rootView.findViewById(R.id.single_item_title);
            textView.setText(list.get(i).getName());
            ImageView image = (ImageView) rootView.findViewById(R.id.single_item_imageView);
//            String iconurl = list.get(i).getImageUrl();
//            String imagename = iconurl.substring(iconurl.lastIndexOf("/") + 1).trim();

            ImageLoader.getInstance().displayImage(list.get(i).getImageUrl(), image);
//            rootView.setClickable(true);
//            rootView.setFocusableInTouchMode(true);
//            rootView.setFocusable(true);
//            rootView.setOnHoverListener(this);
//            rootView.setOnFocusChangeListener(this);
//            rootView.setTag(i);
//            rootView.setOnClickListener(this);
            image.setTag(i);
            if (b)
                image.setOnClickListener(this);
            this.addView(rootView);
        }
    }

    private void clearView() {
        this.removeAllViews();
    }

    @Override
    public void onClick(View v) {
        int i = (int) v.getTag();
        Log.d("position tag", "" + i);
        SingleShow show = list.get(i);
        Intent intent = new Intent(mActivity, SingleActivity.class);
        intent.putExtra("show", show);
        mActivity.startActivity(intent);
    }
}