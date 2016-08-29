package com.hedgehog.smdb;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.ImageLoader;

/**
 * Created by royalpranjal on 18/06/16.
 */
public class SingleActivity extends Activity {


    ImageView bannerImage;


    ImageView smallImage;

    TextView TextName;


    TextView TextTime;

    TextView TextDescription;

    Button heyBro;
    private SingleShow show;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.single_item);
        bannerImage = (ImageView) findViewById(R.id.imageViewLargeImageInView);
        smallImage = (ImageView) findViewById(R.id.imageViewSmallImageInView);
        TextName = (TextView) findViewById(R.id.textViewNameInView);
        TextTime = (TextView) findViewById(R.id.textViewTimeInView);
        TextDescription = (TextView) findViewById(R.id.textViewDescriptionInView);
        show = (SingleShow) getIntent().getSerializableExtra("show");
        ImageLoader.getInstance().displayImage(show.getImageUrl(), bannerImage);
        ImageLoader.getInstance().displayImage(show.getImageUrl(), smallImage);
        heyBro = (Button) findViewById(R.id.HiBroButton);
        heyBro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent sharingIntent = new Intent(Intent.ACTION_SEND);
                sharingIntent.setType("text/plain");
//        sharingIntent.setPackage("com.whatsapp");
                sharingIntent.putExtra(android.content.Intent.EXTRA_TEXT, "Bro! " + show.getName() + " show is running now ,Check it in SMDb\n https://play.google.com/store/apps/details?id=com.hedgehoglab.smdb");
                startActivity(Intent.createChooser(sharingIntent, "Share using"));
            }
        });
        TextName.setText("" + show.getName());
        TextTime.setText("" + show.getRuntime());
        TextDescription.setText("" + Html.fromHtml(show.getSummary()));
    }
}
