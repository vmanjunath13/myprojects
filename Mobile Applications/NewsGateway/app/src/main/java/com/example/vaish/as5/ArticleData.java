package com.example.vaish.as5;

import java.io.Serializable;



class ArticleData implements Serializable
{
    //to store title
    private String news_title;
    //to store author
    private String written_by;
    //to store image
    private String news_img;
    //to store description
    private String news_descp;
    //to store publication
    private String news_publ;
    //to get url
    public String getN_url()
    {
        return n_url;
    }
    //to set url
    public void setN_url(String n_url)
    {
        this.n_url = n_url;
    }
    private String n_url;
    //to store article
    public ArticleData(String written_by, String news_title, String news_descp, String news_img, String news_publ, String n_url)
    {
        //reference author
        this.written_by = written_by;
        //reference news title
        this.news_title = news_title;
        //reference description
        this.news_descp = news_descp;
        //reference image
        this.news_img = news_img;
        //reference publication
        this.news_publ = news_publ;
        this.n_url = n_url;
    }
    //function article data
    public ArticleData(String written_by, String news_title, String news_descp, String news_img)
    {
        //reference author
        this.written_by = written_by;
        //reference title
        this.news_title = news_title;
        //reference description
        this.news_descp = news_descp;
        //reference image
        this.news_img = news_img;
    }
    //get author
    public String getWritten_by()
    {
        return written_by;
    }
    //set author
    public void setWritten_by(String written_by)
    {
        this.written_by = written_by;
    }
    //set get news title
    public String getNews_title()
    {
        return news_title;
    }
    //set news title
    public void setNews_title(String news_title)
    {
        this.news_title = news_title;
    }
    //get news description
    public String getNews_descp()
    {
        return news_descp;
    }
    //set news description
    public void setNews_descp(String news_descp)
    {
        this.news_descp = news_descp;
    }
    //get news image
    public String getNews_img()
    {
        return news_img;
    }
    //set image
    public void setNews_img(String news_img)
    {
        this.news_img = news_img;
    }
    //get publication
    public String getNews_publ()
    {
        return news_publ;
    }
    //set publication
    public void setNews_publ(String news_publ)
    {
        this.news_publ = news_publ;
    }
}
