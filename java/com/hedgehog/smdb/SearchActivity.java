package com.hedgehog.smdb;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.Toast;
import android.widget.ViewFlipper;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class SearchActivity extends Activity implements DownloadTask {

    EditText text;
    private ViewFlipper searchViewFlipper;
    private ArrayList<SingleShow> listOfshows;
    GridView gridView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        text = (EditText) findViewById(R.id.editTextSearch);
        searchViewFlipper = (ViewFlipper) findViewById(R.id.searchViewFlipper);
        findViewById(R.id.progress).setVisibility(View.INVISIBLE);
        gridView = (GridView) findViewById(R.id.gridView);
    }

    public void SearchClick(View v) {
        View view = this.getCurrentFocus();
        if (view != null) {
            InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
        String searchString = text.getText().toString().trim();
        if (TextUtils.isEmpty(searchString)) {
            Toast.makeText(this, "Search is empty", Toast.LENGTH_LONG).show();
        } else {
            searchViewFlipper.setDisplayedChild(0);
            new TaskToDownload(this, this, Constants.UPCOMING_SHOWS).execute(Constants.URL_SEARCH + "?q=" + searchString);
        }
    }

    @Override
    public void downloadComplete(String response, String source) {
        if (!TextUtils.isEmpty(response)) {
            if (source.equalsIgnoreCase(Constants.UPCOMING_SHOWS)) {
                try {
                    searchViewFlipper.setDisplayedChild(1);
                    listOfshows = new ArrayList<>();
                    JSONArray array = new JSONArray(response);
                    for (int i = 0; i < 9; i++) {
                        JSONObject object = array.getJSONObject(i);
                        SingleShow show = new SingleShow();
                        show.setName(object.getJSONObject("show").getString("name"));
                        try {
                            show.setImageUrl(object.getJSONObject("show").getJSONObject("image").getString("medium"));

                        } catch (JSONException e) {
                            show.setImageUrl("http://tvmazecdn.com/uploads/images/medium_portrait/18/45454.jpg");
                            e.printStackTrace();
                        }

                        listOfshows.add(show);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                gridView.setAdapter(new CustomAdapter(this, listOfshows));
            }
        }
    }
}
