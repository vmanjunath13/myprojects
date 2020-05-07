package com.example.vaish.as5;


import android.net.Uri;
import android.os.AsyncTask;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;


public class NewsSourceDownloader extends AsyncTask<String, Void, String>
{
    String str_pass;
    String str_yahoo, str_facebook;
    String url2use = null;
    Uri URL_data_uri = null;
    private static final String TAG = "NewsSourceDownloader";
    private MainActivity mainActivity;
    private final String apiresourcekey ="&apiKey=9129d60e298844f7b02dd93944f06ee1";
    private final String APIKEY = "https://newsapi.org/v1/sources?language=en&country=us&category=";
    ArrayList<String> news_resource_category = new ArrayList<>();
    ArrayList<SourceGetSet> news_resource_list = new ArrayList<>();
    HashMap<Integer, SourceGetSet> hash_map = new HashMap<>();
    ArrayList<String> news_resource_category1 = new ArrayList<>();
    ArrayList unique_List = new ArrayList();

    public NewsSourceDownloader(MainActivity ma)
    {
        mainActivity = ma;
    }


    @Override
    protected void onPreExecute()
    {
        Toast.makeText(mainActivity, "Loading NewsSource Data...", Toast.LENGTH_SHORT).show();
    }

    @Override
    protected String doInBackground(String... params)
    {
        StringBuilder sb_obj = new StringBuilder();
        String str_line, s_11;
        str_facebook = null;
        str_yahoo = null;
        URL_data_uri = null;
        url2use = null;
        if(params[0].equals("all"))
        {
            URL_data_uri = Uri.parse(APIKEY + apiresourcekey);
            url2use = URL_data_uri.toString();

        }
        else
        {
            URL_data_uri = Uri.parse(APIKEY + params[0] + apiresourcekey);
            url2use = URL_data_uri.toString();

        }

        try
        {
            URL url_obj = new URL(url2use);
            HttpURLConnection conn = (HttpURLConnection) url_obj.openConnection();
            conn.setRequestMethod("GET");
            InputStream is_obj = conn.getInputStream();
            BufferedReader reader_obj = new BufferedReader((new InputStreamReader(is_obj)));
            while ((str_line = reader_obj.readLine()) != null)
            {
                sb_obj.append(str_line).append('\n');
            }
        }
        catch (FileNotFoundException e)
        {
            ArrayList<String> news_source = new ArrayList<>();
            return String.valueOf(news_source);
        }
        catch (Exception e)
        {
            ArrayList<String> news_source = new ArrayList<>();
            return String.valueOf(news_source);
        }

        ArrayList<SourceGetSet> news_source = parseJSON(sb_obj.toString());
        return String.valueOf(news_source);
    }

    private ArrayList<SourceGetSet> parseJSON(String s)
    {
        String cid = null;
        String cname = null;
        String curl = null;
        String ccategory = null;
        try
        {
            JSONObject jr1 = new JSONObject(s);
            JSONArray response1 = jr1.getJSONArray("sources");

            for(int i =0; i<response1.length(); i++)
            {
                cid = null;
                cname = null;
                curl = null;
                ccategory = null;
                {
                    JSONObject jb1 = response1.getJSONObject(i);
                    cid = jb1.getString("id");
                    cname = jb1.getString("name");
                    curl = jb1.getString("url");
                    ccategory = jb1.getString("category");
                }
                news_resource_list.add(new SourceGetSet(cid, cname, curl, ccategory));
                news_resource_category.add(ccategory);
            }
            Set<String> hashmap = new HashSet<>();
            hashmap.addAll(news_resource_category);
            news_resource_category.clear();
            news_resource_category1.addAll(hashmap);
            return news_resource_list;

        }
        catch (JSONException e)
        {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    protected void onPostExecute(String s)
    {
        Toast.makeText(mainActivity, "Loading Article Data..", Toast.LENGTH_SHORT).show();
        if(news_resource_list.size() > 0)
        {
            mainActivity.setSources(news_resource_list, news_resource_category1);
        }
    }
}
