package com.example.vaish.as5;

import android.graphics.Color;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.view.Menu;
import android.content.res.Configuration;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.view.MenuItem;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.v7.app.AppCompatActivity;

import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.*;
import android.widget.Toast;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity
{
    //action message
    static final String ACTION_MSG_TO_SERVICE = "ACTION_MSG_TO_SERVICE";
    //TAG for log cat
    private static final String TAG = "MainActivity";
    //adaptor
    private ArrayAdapter arr_adp_drawer_list;
    //news story
    static final String ACTION_NEWS_STORY = "ACTION_NEWS_STORY";
    //page adaptor
    private MyPageAdapter my_page_adp_news_adp;
    //news reciver
    private NewsReciever news_reciv_cata;
    //page view for page
    private ViewPager view_pager_page;
    //list of fragments
    private List<Fragment> frag;
    //the drawer layout
    private DrawerLayout draw_lay_drawerLayout;
    //the data
    private ArticleData article_data_news_arti;
    //the hashmap object creation
    HashMap n_obj_hashmap = new HashMap();
    //list viewer of the drawer
    private ListView list_view_drawer_list;
    //to hold the flag value
    private static int flag_2 = 1;
    //to hold the flag value
    private int flag_3 = 1;
    //array list of items
    private ArrayList<String> arr_list_item = new ArrayList<>();
    //drawable action toggle bar
    private ActionBarDrawerToggle act_bar_drawer_tog_DrawerTog;
    //array list of news resources
    private ArrayList<String> arr_list_newsresource1 = new ArrayList<>();
    //array list of news resources
    private ArrayList<String> arr_list_newsresource = new ArrayList<>();
    String et_2;
    //array list of news resources
    private ArrayList<SourceGetSet> arr_list_newsresourcelist = new ArrayList<>();
    //the fragment content
    Fragment frag_Content;
    String[] cat_String_array = new String[arr_list_newsresource.size()];
    private static FragmentManager frag_mgr;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        draw_lay_drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        String all_cat = "all";
        list_view_drawer_list = (ListView) findViewById(R.id.left_drawer);

        new NewsSourceDownloader(MainActivity.this).execute(String.valueOf(all_cat));
        news_reciv_cata = new NewsReciever();

        Intent serv_intent = new Intent(MainActivity.this, NewService.class);
        startService(serv_intent);

        IntentFilter int_filter = new IntentFilter(ACTION_NEWS_STORY);
        registerReceiver(news_reciv_cata, int_filter);

        act_bar_drawer_tog_DrawerTog = new ActionBarDrawerToggle(
                this,                  /* host Activity */
                draw_lay_drawerLayout,         /* DrawerLayout object */
                R.string.drawer_open,  /* "open drawer" description for accessibility */
                R.string.drawer_close  /* "close drawer" description for accessibility */
        );


        arr_adp_drawer_list = new ArrayAdapter<>(this,
                R.layout.drawer_list_item, arr_list_item);
        list_view_drawer_list.setAdapter(arr_adp_drawer_list);
        list_view_drawer_list.setOnItemClickListener(
                new ListView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id)
                    {
                        selectItem(position);
                        view_pager_page.setBackground(null);
                        for(int h = 0; h < arr_list_newsresourcelist.size(); h++)
                        {
                            if(arr_list_item.get(position).equals(arr_list_newsresourcelist.get(h).getName()))
                            {
                                Intent new_int = new Intent();
                                new_int.putExtra("myinfo", arr_list_newsresourcelist.get(h));
                                new_int.setAction(ACTION_MSG_TO_SERVICE);
                                sendBroadcast(new_int);
                                draw_lay_drawerLayout.closeDrawer(list_view_drawer_list);
                            }
                        }
                    }
                }
        );

        if (savedInstanceState != null) {
            frag_Content = getSupportFragmentManager().getFragment(savedInstanceState, "myFragmentName");
        }
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);

        frag = getFrag();

        my_page_adp_news_adp = new MyPageAdapter(getSupportFragmentManager());
        view_pager_page = (ViewPager) findViewById(R.id.viewpager);
        view_pager_page.setBackgroundResource(R.drawable.newswalls);
        view_pager_page.setAdapter(my_page_adp_news_adp);

    }

    private void selectItem(int position)
    {
        Toast.makeText(this, arr_list_item.get(position), Toast.LENGTH_SHORT).show();
        setTitle(arr_list_item.get(position));
        draw_lay_drawerLayout.closeDrawer(list_view_drawer_list);
    }

    private class NewsReciever extends BroadcastReceiver
    {
        @Override
        public void onReceive(Context context, Intent intent)
        {
            switch (intent.getAction())
            {
                case ACTION_NEWS_STORY:
                    if (intent.hasExtra("vaish"))
                    {
                       reDoFragments((ArrayList<ArticleData>) intent.getSerializableExtra("vaish"));
                    }
            }

        }
    }

    public void setSources(ArrayList<SourceGetSet> newsresourcelist, ArrayList<String> newsresourcecategory1)
    {
        n_obj_hashmap.clear();
        arr_list_item.removeAll(arr_list_item);
        this.arr_list_newsresourcelist.removeAll(this.arr_list_newsresourcelist);
        this.arr_list_newsresourcelist.addAll(newsresourcelist);
        newsresourcecategory1.add(0, "all");
        if(flag_3 == 1)
        {
            arr_list_newsresource.removeAll(arr_list_newsresource);
            arr_list_newsresource.addAll(newsresourcecategory1);
            cat_String_array = arr_list_newsresource.toArray(new String[arr_list_newsresource.size()]);
            flag_3++;
        }
        for(int y = 0; y < this.arr_list_newsresourcelist.size(); y++)
        {
            arr_list_item.add(this.arr_list_newsresourcelist.get(y).getName());
            n_obj_hashmap.put(this.arr_list_newsresourcelist.get(y).getName(), this.arr_list_newsresourcelist.get(y));
        }
        invalidateOptionsMenu();
        arr_adp_drawer_list.notifyDataSetChanged();
    }

    private void reDoFragments(ArrayList<ArticleData> article)
    {
        for (int y = 0; y < my_page_adp_news_adp.getCount(); y++)
        {
            my_page_adp_news_adp.notifyChangeInPosition(y);
        }
        frag.clear();

        for (int y = 0; y < article.size(); y++)
        {

            frag.add(NewsArticleFragment.newInstance(article.get(y).getNews_title(), article.get(y).getNews_img(), article.get(y).getWritten_by(), article.get(y).getNews_descp(), article.get(y).getNews_publ(), article.get(y).getN_url(), " Page " + (y +1) + " of" + article.size()));

        }

        my_page_adp_news_adp.notifyDataSetChanged();
        view_pager_page.setCurrentItem(0);

    }

    private List<Fragment> getFrag() {
        List<Fragment> frag_list = new ArrayList<Fragment>();
        return frag_list;
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        act_bar_drawer_tog_DrawerTog.syncState();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        act_bar_drawer_tog_DrawerTog.onConfigurationChanged(newConfig);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        getMenuInflater().inflate(R.menu.action_menu, menu);
        return true;
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
//        Log.d(TAG,"onPrepareOptionsMenu "+ cat_String_array.length);
        menu.clear();
        if(cat_String_array.length != 0)
        {
            for (int y = 0; y < cat_String_array.length; y++)
            {
                menu.add(R.menu.action_menu, Menu.NONE, 0, cat_String_array[y]);
                MenuItem item = menu.getItem(y);
                SpannableString spanString = new SpannableString(menu.getItem(y).getTitle().toString());
                String str = menu.getItem(y).getTitle().toString();
                changeMenuTitleColor(item, str, spanString);
            }
            return true;
        }
        else
            return false;
    }

    private void changeMenuTitleColor(MenuItem item, String str, SpannableString spanString) {

        if (str.equals("general")) {
            spanString.setSpan(new ForegroundColorSpan(Color.YELLOW), 0, spanString.length(), 0);
        }
        else if (str.equals("sports")) {
            spanString.setSpan(new ForegroundColorSpan(Color.BLUE), 0, spanString.length(), 0);
        }
        else if (str.equals("business")) {
            spanString.setSpan(new ForegroundColorSpan(Color.GREEN), 0, spanString.length(), 0);
        }
        else if(str.equals("entertainment")) {
            spanString.setSpan(new ForegroundColorSpan(Color.RED), 0, spanString.length(), 0);
        }
        else if(str.equals("science")) {
            spanString.setSpan(new ForegroundColorSpan(Color.CYAN), 0, spanString.length(), 0);
        }
        else if(str.equals("health")) {
            spanString.setSpan(new ForegroundColorSpan(Color.MAGENTA), 0, spanString.length(), 0);
        }
        else if(str.equals("technology")) {
            spanString.setSpan(new ForegroundColorSpan(Color.GRAY), 0, spanString.length(), 0);
        }
        item.setTitle(spanString);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if (act_bar_drawer_tog_DrawerTog.onOptionsItemSelected(item))
        {
            return true;
        }
        new NewsSourceDownloader(MainActivity.this).execute(String.valueOf(item));
        return true;
    }

    private class MyPageAdapter extends FragmentPagerAdapter {
        private long baseId = 0;


        public MyPageAdapter(FragmentManager f_mgrm)
        {
            super(f_mgrm);
        }

        @Override
        public int getItemPosition(Object gip_obj)
        {
            return POSITION_NONE;
        }

        @Override
        public Fragment getItem(int frag_pos)
        {
            return frag.get(frag_pos);
        }

        @Override
        public int getCount()
        {
            return frag.size();
        }

        @Override
        public long getItemId(int pos)
        {

            return baseId + pos;
        }


        public void notifyChangeInPosition(int n) {

            baseId += getCount() + n;
        }

    }


    @Override
    protected void onDestroy()
    {
        unregisterReceiver(news_reciv_cata);
        Intent int_obj = new Intent(MainActivity.this, NewService.class);
        stopService(int_obj);
        super.onDestroy();
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState)
    {
        super.onRestoreInstanceState(savedInstanceState);
        arr_list_item.addAll(savedInstanceState.getStringArrayList("HISTORY"));
        arr_list_newsresource.addAll(savedInstanceState.getStringArrayList("HISTORY1"));
    }

    @Override
    protected void onSaveInstanceState(Bundle outState)
    {
        super.onSaveInstanceState(outState);
        outState.putStringArrayList("HISTORY", arr_list_item);
        outState.putStringArrayList("HISTORY1", arr_list_newsresource);
    }
}


