package com.example.stockwatch;

import android.net.Uri;
import android.os.AsyncTask;
import android.support.v7.widget.DialogTitle;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;

public class NameDownloader extends AsyncTask<String, Double, String> {

    //Main Activity, URL for getting symbols + names, tag
    private MainActivity mainActivity;
    private String rawURL = "https://api.iextrading.com/1.0/ref-data/symbols";
    private String requestMethod = "GET";

    private static final String TAG = "NameDownloader";


    public NameDownloader(MainActivity ma){
        this.mainActivity = ma;
    }

    @Override
    protected String doInBackground(String... strArgs){
        //Set up raw Uri object with rawURL, stringify the rawURI, set up a string builder object for the buffered reader and input stream
        Uri rawURI = Uri.parse(rawURL);
        String parsedURI = rawURI.toString();
        StringBuilder stringBuilder = new StringBuilder();

        try{
            //Set up connection to URL
            URL url = new URL(parsedURI);
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod(requestMethod);

            Log.d(TAG, "doInBackground: GET method worked");

            //Parse JSON
            InputStream inputStream = con.getInputStream();
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));

            //put the newly parsed JSON into a string
            String currentLine;
            while((currentLine = bufferedReader.readLine()) != null){
                stringBuilder.append(currentLine).append('\n');
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

        //Hashmap for symbol-to-name from generated JSON, let the hashmap be accessible by the main activity
        HashMap<String, String> symbolNameMap = this.parseJSON(str);
        this.mainActivity.generateHashMap(symbolNameMap);
    }

    private HashMap<String, String> parseJSON(String str){
        HashMap<String, String> parsedMap = new HashMap<>();

        try{
            //Make a JSON array with String str
            JSONArray jArray = new JSONArray(str);

            //Store each stock's company name and symbol into a hashmap
            for(int i = 0; i < jArray.length(); i++){
                JSONObject stock = jArray.getJSONObject(i);
                String name = stock.getString("name");
                if(!name.isEmpty()){
                    String symbol = stock.getString("symbol");
                    parsedMap.put(symbol, name);
                    Log.d(TAG, "parseJSON: Successfully put" + symbol + ":" + name + "into hashmap!");
                }
            }
            return parsedMap;
        }
        catch(JSONException e){
            e.printStackTrace();
            return null;
        }
    }
}
