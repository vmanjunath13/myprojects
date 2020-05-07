package com.example.vaish.as5;

import android.net.Uri;
import android.os.AsyncTask;
import android.util.Log;
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



class NewsArticleDownloader extends AsyncTask<String, Void, String>
{
    String id_1;
    String var_x, var_y;
    String url2use = null;
    Uri data_gener = null;
    private NewService obj_news_service;
    ArrayList<ArticleData> arr_list_art_list = new ArrayList<>();
    private final String dataURL = "https://newsapi.org/v1/articles?source=";
    private static final String TAG = "NewsArticleDownloader";
    private final String apikey ="&apiKey=9129d60e298844f7b02dd93944f06ee1";


    public NewsArticleDownloader(NewService n_serv_par, String id_par)
    {
        obj_news_service = n_serv_par;
        id_1 = id_par;
    }

    @Override
    protected void onPreExecute()
    {
        Toast.makeText(obj_news_service, "Loading NewsArticle Data...", Toast.LENGTH_SHORT).show();
    }

    @Override
    protected String doInBackground(String... params)
    {
        var_y = null;
        var_x = null;
        data_gener = null;
        url2use = null;
        data_gener = Uri.parse(dataURL + id_1 + apikey);
        url2use = data_gener.toString();
        StringBuilder sb_obj = new StringBuilder();
        String str_line, s_11;
        try
        {
            URL url_obj = new URL(url2use);
            HttpURLConnection http_conn = (HttpURLConnection) url_obj.openConnection();
            http_conn.setRequestMethod("GET");
            InputStream is_obj = http_conn.getInputStream();
            BufferedReader buff_reader = new BufferedReader((new InputStreamReader(is_obj)));
            while ((str_line = buff_reader.readLine()) != null)
            {
                sb_obj.append(str_line).append('\n');
            }
        }
        catch (FileNotFoundException e)
        {
            ArrayList<String> arr_list_news_source = new ArrayList<>();
            Log.e(TAG, "DoException: ", e);
            return String.valueOf(arr_list_news_source);
        }
        catch (Exception e)
        {
            ArrayList<String> arr_list_news_source = new ArrayList<>();
            Log.e(TAG, "DoException: ", e);
            return String.valueOf(arr_list_news_source);
        }
        ArrayList<ArticleData> arr_list_news_article = parseJSON(sb_obj.toString());
        return String.valueOf(arr_list_news_article);
    }

    private ArrayList<ArticleData> parseJSON(String s)
    {
        String author = null;
        String title = null;
        String description = null;
        String urlToImage = null;
        String publishedAt = null;
        String url = null;
        try
        {
            JSONObject jr1 = new JSONObject(s);
            JSONArray response1 = jr1.getJSONArray("articles");
            for(int i =0; i<response1.length(); i++)
            {

                {
                    JSONObject job = response1.getJSONObject(i);
                    author = job.getString("author");
                    title = job.getString("title");
                    description = job.getString("description");
                    urlToImage = job.getString("urlToImage");
                    publishedAt = job.getString("publishedAt");
                    url = job.getString("url");
                }
                arr_list_art_list.add(new ArticleData(author, title, description, urlToImage, publishedAt, url));
            }
            return arr_list_art_list;
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
        if(arr_list_art_list.size() > 0)
        {
            obj_news_service.setArticles(arr_list_art_list);
        }
    }
}
