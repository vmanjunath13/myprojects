package com.example1.vaish.assign4;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Vaishnavi on 03/20/2020.
 */

public class CivilOfficial implements Serializable{
    private String officialName;
    private OfficialAddress officialAddress;
    private String officialEmail;
    private String officialParty;
    private List<String> officialPhNumList;
    private List<String> officialEmailList;
    private List<String> officialWebsiteList;
    private String officialPhotoLink;
    private SocialMediaChannel socialMediaChannel;

    public CivilOfficial() {
    }

    public CivilOfficial(String officialName, OfficialAddress officialAddress, String officialParty, List<String> officialPhNumList, List<String> officialEmailList, List<String> officialWebsiteList, String officialPhotoLink, SocialMediaChannel socialMediaChannel) {
        this.officialName = officialName;
        this.officialAddress = officialAddress;
        this.officialParty = officialParty;
        this.officialPhNumList = officialPhNumList;
        this.officialEmailList = officialEmailList;
        this.officialWebsiteList = officialWebsiteList;
        this.officialPhotoLink = officialPhotoLink;
        this.socialMediaChannel = socialMediaChannel;
    }

    public List<String> getOfficialEmailList() {
        return officialEmailList;
    }

    public void setOfficialEmailList(List<String> officialEmailList) {
        this.officialEmailList = officialEmailList;
    }

    public String getOfficialEmail() {
        return officialEmail;
    }

    public void setOfficialEmail(String officialEmail) {
        this.officialEmail = officialEmail;
    }

    public String getOfficialName() {
        return officialName;
    }

    public void setOfficialName(String officialName) {
        this.officialName = officialName;
    }

    public OfficialAddress getOfficialAddress() {
        return officialAddress;
    }

    public void setOfficialAddress(OfficialAddress officialAddress) {
        this.officialAddress = officialAddress;
    }

    public String getOfficialParty() {
        return officialParty;
    }

    public void setOfficialParty(String officialParty) {
        this.officialParty = officialParty;
    }

    public List<String> getOfficialPhNumList() {
        return officialPhNumList;
    }

    public void setOfficialPhNumList(List<String> officialPhNumList) {
        this.officialPhNumList = officialPhNumList;
    }

    public List<String> getOfficialWebsiteList() {
        return officialWebsiteList;
    }

    public void setOfficialWebsiteList(List<String> officialWebsiteList) {
        this.officialWebsiteList = officialWebsiteList;
    }

    public String getOfficialPhotoLink() {
        return officialPhotoLink;
    }

    public void setOfficialPhotoLink(String officialPhotoLink) {
        this.officialPhotoLink = officialPhotoLink;
    }

    public SocialMediaChannel getSocialMediaChannel() {
        return socialMediaChannel;
    }

    public void setSocialMediaChannel(SocialMediaChannel socialMediaChannel) {
        this.socialMediaChannel = socialMediaChannel;
    }

    @Override
    public String toString() {
        return "Official Name "+officialName
                +"\n Adddress "+ officialAddress
                +"\n Party Name "+ officialParty
                +"\n Phone Num "+ officialPhNumList
                +"\n Email List "+ officialEmailList
                +"\n URL List "+ officialWebsiteList
                +"\n photo URL "+ officialPhotoLink
                +"\n Channel "+ socialMediaChannel;
    }
}
