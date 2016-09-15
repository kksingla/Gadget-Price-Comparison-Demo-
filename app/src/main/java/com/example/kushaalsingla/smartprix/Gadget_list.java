package com.example.kushaalsingla.smartprix;

import android.app.PendingIntent;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
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
 * Created by Kushaal Singla on 30-Jun-16.
 */
public class Gadget_list extends AppCompatActivity
{

    int preLast=0;
    String jsonString;
    ListView lv2;
    TextView t_head;
    Button b_search;
    EditText et_search;

    String prod;

    String key="NVgien7bb7P5Gsc8DWqc";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.category);

        Intent i = getIntent();


        lv2 = (ListView) findViewById(R.id.listView2);

        t_head = (TextView) findViewById(R.id.textView_head);
        b_search = (Button) findViewById(R.id.b_search);
        et_search = (EditText) findViewById(R.id.et_search);
        final String prod = i.getStringExtra("product");
        final String search = i.getStringExtra("search");

        String url = "";
        if (search != null) {
            url = "http://api.smartprix.com/simple/v1?type=search&key=" + key + "&q=" + search + "&indent=1&rows=20";
        } else {

            url = "http://api.smartprix.com/simple/v1?type=search&key=" + key + "&category=" + prod + "&indent=1&rows=20";
        }

        t_head.setText(prod);
        FetchDataFromURL(url.replace(" ","%20"));


        b_search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {

                    if (v.getId() == R.id.b_search && et_search.getText().length() > 0) {
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
    }






    private List<Gadget> GetProductList()
    {
        List<Gadget> g= new ArrayList<>();

        try
        {
            JSONObject jsonObject= new JSONObject(jsonString);

            JSONObject j1= jsonObject.getJSONObject("request_result");
            JSONArray j2= j1.getJSONArray("results");


            for(int i=0;i<j2.length();i++)
            {
                Gadget a=new Gadget();

                a.setId(j2.getJSONObject(i).getString("id"));
                a.setCategory(j2.getJSONObject(i).getString("category"));
                a.setName(j2.getJSONObject(i).getString("name"));
                a.setImg_url(j2.getJSONObject(i).getString("img_url"));
                a.setBrand(j2.getJSONObject(i).getString("brand"));
                a.setPrice(j2.getJSONObject(i).getString("price"));
                g.add(a);
            }





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
            final List<Gadget> g = GetProductList();

            CustomAdapter a = new CustomAdapter(getApplicationContext(), g);

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
