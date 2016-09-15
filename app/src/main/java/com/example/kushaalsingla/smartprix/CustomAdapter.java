package com.example.kushaalsingla.smartprix;

import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.support.v4.app.ActivityCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;


/**
 * Created by Kushaal Singla on 01-Jul-16.
 */
public class CustomAdapter  extends BaseAdapter
{
    List<Gadget> g= new ArrayList<>();
    Context context;
    private static LayoutInflater inflater=null;

    public CustomAdapter(Context mainActivity, List<Gadget> data)
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
        TextView tv1;
        ImageView img;
    }

    @Override
    public View getView(final int position, final View convertView, ViewGroup parent) {
        // TODO Auto-generated method stub
         final Holder holder=new Holder();
        View rowView;
        rowView = inflater.inflate(R.layout.gadgetview, null);
        try{

        holder.tv=(TextView) rowView.findViewById(R.id.textViewName);
        holder.tv1=(TextView) rowView.findViewById(R.id.textViewRs);
        holder.img=(ImageView) rowView.findViewById(R.id.imageViewIcon);

        holder.tv.setText(g.get(position).getName());

        holder.tv1.setText(g.get(position).getPrice());


            new Thread() {
                public void run() {
                    try {
                        InputStream in = new java.net.URL(g.get(position).getImg_url()).openStream();
                        holder.img.setImageBitmap( BitmapFactory.decodeStream(in));
                    } catch (Exception e) {
                        //Log.e("ErrorIMG", g.get(position).getImg_url()+e.getMessage());
                        //e.printStackTrace();
                    }

                }
            }.start();



        rowView.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent("com.ks.Pfull");
                i.putExtra("code", g.get(position).getId());
                i.putExtra("name", g.get(position).getName());

                i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(i);
            }
        });
        }
        catch (Exception e)
        {
            Log.e("ErrorNAME", e.getMessage());
            e.printStackTrace();
        }
        return rowView;
    }

}