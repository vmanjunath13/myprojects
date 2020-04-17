package com.example1.vaish.assign4;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

/**
 * Created by Vaishnavi on 03/20/2020.
 */

public class PhotoActivity extends AppCompatActivity {
    TextView aboutTxtView;
    ImageView officialImgView;
    ImageView officialPartyImgView;
    ConstraintLayout backgroundLayoutConstraint;
    CivilGovermentOfficial mainCivilGovernmentOfficial;
    TextView phtActLocTv;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_photo);

        aboutTxtView = (TextView) findViewById(R.id.paOfficialInfoText);
        officialImgView = (ImageView) findViewById(R.id.paImgView);
        officialPartyImgView = (ImageView) findViewById(R.id.paPartyImgView);
        backgroundLayoutConstraint = (ConstraintLayout) findViewById(R.id.phtActConstraint);
        phtActLocTv = (TextView) findViewById(R.id.photoActBannerText);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        Intent ofcActIntent = getIntent();

        CivilGovermentOfficial civilGovermentOfficial = (CivilGovermentOfficial) ofcActIntent.getSerializableExtra(getString(R.string.SerializeGovernmentObject));
        mainCivilGovernmentOfficial = civilGovermentOfficial;
        OfficialAddress locaAdd = civilGovermentOfficial.getBannerTextAddress();
        setOfficialImage(civilGovermentOfficial);
        aboutTxtView .setText(getOfficialInfoString(civilGovermentOfficial));
        phtActLocTv.setText(locaAdd.getCity()+","+locaAdd.getState()+","+locaAdd.getZip());

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                Intent intent = new Intent();
                setResult(RESULT_OK, intent);
                finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed(){
        Intent clickedNoteIntent = new Intent();
        setResult(RESULT_OK, clickedNoteIntent);
        finish();
    }


    String getOfficialInfoString(CivilGovermentOfficial civilGovermentOfficial){
        String aboutString = "";
        aboutString = civilGovermentOfficial.getOfficeName()
                + "\n"+ civilGovermentOfficial.getCivilOfficial().getOfficialName();
        if(civilGovermentOfficial.getCivilOfficial().getOfficialParty()!=null && civilGovermentOfficial.getCivilOfficial().getOfficialParty().length() > 0){
          //  aboutString =aboutString + "\n("+ civilGovermentOfficial.getCivilOfficial().getOfficialParty()+")";

            String partyName = civilGovermentOfficial.getCivilOfficial().getOfficialParty();
            if(partyName.equalsIgnoreCase("Democratic Party")
                    || partyName.equalsIgnoreCase("Democratic")){
                int myColor = getResources().getColor(R.color.colorBlue);
                backgroundLayoutConstraint.setBackgroundColor(myColor);
            }else if (partyName.equalsIgnoreCase("Republican Party")
                    || partyName.equalsIgnoreCase("Republican")) {
                int myColor = getResources().getColor(R.color.colorRed);
                backgroundLayoutConstraint.setBackgroundColor(myColor);
            }else {
                int myColor = getResources().getColor(R.color.colorBlack);
                backgroundLayoutConstraint.setBackgroundColor(myColor);
            }
        }
        return aboutString;
    }

    public boolean isOnline() {
        ConnectivityManager connMgr = (ConnectivityManager)
                getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
        return (networkInfo != null && networkInfo.isConnected());
    }

    private void setOfficialImage(final CivilGovermentOfficial civilGovermentOfficial){
        final String photoUrl = civilGovermentOfficial.getCivilOfficial().getOfficialPhotoLink();
        if (isOnline()) {
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
                                .into(officialImgView);
                    }
                }).build();

                picasso.load(photoUrl)
                        .fit()
                        .error(R.drawable.brokenimage)
                        .placeholder(R.drawable.hourglass)
                        .into(officialImgView);

            } else {
                Picasso.with(this).load(photoUrl)
                        .fit()
                        .error(R.drawable.brokenimage)
                        .placeholder(R.drawable.missingimage)
                        .into(officialImgView);

            }
        } else {
            officialImgView.setImageResource(R.drawable.brokenimage);
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

}
