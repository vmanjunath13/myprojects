package com.example1.vaish.assign4;

import java.io.Serializable;

/**
 * Created by Vaishnavi on 03/20/2020.
 */

public class SocialMediaChannel implements Serializable{
    private String googlePageID;
    private String twitterPageID;
    private String facebookPageID;
    private String youtubePageID;

    public SocialMediaChannel() {
    }

    public SocialMediaChannel(String googlePageID, String twitterPageID, String facebookPageID, String youtubePageID) {
        this.googlePageID = googlePageID;
        this.twitterPageID = twitterPageID;
        this.facebookPageID = facebookPageID;
        this.youtubePageID = youtubePageID;
    }

    public String getGooglePageID() {
        return googlePageID;
    }

    public void setGooglePageID(String googlePageID) {
        this.googlePageID = googlePageID;
    }

    public String getTwitterPageID() {
        return twitterPageID;
    }

    public void setTwitterPageID(String twitterPageID) {
        this.twitterPageID = twitterPageID;
    }

    public String getFacebookPageID() {
        return facebookPageID;
    }

    public void setFacebookPageID(String facebookPageID) {
        this.facebookPageID = facebookPageID;
    }

    public String getYoutubePageID() {
        return youtubePageID;
    }

    public void setYoutubePageID(String youtubePageID) {
        this.youtubePageID = youtubePageID;
    }

    @Override
    public String toString() {
        return "Google ID "+ googlePageID
                +"\n Facebook "+ facebookPageID
                +"\n twitter "+ twitterPageID
                +"\n youtube "+ youtubePageID;

    }
}