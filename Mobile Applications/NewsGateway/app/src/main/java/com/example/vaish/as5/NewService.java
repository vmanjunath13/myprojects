package com.example.vaish.as5;

import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.IBinder;

import java.util.ArrayList;

import static com.example.vaish.as5.MainActivity.ACTION_MSG_TO_SERVICE;
import static com.example.vaish.as5.MainActivity.ACTION_NEWS_STORY;


public class NewService extends Service {

    private boolean bool_running = true;
    public SourceGetSet source_name;
    private ArrayList<ArticleData> list_of_stories = new ArrayList<>();
    private ServiceReciever service_receiver;

    @Override
    public IBinder onBind(Intent intent)
    {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        service_receiver = new ServiceReciever();

        IntentFilter filt1 = new IntentFilter(ACTION_MSG_TO_SERVICE);
        registerReceiver(service_receiver, filt1);

        new Thread(new Runnable() {
            @Override
            public void run() {
                while (bool_running) {
                    try {
                        while (list_of_stories.size() == 0) {

                            Thread.sleep(250);
                        }

                        Intent int1 = new Intent();
                        int1.setAction(ACTION_NEWS_STORY);
                        int1.putExtra("vaish", list_of_stories);
                        sendBroadcast(int1);
                        list_of_stories.removeAll(list_of_stories);

                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                }
            }
        }).start();
        return Service.START_STICKY;
    }

    private class ServiceReciever extends BroadcastReceiver
    {
        @Override
        public void onReceive(Context context, Intent intent)
        {
            switch (intent.getAction()) {
                case ACTION_MSG_TO_SERVICE:
                    if (intent.hasExtra("myinfo"))
                    {
                        source_name = (SourceGetSet) intent.getSerializableExtra("myinfo");
                        new NewsArticleDownloader(NewService.this, source_name.getId()).execute();
                    }
            }

        }
    }

    public void setArticles(ArrayList<ArticleData> newsarticlelist)
    {
        list_of_stories.clear();
        list_of_stories.addAll(newsarticlelist);
    }

    @Override
    public void onDestroy()
    {
        unregisterReceiver(service_receiver);
        Intent intent_obj = new Intent(NewService.this, MainActivity.class);
        stopService(intent_obj);
        super.onDestroy();
    }
}



