package com.example1.vaish.assign4;

import android.content.Context;
import android.util.JsonReader;
import android.util.Log;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Vaishnavi on 03/20/2020.
 */

public class JSONParser {
    private final static String TAG="JSONParser";
    private Map<Integer,CivilOfficial> civilofficialOfficeMap;
    private List<CivilGovermentOfficial> civilGovermentOfficialList;
    private Context context;

    public JSONParser(Context context) {
        this.context = context;
    }


    public List<CivilGovermentOfficial> readCivilData(InputStream in) throws IOException {
        JsonReader jsonReader = new JsonReader(new InputStreamReader(in, "UTF-8"));try {
            readCivilOfficialObject(jsonReader);
        } finally {
            jsonReader.close();
        }
        in.reset();
        jsonReader = new JsonReader(new InputStreamReader(in, "UTF-8"));
        try {
            civilGovermentOfficialList = readCivilOfficeObject(jsonReader);
        } finally {
            jsonReader.close();
        }
        return civilGovermentOfficialList;
    }
    /*************************************Reading of offices Start******************************************/
    private List<CivilGovermentOfficial> readCivilOfficeObject(JsonReader reader) throws IOException {
        civilGovermentOfficialList = new ArrayList<>();
        reader.beginObject();
        while (reader.hasNext()) {
            String name = reader.nextName();
            if (name.equals("offices")) {
                reader.beginArray();
                while (reader.hasNext()) {
                    readCivilOfficeObjectList(reader);
                }
                reader.endArray();
            }else{
                reader.skipValue();
            }
        }
        reader.endObject();
        return civilGovermentOfficialList;
    }


    private void readCivilOfficeObjectList(JsonReader reader) throws IOException {
        String officeName=null;
        reader.beginObject();
        while (reader.hasNext()) {
            String name = reader.nextName();
            if (name.equals("name")) {
                officeName = reader.nextString();
            }else if (name.equals("officialIndices")) {
                reader.beginArray();
                while (reader.hasNext()) {
                    int index = reader.nextInt();
                    CivilOfficial civilOfficial = civilofficialOfficeMap.get(index);
                    civilGovermentOfficialList.add(new CivilGovermentOfficial(officeName,index, civilOfficial));
                    Log.d(TAG," Index "+index);
                }
                reader.endArray();
            }else{
                reader.skipValue();
            }
        }
        Log.d(TAG,"Office => "+officeName);
        reader.endObject();
    }

    private void readCivilOfficialObject(JsonReader reader) throws IOException {
        reader.beginObject();
        String officials=null;
        while (reader.hasNext()) {
            String name = reader.nextName();
            if (name.equals("officials")) {
                int count = 0;
                civilofficialOfficeMap = new HashMap<>();
                reader.beginArray();
                while (reader.hasNext()) {
                    CivilOfficial civilOfficial = readCivilOfficialsObjectList(reader);
                    civilofficialOfficeMap.put(count++, civilOfficial);
                }
                reader.endArray();
            }else{
                reader.skipValue();
            }
        }
        Log.d(TAG,"Official => "+officials);
        reader.endObject();
    }

    private CivilOfficial readCivilOfficialsObjectList(JsonReader reader) throws IOException {
         String officialName=null;
         OfficialAddress officialAddress =null;
         String partyName=null;
         List<String> phNumList=null;
         List<String> emailList= null;
         List<String> urlList=null;
         String photoUrl=null;
         SocialMediaChannel socialMediaChannel =null;
         CivilOfficial official;
        reader.beginObject();
        while (reader.hasNext()) {
            String name = reader.nextName();
            if (name.equals("name")) {
                officialName = reader.nextString();
            }else if (name.equals("address")) {
              officialAddress =  readCivilOfficialAddressArray(reader);
            }else if (name.equals("party")) {
                partyName=reader.nextString();
            }else if (name.equals("phones")) {
               phNumList =  readContactDetails(reader);
            }else if (name.equals("emails")) {
              emailList=  readEmailDetails(reader);
            }else if (name.equals("urls")) {
              urlList =  readWebsiteDetails(reader);
            }else if (name.equals("photoUrl")) {
               photoUrl = reader.nextString();
            }else if (name.equals("channels")) {
                socialMediaChannel = new SocialMediaChannel();
                readSocialMediaDetails(reader, socialMediaChannel);
            }else{
                reader.skipValue();
            }
        }
        Log.d(TAG,"Office => "+officialName);
        reader.endObject();
        official = new CivilOfficial(officialName, officialAddress,partyName,phNumList,emailList,urlList,photoUrl, socialMediaChannel);
        return official;
    }


    private OfficialAddress readCivilOfficialAddressArray(JsonReader reader) throws IOException {
        OfficialAddress officialAddress =null;
        reader.beginArray();
        while (reader.hasNext()) {
            String line1=null;
            String line2=null;
            String line3=null;
            String city=null;
            String state=null;
            String zip=null;
            reader.beginObject();
            while (reader.hasNext()) {
                String nextName = reader.nextName();
                if (nextName.equals("line1")) {
                    line1 = reader.nextString();
                }else if (nextName.equals("line2")) {
                    line2 = reader.nextString();
                }else if (nextName.equals("line3")) {
                    line3 = reader.nextString();
                }else if (nextName.equals("city")) {
                    city = reader.nextString();
                }else if (nextName.equals("state")) {
                    state = reader.nextString();
                }else if (nextName.equals("zip")) {
                    zip = reader.nextString();
                }else{
                    reader.skipValue();
                }
            }
            reader.endObject();
            officialAddress =new OfficialAddress(line1,line2,line3,city,state,zip);
            Log.d(TAG , " "+ officialAddress);
        }
        reader.endArray();
        return officialAddress;
    }


    private List<String> readContactDetails(JsonReader reader) throws IOException {
        List<String> phNumList= new ArrayList<>();
        reader.beginArray();
        while (reader.hasNext()) {
            String phoneNum = reader.nextString();
            phNumList.add(phoneNum);
            Log.d(TAG,"Phoen No "+phoneNum);
        }
        reader.endArray();
        return phNumList;
    }
    private List<String> readEmailDetails(JsonReader reader) throws IOException {
        List<String> emailList= new ArrayList<>();
        reader.beginArray();
        while (reader.hasNext()) {
            String email = reader.nextString();
            emailList.add(email);
            Log.d(TAG,"Phoen No "+email);
        }
        reader.endArray();
        return emailList;
    }
    private List<String> readWebsiteDetails(JsonReader reader) throws IOException {
        List<String> urlList= new ArrayList<>();
        reader.beginArray();
        while (reader.hasNext()) {
            String url = reader.nextString();
            urlList.add(url);
            Log.d(TAG,"url "+url);
        }
        reader.endArray();
        return urlList;
    }

    private void readSocialMediaDetails(JsonReader reader, SocialMediaChannel socialMediaChannel)throws IOException {
        reader.beginArray();
        while (reader.hasNext()) {
            String channelName="";
            String channelID="";
            reader.beginObject();
            while (reader.hasNext()){
                String name = reader.nextName();
                if (name.equals("type")) {
                    channelName = reader.nextString();
                }else if (name.equals("id")) {
                    channelID = reader.nextString();
                }else{
                    reader.skipValue();
                }
            }
            if(channelName.equalsIgnoreCase("GooglePlus")){
                socialMediaChannel.setGooglePageID(channelID);
            }else if(channelName.equalsIgnoreCase("Facebook")){
                socialMediaChannel.setFacebookPageID(channelID);
            }else if(channelName.equalsIgnoreCase("Twitter")){
                socialMediaChannel.setTwitterPageID(channelID);
            }else if(channelName.equalsIgnoreCase("YouTube")){
                socialMediaChannel.setYoutubePageID(channelID);
            }
            reader.endObject();
        }
        reader.endArray();
    }

    public OfficialAddress readAddressDetails(InputStream in) throws IOException {
        OfficialAddress officialAddress =null;
        JsonReader jsonReader = new JsonReader(new InputStreamReader(in, "UTF-8"));

        try {
          officialAddress =   readAddressObject(jsonReader);
        } finally {
            jsonReader.close();
        }
        return officialAddress;
    }
    private OfficialAddress readAddressObject(JsonReader reader)throws IOException {
        OfficialAddress officialAddress =null;
        reader.beginObject();
        while (reader.hasNext()) {
            String name = reader.nextName();
            if (name.equals("normalizedInput")) {
                String line1=null;
                String city=null;
                String state=null;
                String zip=null;
                reader.beginObject();
                while (reader.hasNext()) {
                    String nextName = reader.nextName();
                    if (nextName.equals("line1")) {
                        line1 = reader.nextString();
                    }else if (nextName.equals("city")) {
                        city = reader.nextString();
                    }else if (nextName.equals("state")) {
                        state = reader.nextString();
                    }else if (nextName.equals("zip")) {
                        zip = reader.nextString();
                    }else{
                        reader.skipValue();
                    }
                }
                reader.endObject();
                officialAddress =new OfficialAddress(line1,null,null,city,state,zip);
                Log.d(TAG , " "+ officialAddress);
            }else{
                reader.skipValue();
            }
        }
        reader.endObject();
        return officialAddress;
    }

}
