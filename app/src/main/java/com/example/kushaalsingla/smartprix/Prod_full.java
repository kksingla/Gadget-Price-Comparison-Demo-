package com.example.kushaalsingla.smartprix;

import android.content.Intent;
import android.graphics.BitmapFactory;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Kushaal Singla on 01-Jul-16.
 */
public class Prod_full extends AppCompatActivity
{
    String url="http://api.smartprix.com/simple/v1?type=product_full&key=NVgien7bb7P5Gsc8DWqc&id=2179&indent=1&id=";

    String jsonString;
    Button b_search;
    EditText et_search;
    TextView head,textView_price,textView_aval;
    ImageView img;
    ListView lv2;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.prod_detail);

        b_search = (Button) findViewById(R.id.b_search_prod);
        et_search = (EditText) findViewById(R.id.et_search_prod);
        head = (TextView) findViewById(R.id.textView_head_full);
        textView_price=(TextView)findViewById(R.id.textView_price);
        textView_aval=(TextView)findViewById(R.id.textView_aval);
        img = (ImageView)findViewById(R.id.img);
        lv2 = (ListView)findViewById(R.id.list3);

        b_search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    if (v.getId() == R.id.b_search_prod && et_search.getText().length() > 0) {
                        Intent i = new Intent("com.ks.Product");
                        i.putExtra("search", et_search.getText().toString());
                        i.putExtra("product", "Search");

                        startActivity(i);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

        Intent i = getIntent();

        url = url + i.getStringExtra("code");

        //Log.d("URL",url);

        FetchDataFromURL(url.replace(" ","%20"));

    }




    private List<Store> GetProductList()
    {
        List<Store> g= new ArrayList<>();

        try
        {
            //Log.d("JSON",jsonString);
            JSONObject jsonObject= new JSONObject(jsonString);

            final JSONObject j1= jsonObject.getJSONObject("request_result");
            JSONArray j2= j1.getJSONArray("prices");

            head.setText(j1.getString(("name")));

            new Thread() {
                public void run() {
                    try {
                        InputStream in = new java.net.URL(j1.getString("img_url")).openStream();
                        img.setImageBitmap( BitmapFactory.decodeStream(in));
                    } catch (Exception e) {
                        //Log.e("ErrorIMG", g.get(position).getImg_url()+e.getMessage());
                        //e.printStackTrace();
                    }

                }
            }.start();

            float price= 0;
            int count=0;

            for(int i=0;i<j2.length();i++)
            {
                Store a=new Store();

                a.setLink(j2.getJSONObject(i).getString("link"));
                a.setStore_url(j2.getJSONObject(i).getString("store_url"));
                a.setStock(j2.getJSONObject(i).getString("stock"));
                a.setLogo(j2.getJSONObject(i).getString("logo"));
                a.setPrice(j2.getJSONObject(i).getString("price"));
                a.setName(j2.getJSONObject(i).getString("name"));

                try {
                    if (i == 0) {
                        price = Float.parseFloat(j2.getJSONObject(i).getString("price"));
                    } else {
                        if(price>Float.parseFloat(j2.getJSONObject(i).getString("price")))
                        {
                            price=Float.parseFloat(j2.getJSONObject(i).getString("price"));
                        }
                    }


                    if(a.getStock().equalsIgnoreCase("In Stock"))
                    {
                        count++;
                    }
                }
                catch (Exception e)
                {
                    e.printStackTrace();
                }
                g.add(a);
            }




            textView_aval.setText("Available at "+count+" stores");
            textView_price.setText("Best Price: Rs. "+price);


        }
        catch (Exception e)
        {
            e.printStackTrace();
        }

        return g;
    }

    private void FetchDataFromURL(String u) {
        if (CheckNetConnection()) {

            final String u1 = u;

            Thread a = new Thread() {

                public void run() {
                    try {


                        URL url = new URL(u1);


                        HttpURLConnection httpConn = (HttpURLConnection) url.openConnection();
                        httpConn.connect();

                        int resCode = httpConn.getResponseCode();

                        if (resCode == HttpURLConnection.HTTP_OK) {
                            InputStream in = httpConn.getInputStream();

                            BufferedReader reader = new BufferedReader(new InputStreamReader(in));
                            StringBuilder sb = new StringBuilder();

                            String line = null;
                            while ((line = reader.readLine()) != null) {
                                sb.append(line);
                            }

                            in.close();

                            jsonString = sb.toString();


                        } else {
                            Log.e("Connection Error: ", "Unable to make Connection with URL ");
                            jsonString="";
                        }

                    } catch (Exception e) {
                        Log.e("Connection Error: ", "Unable to fetch data from URL " + e.toString());
                        jsonString="";
                        e.printStackTrace();
                    }
                }
            };

            a.start();

        } else {
            Log.e("Connection Error: ", "Internet is not working");
        }


        while (jsonString == null) {
            //Waiting

            if(jsonString=="")
            {

                break;
            }

        }
        if(jsonString=="")
        {
            Toast.makeText(getApplicationContext(),"Unable to fetch data",Toast.LENGTH_LONG).show();
            return;
        }

        try {
            final List<Store> g = GetProductList();

            CustomAdapterFull a = new CustomAdapterFull(getApplicationContext(), g);

            lv2.setAdapter(a);


            lv2.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                }

            });

        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    private boolean CheckNetConnection() {
        ConnectivityManager connec = (ConnectivityManager) getSystemService(getBaseContext().CONNECTIVITY_SERVICE);

        if (connec.getNetworkInfo(0).getState() == android.net.NetworkInfo.State.CONNECTED ||

                connec.getNetworkInfo(1).getState() == android.net.NetworkInfo.State.CONNECTED) {

            return true;
        } else {
            return false;
        }
    }
}
