package com.example1.vaish.assign4;

import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.text.util.Linkify;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

/**
 * Created by Vaishnavi on 03/20/2020.
 */

public class OfficialActivity extends AppCompatActivity implements View.OnClickListener {
    TextView bannerLocTextView;
    TextView abtInfoTextView;
    TextView abtPartyInfoView;
    TextView officialAddTextView;
    TextView phoneNumberTextView;
    TextView emailTextView;
    TextView urlTextView;
    ImageView officialImageView;
    ImageView officialPartyImgView;
    ConstraintLayout constraintLayout;
    CivilGovermentOfficial civilGovernmentOfficialMain;
    final static String NO_DATA_PROVIDED="No Data Provided";
    public static final String TAG="OfficialActivity";
    ImageView googleImageView;
    ImageView twitterIconImageView;
    ImageView facebookIconImageView;
    ImageView youtubeIconImageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_official);

        bannerLocTextView = (TextView) findViewById(R.id.ofcActBannerText);
        abtInfoTextView = (TextView) findViewById(R.id.ofcAbtTextView);
        abtPartyInfoView = (TextView) findViewById(R.id.ofcParty);
        officialAddTextView = (TextView) findViewById(R.id.ofcAddTextView);
        phoneNumberTextView = (TextView) findViewById(R.id.ofcPhoneNumTextView);
        emailTextView = (TextView) findViewById(R.id.ofcEmailTextView);
        urlTextView = (TextView) findViewById(R.id.ofcWebSiteTectView);
        officialImageView = (ImageView) findViewById(R.id.ofcImgView);
        officialPartyImgView = (ImageView) findViewById(R.id.ofcPartyView);
        googleImageView = (ImageView)findViewById(R.id.googleIconImageView);
        twitterIconImageView = (ImageView) findViewById(R.id.twitterIconImageView);
        facebookIconImageView = (ImageView) findViewById(R.id.facebookIconImageView);
        youtubeIconImageView = (ImageView) findViewById(R.id.youtubeIconImageView);
        constraintLayout = (ConstraintLayout) findViewById(R.id.officalActContraint);
        officialImageView.setOnClickListener(this);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);

        Intent ofcActIntent = getIntent();
        final CivilGovermentOfficial civilGovermentOfficial = (CivilGovermentOfficial) ofcActIntent.getSerializableExtra(getString(R.string.SerializeGovernmentObject));
        civilGovernmentOfficialMain = civilGovermentOfficial;
        CivilOfficial civilOfficial = civilGovermentOfficial.getCivilOfficial();
        SocialMediaChannel officialSocialMediaChannel = civilOfficial.getSocialMediaChannel();
        OfficialAddress localAdd = civilGovermentOfficial.getBannerTextAddress();

        String phoneNum = "";
        String email = "";
        String website = "";
        if (civilOfficial.getOfficialPhNumList() != null && civilOfficial.getOfficialPhNumList().size() > 0) {
            phoneNum = civilOfficial.getOfficialPhNumList().get(0);
        } else {
            phoneNum = NO_DATA_PROVIDED;
        }

        if (civilOfficial.getOfficialEmailList() != null && civilOfficial.getOfficialEmailList().size() > 0) {
            email = civilOfficial.getOfficialEmailList().get(0);
        } else {
            email = NO_DATA_PROVIDED;
        }
        if (civilOfficial.getOfficialWebsiteList() != null && civilOfficial.getOfficialWebsiteList().size() > 0) {
            website = civilOfficial.getOfficialWebsiteList().get(0);
        } else {
            website = NO_DATA_PROVIDED;
        }

        setSocialMediaChannel(officialSocialMediaChannel);

        setOfficialImage(civilGovermentOfficial);

        facebookIconImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                facebookClicked(v, civilGovermentOfficial);
            }
        });

        twitterIconImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                twitterClicked(v, civilGovermentOfficial);
            }
        });

        googleImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                googlePlusClicked(v, civilGovermentOfficial);
            }
        });

        youtubeIconImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                youTubeClicked(v, civilGovermentOfficial);
            }
        });

        bannerLocTextView.setText(localAdd.getCity() + ", " + localAdd.getState() + " " + localAdd.getZip());
        abtInfoTextView.setText(getAboutString(civilGovermentOfficial));
        String partyName = "(" + civilGovermentOfficial.getCivilOfficial().getOfficialParty() + ")";
        abtPartyInfoView.setText(partyName);
        officialAddTextView.setText(getCombinedAddress(civilOfficial.getOfficialAddress()));
        phoneNumberTextView.setText(phoneNum);
        emailTextView.setText(email);
        urlTextView.setText(website);
        Linkify.addLinks(officialAddTextView, Linkify.MAP_ADDRESSES);
        Linkify.addLinks(phoneNumberTextView, Linkify.PHONE_NUMBERS);
        Linkify.addLinks(emailTextView, Linkify.EMAIL_ADDRESSES);
        Linkify.addLinks(urlTextView, Linkify.WEB_URLS);
        officialAddTextView.setLinkTextColor(Color.parseColor("#ffffff"));
        phoneNumberTextView.setLinkTextColor(Color.parseColor("#ffffff"));
        emailTextView.setLinkTextColor(Color.parseColor("#ffffff"));
        urlTextView.setLinkTextColor(Color.parseColor("#ffffff"));

        officialPartyImgView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setAction(Intent.ACTION_VIEW);
                intent.addCategory(Intent.CATEGORY_BROWSABLE);
                String partyName = civilGovermentOfficial.getCivilOfficial().getOfficialParty();
                if (partyName.equalsIgnoreCase("Democratic Party")
                        || partyName.equalsIgnoreCase("Democratic")) {
                    intent.setData(Uri.parse("https://democrats.org/"));
                } else if (partyName.equalsIgnoreCase("Republican Party")
                        || partyName.equalsIgnoreCase("Republican")) {
                    intent.setData(Uri.parse("https://www.gop.com/"));
                }
                startActivity(intent);
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                Intent clickedNoteIntent = new Intent();
                setResult(RESULT_OK, clickedNoteIntent);
                finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public boolean isOnline() {
        ConnectivityManager connMgr = (ConnectivityManager)
                getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
        return (networkInfo != null && networkInfo.isConnected());
    }

    @Override
    public void onBackPressed(){
        Intent clickedNoteIntent = new Intent();
        setResult(RESULT_OK, clickedNoteIntent);
        finish();
    }
    String getCombinedAddress(OfficialAddress officialOfficialAddress){
        String addressString="";
        if(officialOfficialAddress !=null){
            if(officialOfficialAddress.getLine1() != null){
                addressString = officialOfficialAddress.getLine1()+"\n";
            }
            if(officialOfficialAddress.getLine2() != null){
                addressString +=  officialOfficialAddress.getLine2()+"\n";
            }
            if(officialOfficialAddress.getLine3() != null){
                addressString += officialOfficialAddress.getLine3()+"\n";
            }
            if(officialOfficialAddress.getCity() != null){
                addressString += officialOfficialAddress.getCity()+",";
            }
            if(officialOfficialAddress.getState() !=null){
                addressString += officialOfficialAddress.getState()+" ";
            }
            if(officialOfficialAddress.getZip() !=null){
                addressString += officialOfficialAddress.getZip();
            }
        }
        return addressString;
    }

    void setSocialMediaChannel(SocialMediaChannel socialMediaChannel){
        if(socialMediaChannel !=null) {
            if (socialMediaChannel.getGooglePageID() == null) {
                googleImageView.setVisibility(View.INVISIBLE);
            }
            if (socialMediaChannel.getFacebookPageID() == null) {
                facebookIconImageView.setVisibility(View.INVISIBLE);
            }
            if (socialMediaChannel.getTwitterPageID() == null) {
                twitterIconImageView.setVisibility(View.INVISIBLE);
            }
            if (socialMediaChannel.getYoutubePageID() == null) {
                youtubeIconImageView.setVisibility(View.INVISIBLE);
            }
        }else{
            googleImageView.setVisibility(View.INVISIBLE);
            facebookIconImageView.setVisibility(View.INVISIBLE);
            twitterIconImageView.setVisibility(View.INVISIBLE);
            youtubeIconImageView.setVisibility(View.INVISIBLE);
        }
    }

    String getAboutString(CivilGovermentOfficial civilGovermentOfficial){
        String aboutString = "";
        aboutString = civilGovermentOfficial.getOfficeName()
                + "\n"+ civilGovermentOfficial.getCivilOfficial().getOfficialName();
        if(civilGovermentOfficial.getCivilOfficial().getOfficialParty()!=null && civilGovermentOfficial.getCivilOfficial().getOfficialParty().length() > 0){
          String partyName = civilGovermentOfficial.getCivilOfficial().getOfficialParty();
            if(partyName.equalsIgnoreCase("Republican") || partyName.equalsIgnoreCase("Democratic"))
            aboutString =aboutString + "\n("+ civilGovermentOfficial.getCivilOfficial().getOfficialParty()+")";

            if(partyName.equalsIgnoreCase("Democratic Party")
                    || partyName.equalsIgnoreCase("Democratic")){
                int myColor = getResources().getColor(R.color.colorBlue);
                constraintLayout.setBackgroundColor(myColor);
            }else if (partyName.equalsIgnoreCase("Republican Party")
                    || partyName.equalsIgnoreCase("Republican")) {
                int myColor = getResources().getColor(R.color.colorRed);
                constraintLayout.setBackgroundColor(myColor);
            }else {
                int myColor = getResources().getColor(R.color.colorBlack);
                constraintLayout.setBackgroundColor(myColor);
            }
        }
        return aboutString;
    }
    @Override
    public void onClick(View v) {
        if(civilGovernmentOfficialMain.getCivilOfficial().getOfficialPhotoLink() != null &&
                civilGovernmentOfficialMain.getCivilOfficial().getOfficialPhotoLink().length() > 0) {
            startPhotoActivity(civilGovernmentOfficialMain);
        }
    }

    public void startPhotoActivity(CivilGovermentOfficial civilGovermentOfficial){
            Intent officialActIntent = new Intent(this, PhotoActivity.class);
            officialActIntent.putExtra(getString(R.string.SerializeGovernmentObject), civilGovermentOfficial);
            startActivityForResult(officialActIntent,0);
    }

    interface OfficialPartyConstant {
        public static final String PARTY_DEMOCRATIC= "Democratic";
        public static final String PARTY_REPUBLICAN = "Republican";
    }

    public void facebookClicked(View v,CivilGovermentOfficial civilGovermentOfficial) {
        String FACEBOOK_URL = "https://www.facebook.com/" + civilGovermentOfficial.getCivilOfficial().getSocialMediaChannel().getFacebookPageID();
        String urlToUse;
        PackageManager packageManager = getPackageManager();
        try {
            int versionCode = packageManager.getPackageInfo("com.facebook.katana", 0).versionCode;
            if (versionCode >= 3002850) {
                urlToUse = "fb://facewebmodal/f?href=" + FACEBOOK_URL;
            }else{
                urlToUse = "fb://page/" + civilGovermentOfficial.getCivilOfficial().getSocialMediaChannel().getFacebookPageID();;
            }
        }catch (PackageManager.NameNotFoundException e){
            urlToUse = FACEBOOK_URL;
        }
        Intent facebookIntent = new Intent(Intent.ACTION_VIEW);
        facebookIntent.setData(Uri.parse(urlToUse));
        startActivity(facebookIntent);
    }

    public void twitterClicked(View v,CivilGovermentOfficial civilGovermentOfficial) {
        Intent intent = null;
        String name =  civilGovermentOfficial.getCivilOfficial().getSocialMediaChannel().getTwitterPageID();
        try {
            // get the Twitter app if possible
            getPackageManager().getPackageInfo("com.twitter.android", 0);
            intent = new Intent(Intent.ACTION_VIEW, Uri.parse("twitter://user?screen_name=" + name));
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        } catch (Exception e) { // no Twitter app, revert to browser
            intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://twitter.com/" + name));
        }
        startActivity(intent);
    }

    public void googlePlusClicked(View v, CivilGovermentOfficial civilGovermentOfficial) {
        String name = civilGovermentOfficial.getCivilOfficial().getSocialMediaChannel().getGooglePageID();
        Intent intent = null;
        try {
            intent = new Intent(Intent.ACTION_VIEW);
            intent.setClassName("com.google.android.apps.plus", "com.google.android.apps.plus.phone.UrlGatewayActivity");
            intent.putExtra("customAppUri", name);
            startActivity(intent);
        } catch (ActivityNotFoundException e) {
            startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://plus.google.com/" + name)));
        }
    }

    public void youTubeClicked(View v, CivilGovermentOfficial civilGovermentOfficial) {
        String name = civilGovermentOfficial.getCivilOfficial().getSocialMediaChannel().getYoutubePageID();
        Intent intent = null;
        try {
            intent = new Intent(Intent.ACTION_VIEW);
            intent.setPackage("com.google.android.youtube");
            intent.setData(Uri.parse("https://www.youtube.com/" + name));
            startActivity(intent);
        } catch (ActivityNotFoundException e) {
            startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.youtube.com/" + name)));
        }
    }

    private void setOfficialImage(final CivilGovermentOfficial civilGovermentOfficial){
        final String photoUrl = civilGovermentOfficial.getCivilOfficial().getOfficialPhotoLink();
        if(isOnline()) {
            if ( photoUrl != null) {
                Picasso picasso = new Picasso.Builder(this).listener(new Picasso.Listener() {
                    @Override
                    public void onImageLoadFailed(Picasso picasso, Uri uri, Exception exception) {
                        // Here we try https if the http image attempt failed
                        final String changedUrl = photoUrl.replace("http:", "https:");

                        picasso.load(changedUrl)
                                .fit()
                                .error(R.drawable.brokenimage)
                                .placeholder(R.drawable.hourglass)
                                .into(officialImageView);
                    }
                }).build();

                picasso.load(photoUrl)
                        .fit()
                        .error(R.drawable.brokenimage)
                        .placeholder(R.drawable.hourglass)
                        .into(officialImageView);

            } else {
                Picasso.with(this).load(photoUrl)
                        .fit()
                        .error(R.drawable.brokenimage)
                        .placeholder(R.drawable.missingimage)
                        .into(officialImageView);

            }
        } else {
            officialImageView.setImageResource(R.drawable.brokenimage);
        }

        String partyName = civilGovermentOfficial.getCivilOfficial().getOfficialParty();
        if(partyName.equalsIgnoreCase("Democratic Party")
                || partyName.equalsIgnoreCase("Democratic")){
            officialPartyImgView.setImageResource(R.drawable.dem_logo);
        }else if (partyName.equalsIgnoreCase("Republican Party")
                || partyName.equalsIgnoreCase("Republican")) {
            officialPartyImgView.setImageResource(R.drawable.rep_logo);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 0) {
            if (resultCode == RESULT_OK) {
            }
        }
    }
}
