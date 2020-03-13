package com.example.stockwatch;

import android.annotation.SuppressLint;
import android.net.Uri;
import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Random;

public class StockDownloader extends AsyncTask<String, Double, String> {
    //Main Activity, URL for getting symbols + names, tag
    @SuppressLint("StaticFieldLeak")
    private MainActivity mainActivity;
    private String rawURL = "https://cloud.iexapis.com/stable/stock/";
    private String requestMethod = "GET";
    private static final String TAG = "StockDownloader";

    //Symbol and name for stocks, pos for keeping track of list position
    private String symbol;
    private String name;
    private int position;

    public StockDownloader(MainActivity ma, int pos){
        this.mainActivity = ma;
        this.position = pos;
    }

    @Override
    protected String doInBackground(String... strArgs){

        //Init Stock symbol and name
        this.symbol = strArgs[0];
        this.name = strArgs[1];

        // URI/URL creation and StringBuilder
        String apiToken = "pk_b765020aad64429eb099e93adbbd1c03";
        rawURL = rawURL + strArgs[0] + "/quote?token=" + apiToken;
        Log.d(TAG, "doInBackground: rawURL" + rawURL);
        Uri rawURI = Uri.parse(rawURL);
        String parsedURI = rawURI.toString();
        StringBuilder stringBuilder = new StringBuilder();

        try{
            //set up a connection to URL
            URL url = new URL(parsedURI);
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod(requestMethod);

            Log.d(TAG, "doInBackground: GET method worked!");

            //Parse JSON
            InputStream inputStream = con.getInputStream();
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));

            //put newly parsed JSON into a string
            String currentLine;
            while((currentLine =  bufferedReader.readLine()) != null){
                stringBuilder.append(currentLine).append("\n");
            }
            return stringBuilder.toString();
        }
        catch(MalformedURLException e){
            e.printStackTrace();
            return null;
        }
        catch(IOException e){
            e.printStackTrace();
            return null;
        }
    }

    @Override
    protected void onPostExecute(String str){
        //super method
        super.onPostExecute(str);

        try{
            //Init JSON Object jo
            JSONObject jo = new JSONObject(str);

            //Get all the relevant stock data latestPrice, change, changePercent
            double latestPrice = jo.getDouble("latestPrice");
            double change = jo.getDouble("change");
            double changePercent = jo.getDouble("changePercent");

            //Make that new stock object
            Stock s = new Stock(this.symbol, this.name, latestPrice, change, changePercent);

            //If stock exists UPDATE STOCK s
            if(this.position > -1){
                this.mainActivity.updateStock(s, this.position);
            }
            //If stock doesn't exist ADD STOCK s
            else{
                this.mainActivity.addStock(s);
            }
        }
        catch(JSONException e){
            e.printStackTrace();
        }
    }
}
