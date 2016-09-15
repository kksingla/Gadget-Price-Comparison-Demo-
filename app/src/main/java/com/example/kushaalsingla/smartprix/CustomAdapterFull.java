package com.example.kushaalsingla.smartprix;

import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.support.v4.app.ActivityCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.InputStream;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;


/**
 * Created by Kushaal Singla on 01-Jul-16.
 */
public class CustomAdapterFull extends BaseAdapter
{
    List<Store> g= new ArrayList<>();
    Context context;
    private static LayoutInflater inflater=null;

    public CustomAdapterFull(Context mainActivity, List<Store> data)
    {
        // TODO Auto-generated constructor stub
        g=data;
        context=mainActivity;
        inflater = ( LayoutInflater )context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        return g.size();
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

    public class Holder
    {
        TextView tv;
        Button buy;
        ImageView img;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        // TODO Auto-generated method stub
         final Holder holder=new Holder();
        View rowView;
        rowView = inflater.inflate(R.layout.buy, null);
        try{

        holder.buy=(Button) rowView.findViewById(R.id.b_buy);
        holder.tv=(TextView) rowView.findViewById(R.id.tv_name);
        holder.img=(ImageView) rowView.findViewById(R.id.img_vendor);

        holder.tv.setText(g.get(position).getPrice());

        holder.buy.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent();
                i.setData(Uri.parse(g.get(position).getLink()));
                i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(i);
            }
        });


            new Thread() {
                public void run() {
                    try {
                        InputStream in = new java.net.URL(g.get(position).getLogo()).openStream();
                        holder.img.setImageBitmap( BitmapFactory.decodeStream(in));
                    } catch (Exception e) {
                        //Log.e("ErrorIMG", g.get(position).getImg_url()+e.getMessage());
                        //e.printStackTrace();
                    }

                }
            }.start();





        }
        catch (Exception e)
        {
            Log.e("ErrorNAME", e.getMessage());
            e.printStackTrace();
        }
        return rowView;
    }

}