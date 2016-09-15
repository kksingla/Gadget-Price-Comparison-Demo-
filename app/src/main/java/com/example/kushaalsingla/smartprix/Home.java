package com.example.kushaalsingla.smartprix;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class Home extends AppCompatActivity {


    ConnectivityManager connec;
    String jsonString;
    ListView lv;

    Button b_search;
    EditText et_search;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        FetchDataFromURL();

        lv = (ListView) findViewById(R.id.listView);

        b_search = (Button) findViewById(R.id.b_search_home);
        et_search = (EditText) findViewById(R.id.et_search_home);


        while (jsonString == null) {
            //Waiting

        }

        final List<String> arr = GetMenuList();
        ArrayAdapter a = new ArrayAdapter(this, android.R.layout.simple_list_item_1, arr);

        lv.setAdapter(a);

        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Intent i = new Intent("com.ks.Product");
                i.putExtra("product", arr.get(position));
                startActivity(i);

            }
        });

        try{

        b_search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {

                    if (v.getId() == R.id.b_search_home && et_search.getText().length() > 0) {
                        Intent i = new Intent("com.ks.Product");
                        i.putExtra("search", et_search.getText().toString());
                        i.putExtra("product", "Search");

                        startActivity(i);
                    }
                }
                catch (Exception e)
                {
                    e.printStackTrace();
                }
            }
        });}
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }



    private List<String> GetMenuList() {
        List<String> arr = new ArrayList<String>();
        try {
            JSONObject jObject = new JSONObject(jsonString);
            JSONArray jsonArray = jObject.getJSONArray("request_result");


            for (int i = 0; i < jsonArray.length(); i++) {
                arr.add(jsonArray.getString(i));
            }


        } catch (Exception e) {
            e.printStackTrace();
        }
        return arr;
    }


    private void FetchDataFromURL() {
        if (CheckNetConnection()) {

            Thread a = new Thread() {

                public void run() {
                    try {

                        String link = "http://api.smartprix.com/simple/v1?type=categories&key=NVgien7bb7P5Gsc8DWqc&indent=1";
                        URL url = new URL(link);


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
                        }

                    } catch (Exception e) {
                        Log.e("Connection Error: ", "Unable to fetch data from URL " + e.toString());
                        e.printStackTrace();
                    }
                }
            };

            a.start();

        } else {
            Log.e("Connection Error: ", "Internet is not working");
        }
    }

    private boolean CheckNetConnection() {
        connec = (ConnectivityManager) getSystemService(getBaseContext().CONNECTIVITY_SERVICE);

        if (connec.getNetworkInfo(0).getState() == android.net.NetworkInfo.State.CONNECTED ||

                connec.getNetworkInfo(1).getState() == android.net.NetworkInfo.State.CONNECTED) {

            return true;
        } else {
            return false;
        }
    }

}

