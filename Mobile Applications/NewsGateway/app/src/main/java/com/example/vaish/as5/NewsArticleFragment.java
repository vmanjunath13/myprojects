package com.example.vaish.as5;


import android.content.Intent;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import com.squareup.picasso.Picasso;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class NewsArticleFragment extends Fragment
{
    public static final String str_a = "str_a";
    final String TAG = "NewsArticleFragment";
    public static final String str_c = "str_c";
    public static final String str_b = "str_b";
    String final_date;
    public static final String str_d = "str_d";
    public static final String str_f = "str_f";
    public static final String str_e = "str_e";
    String d_2 = "";
    public static final String str_g = "str_g";
    private TextView written_by_name, news_title, news_Descp, news_pageno;
    NetworkInfo act_netw_info;
    Fragment my_frag;
    private ImageView news_image;


    public static final NewsArticleFragment newInstance(String msg, String msg1, String msg2, String msg3, String msg4, String msg5, String msg6)
    {
        Bundle obj_bdl = new Bundle(6);
        NewsArticleFragment news_art_frag = new NewsArticleFragment();

        if(msg1 != null) {
            obj_bdl.putString(str_b, msg1);
        }
        if(msg != null) {
            obj_bdl.putString(str_a, msg);
        }
        if(msg3 != null) {
            obj_bdl.putString(str_d, msg3);
        }

        if(msg2 != null) {
            obj_bdl.putString(str_c, msg2);
        }
        if(msg5 != null) {
            obj_bdl.putString(NewsArticleFragment.str_f, msg5);
        }

        if(msg4 != null) {
            obj_bdl.putString(str_e, msg4);
        }

        if(msg6 != null) {
            obj_bdl.putString(str_g, msg6);}
        news_art_frag.setArguments(obj_bdl);
        return news_art_frag;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        onRestoreInstanceStae(savedInstanceState);
        final String msg = getArguments().getString(str_a);;
        String msg1 = getArguments().getString(str_b);
        String msg3 = getArguments().getString(str_d);
        String msg2 = getArguments().getString(str_c);
        String msg4 = getArguments().getString(str_e);
        final String msg5 = getArguments().getString(str_f);
        String msg6 = getArguments().getString(str_g);
        msg.trim();
        msg1.trim();
        msg2.trim();
        msg3.trim();
        msg6.trim();

        setRetainInstance(true);
        View v = inflater.inflate(R.layout.myfragment_layout, container, false);
        written_by_name = (TextView)v.findViewById(R.id.Authorname);
        news_image = (ImageView) v.findViewById(R.id.positionphoto);
        news_title = (TextView)v.findViewById(R.id.Title);
        news_Descp = (TextView)v.findViewById(R.id.Description);
        news_pageno = (TextView)v.findViewById(R.id.pageno);

        try {
            SimpleDateFormat obj_simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssZ", Locale.getDefault());
            Date created_Date = obj_simpleDateFormat.parse(msg4.replaceAll("Z$", "+0000"));

            obj_simpleDateFormat = new SimpleDateFormat("EEE MMM dd HH:mm", Locale.getDefault());
            String new_Date = obj_simpleDateFormat.format(created_Date);

            String [] d_1 = new_Date.split(" ");
            final_date = d_1[1]+" "+ d_1[2]+", 2017 "+ d_1[3];
            d_2 = final_date;
        }catch(Exception pe) {
            Log.d(TAG, "onCreateView: Inside exception");
        }

        if(msg2.trim()!= null && final_date != null)
        {
            written_by_name.setText(final_date + "\n\n\n" + msg2);
        }
        else if(msg2.trim() == null && final_date != null)
        {
            written_by_name.setText(final_date);
        }
        else if(msg2.trim()!= null && final_date == null)
        {
            written_by_name.setText("");
        }
        else if (msg2.trim()== null && final_date == null)
        {
            written_by_name.setText("No Information Available!");
        }
        news_pageno.setText(msg6);

        if (msg1 != null) {

            final String img_url = msg1;

            Picasso picasso_build = new Picasso.Builder(this.getContext()).listener(new Picasso.Listener()
            {
                @Override
                public void onImageLoadFailed(Picasso picasso, Uri uri, Exception exception)
                { // Here we try https if the http news_image attempt failed
                    final String changed_Url = img_url.replace("http:", "https:");
                    picasso.load(changed_Url).error(R.drawable.brokenimage)
                            .placeholder(R.drawable.placeholder).into(news_image);
                }
            }).build();
            picasso_build.load(img_url).error(R.drawable.brokenimage)
                    .placeholder(R.drawable.placeholder).into(news_image);
            news_image.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v)
                {
                    Uri uri_parse = Uri.parse(msg5);
                    Intent intent_obj = new Intent(Intent.ACTION_VIEW, uri_parse);
                    startActivity(intent_obj);
                }
            });
        }
        if(!msg.equals(null))
        {
            news_title.setText(msg);
            news_title.setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick(View view)
                {
                    Uri uri_parse = Uri.parse(msg5);
                    Intent intent_obj = new Intent(Intent.ACTION_VIEW, uri_parse);
                    startActivity(intent_obj);
                }
            });

        }

        else {
            news_title.setText("No Information Available!");
        }
        if(!msg3.equals(null)) {
            news_Descp.setText(msg3);
            news_Descp.setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick(View view)
                {
                    Uri uri_parse = Uri.parse(msg5);
                    Intent intent_obj = new Intent(Intent.ACTION_VIEW, uri_parse);
                    startActivity(intent_obj);
                }
            });
        }
        else {
            news_Descp.setText("No Information Available!");
        }
        return v;
    }

    private void onRestoreInstanceStae(Bundle savedInstanceState)
    {
        if(savedInstanceState!=null)
        {
            news_title.setText(savedInstanceState.getString("news_title"));
            news_Descp.setText(savedInstanceState.getString("news_Descp"));
            news_pageno.setText(savedInstanceState.getString("news_pageno"));
            written_by_name.setText(savedInstanceState.getString("author"));
            FragmentManager manager = getFragmentManager();
            FragmentTransaction transaction = manager.beginTransaction();
            my_frag = (NewsArticleFragment) manager.getFragment(savedInstanceState, "myFragment");
        }

    }

    @Override
    public void onSaveInstanceState(Bundle outState)
    {
        super.onSaveInstanceState(outState);
        FragmentManager mgr = getFragmentManager();
        if(my_frag != null) {
            mgr.putFragment(outState, "myFragment", my_frag);
        }
        outState.putString("author", written_by_name.getText().toString());
        outState.putString("news_title", news_title.getText().toString());
        outState.putString("news_Descp", news_Descp.getText().toString());
        outState.putString("news_pageno", news_pageno.getText().toString());
    }
}