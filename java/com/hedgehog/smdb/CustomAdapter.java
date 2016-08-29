package com.hedgehog.smdb;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.ArrayList;

/**
 * Created by royalpranjal on 18/06/16.
 */
public class CustomAdapter extends BaseAdapter {


    Context context;
    ArrayList<SingleShow> list;
    private static LayoutInflater inflater = null;

    public CustomAdapter(Activity mContext, ArrayList<SingleShow> list) {
        // TODO Auto-generated constructor stub

        context = mContext;
        this.list = list;

        inflater = (LayoutInflater) context.
                getSystemService(Context.LAYOUT_INFLATER_SERVICE);

    }

    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        // TODO Auto-generated method stub
        return position;
    }

    @Override
    public long getItemId(int position) {
        // TODO Auto-generated method stub
        return position;
    }

    public class Holder {
        TextView tv;
        ImageView img;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        // TODO Auto-generated method stub
        Holder holder = new Holder();
        View rowView;

        rowView = inflater.inflate(R.layout.single_item_layout, null);
        holder.tv = (TextView) rowView.findViewById(R.id.single_item_title);
        holder.img = (ImageView) rowView.findViewById(R.id.single_item_imageView);

        holder.tv.setText(list.get(position).getName());
        ImageLoader.getInstance().displayImage(list.get(position).getImageUrl(), holder.img);

        
        return rowView;
    }

}