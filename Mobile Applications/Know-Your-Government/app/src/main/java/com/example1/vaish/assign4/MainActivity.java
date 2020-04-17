package com.example1.vaish.assign4;

import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.*;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.InputFilter;
import android.text.InputType;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

/**
 * Created by Vaishnavi on 03/20/2020.
 */

public class MainActivity extends AppCompatActivity implements DownloadCallback<String>,View.OnClickListener {

    final String APT_KEY = "AIzaSyCVvvKcQqoTaupnuxGzpEz4USFpswZt8dc";
    final String SEARCH_TEXT = "&address=";
    String search_url ="https://www.googleapis.com/civicinfo/v2/representatives?key=";
    DataFetchTask dataFetchTask;
    private static ArrayList<Location> locationList = new ArrayList<>();
    private static final String TAG="MainActivity";
    List<CivilGovermentOfficial> civilGovernmentOfficialList;
    RecyclerView governmentRecycleView;
    private CivilGovernmentAdaptor civilGovernmentAdaptor;
    CardView governmentCardView;
    static final int ACT_SHOW = 0;
    static final int NO_ITM_CLICK = -1;
    private static final String NO_RESP_REC="No response received.";
    private Locator locator;
    String address;
    public TextView locAddressTextView;
    OfficialAddress localaddress;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        dataFetchTask = new DataFetchTask(this);
        governmentCardView = (CardView)findViewById(R.id.cardViewCivil);
        locAddressTextView = (TextView) findViewById(R.id.bannerTxt);
        governmentRecycleView = (RecyclerView) findViewById(R.id.mainRecycleView);
        governmentRecycleView.setAdapter(civilGovernmentAdaptor);
        if(isOnline()) {
            locator = new Locator(this);
            String numberOnly = address.replaceAll("[^0-9]", "");
            String api_search_url = search_url + APT_KEY + SEARCH_TEXT + numberOnly;
            address = address.replace('$', ' ');
            Log.d(TAG,"Locate Address "+ address);
            locAddressTextView.setText(address);
            dataFetchTask.execute(api_search_url);
        }else{
            showUserAlert("No Network Connection", "Data cannot be accessed/loaded \n without an internet connection.");
            locAddressTextView.setText("No data for Location");
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        Log.d("MainActivity","Menu inflater called ");
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.addNewStock :
                if(isOnline()) {
                    searchDialog();
                }
                else {
                   showUserAlert("No Network Connection", "Stocks Can not be added \n without network connection");
                }
                return true;
            case R.id.aboutAct :
                startAboutActivity();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }


    @Override
    public void updateFromDownload(String result) {
        if(result==null || result.length()<50 || result.equals(NO_RESP_REC)){
            showUserAlert("No Result","Can not fetch data");
            return;
        }else {
            civilGovernmentOfficialList = readCivilJsonData(result);
             localaddress = readCivilAddressData(result);
             Log.d(TAG," Local Address "+localaddress);
            address = localaddress.getCity()+", "+localaddress.getState()+" "+localaddress.getZip();
            locAddressTextView.setText(address);
            initCivilGovernmentListView();
        }
    }

    @Override
    public NetworkInfo getActiveNetworkInfo() {
        final String DEBUG_TAG = "NetworkStatusExample";
        ConnectivityManager connectivityManager =
                (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        boolean isWifiConn = networkInfo.isConnected();
        boolean isMobileConn = networkInfo.isConnected();
        Log.d(DEBUG_TAG, "Wifi connected: " + isWifiConn);
        Log.d(DEBUG_TAG, "Mobile connected: " + isMobileConn);

        return networkInfo;
    }

    public boolean isOnline() {
        ConnectivityManager connMgr = (ConnectivityManager)
                getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
        return (networkInfo != null && networkInfo.isConnected());
    }

    @Override
    public void onProgressUpdate(int progressCode, int percentComplete) {

    }

    @Override
    public void finishDownloading() {

    }

    private List<CivilGovermentOfficial> readCivilJsonData(String jsonData){
        List<CivilGovermentOfficial> govList=null;
        try {
            InputStream inputStream = new ByteArrayInputStream(jsonData.getBytes("UTF-8"));
            JSONParser JSONParser = new JSONParser(this);
            govList =  JSONParser.readCivilData(inputStream);
        }catch(Exception e){
            e.printStackTrace();
        }
        return govList;
    }

    private OfficialAddress readCivilAddressData(String jsonData){
        OfficialAddress officialAddress =null;
        try {
            InputStream inputStream = new ByteArrayInputStream(jsonData.getBytes("UTF-8"));
            JSONParser JSONParser = new JSONParser(this);
            officialAddress =  JSONParser.readAddressDetails(inputStream);
        }catch(Exception e){
            e.printStackTrace();
        }
        return officialAddress;
    }

    public void searchDialog(){
        final DataFetchTask dataFetchTask;
        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Enter a City, State or Zip Code");
        final EditText input = new EditText(this);
        input.setInputType(InputType.TYPE_CLASS_TEXT);
        input.setFilters(new InputFilter[] {new InputFilter.AllCaps()});
        builder.setView(input);
        locator = new Locator(this);
        dataFetchTask = new DataFetchTask(this);
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                String  m_Text = input.getText().toString();
                Log.d("MainActivity","test := "+m_Text);
                if(isOnline()) {
                    locator.setUpLocationManager();
                    locator.determineLocation();
                    String api_search_url = search_url + APT_KEY +SEARCH_TEXT+m_Text;
                    dataFetchTask.execute(api_search_url);
                }else{
                    showUserAlert("No Network Connection", "Data can not be fetched\n without network connection");
                }
            }
        });
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        builder.show();
    }
    public void showUserAlert(String alertTitle, String alertMessage){
        LayoutInflater layoutInflater = LayoutInflater.from(MainActivity.this);
        View promptView = layoutInflater.inflate(R.layout.activity_dialog_alert, null);
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(MainActivity.this);
        alertDialogBuilder.setView(promptView);

        final TextView tittle = (TextView) promptView.findViewById(R.id.alertTitile);
        final TextView message = (TextView) promptView.findViewById(R.id.alertMsg);

        tittle.setText(alertTitle);
        message.setText(alertMessage);

        alertDialogBuilder.setCancelable(false)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.dismiss();
                    }
                });
        AlertDialog alert = alertDialogBuilder.create();
        alert.show();
    }

    @Override
    public void onClick(View view) {
        int pos = governmentRecycleView.getChildLayoutPosition(view);
        CivilGovermentOfficial civilGovermentOfficial = civilGovernmentOfficialList.get(pos);
        Log.d(TAG,"Government office selected => "+ civilGovermentOfficial.getOfficeName());
        startOfficialActivity(ACT_SHOW,pos);
    }


    public void initCivilGovernmentListView(){
        governmentRecycleView = (RecyclerView) findViewById(R.id.mainRecycleView);
        civilGovernmentAdaptor = new CivilGovernmentAdaptor(this, civilGovernmentOfficialList);
        governmentRecycleView.setAdapter(civilGovernmentAdaptor);
        final LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        governmentRecycleView.setLayoutManager(layoutManager);
        civilGovernmentAdaptor.notifyDataSetChanged();
    }

    public void startAboutActivity(){
        Intent abtActyIntent = new Intent(this,AboutActivity.class);
        startActivityForResult(abtActyIntent,0);
    }

    public void startOfficialActivity(int actionCode, int itemClickedPos){
        if(actionCode == ACT_SHOW) {
            CivilGovermentOfficial civilGovermentOfficial;
            if(itemClickedPos!= NO_ITM_CLICK){
                civilGovermentOfficial = civilGovernmentOfficialList.get(itemClickedPos);
                civilGovermentOfficial.setBannerTextAddress(localaddress);
            }else{
                Log.d(TAG,"Something wrong check code ");
                return;
            }
            Intent officalActIntent = new Intent(this, OfficialActivity.class);
            officalActIntent.putExtra(getString(R.string.SerializeGovernmentObject), civilGovermentOfficial);
           startActivityForResult(officalActIntent, ACT_SHOW);
        }
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == ACT_SHOW) {
            if (resultCode == RESULT_OK) {
            }
        }
    }
    private String doAddress(double latitude, double longitude) {

        Log.d(TAG, "doAddress: Lat: " + latitude + ", Lon: " + longitude);

        List<android.location.Address> addresses = null;
        for (int times = 0; times < 3; times++) {
            Geocoder geocoder = new Geocoder(this, Locale.getDefault());
            try {
                Log.d(TAG, "doAddress: Getting address now");
                addresses = geocoder.getFromLocation(latitude, longitude, 1);
                StringBuilder sb = new StringBuilder();
                for (android.location.Address ad : addresses) {
                    Log.d(TAG, "doLocation: " + ad);
                    sb.append(ad.getLocality()+", "+ad.getAdminArea());
                    sb.append("$"+ad.getPostalCode());

                }
                Log.d(TAG, "Address iS ***********" + sb);
                return sb.toString();
            } catch (IOException e) {
                Log.d(TAG, "doAddress: " + e.getMessage());
            }
            Toast.makeText(this, "GeoCoder service is slow - please wait", Toast.LENGTH_SHORT).show();
        }
        Toast.makeText(this, "GeoCoder service timed out - please try again", Toast.LENGTH_LONG).show();
        return null;
    }
    public void setData(double lat, double lon) {
        address = doAddress(lat, lon);
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {

        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        Log.d(TAG, "onRequestPermissionsResult: CALL: " + permissions.length);
        Log.d(TAG, "onRequestPermissionsResult: PERM RESULT RECEIVED");

        if (requestCode == 5) {
            Log.d(TAG, "onRequestPermissionsResult: permissions.length: " + permissions.length);
            for (int i = 0; i < permissions.length; i++) {
                if (permissions[i].equals(Manifest.permission.ACCESS_FINE_LOCATION)) {
                    if (grantResults[i] == PackageManager.PERMISSION_GRANTED) {
                        Log.d(TAG, "onRequestPermissionsResult: HAS PERM");
                        locator.setUpLocationManager();
                        locator.determineLocation();
                    } else {
                        Toast.makeText(this, "Location permission was denied - cannot determine address", Toast.LENGTH_LONG).show();
                        Log.d(TAG, "onRequestPermissionsResult: NO PERM");
                    }
                }
            }
        }
        Log.d(TAG, "onRequestPermissionsResult: Exiting onRequestPermissionsResult");
    }
    public void noLocationAvailable() {
        Toast.makeText(this, "No location providers were available", Toast.LENGTH_LONG).show();
    }

    @Override
    protected void onDestroy() {
        locator.shutdown();
        super.onDestroy();
    }

}
