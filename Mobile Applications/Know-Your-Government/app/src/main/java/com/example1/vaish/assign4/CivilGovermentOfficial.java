package com.example1.vaish.assign4;

import java.io.Serializable;

/**
 * Created by Vaishnavi on 03/20/2020.
 */

public class CivilGovermentOfficial implements Serializable{
    private String officeName;
    private int index;
    private CivilOfficial civilOfficial;
    OfficialAddress bannerTextAddress;


    public CivilGovermentOfficial(String officeName, int index, CivilOfficial civilOfficial) {
        this.officeName = officeName;
        this.index = index;
        this.civilOfficial = civilOfficial;
    }

    public String getOfficeName() {
        return officeName;
    }

    public void setOfficeName(String officeName) {
        this.officeName = officeName;
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public CivilOfficial getCivilOfficial() {
        return civilOfficial;
    }

    public void setCivilOfficial(CivilOfficial civilOfficial) {
        this.civilOfficial = civilOfficial;
    }

    public OfficialAddress getBannerTextAddress() {
        return bannerTextAddress;
    }

    public void setBannerTextAddress(OfficialAddress bannerTextAddress) {
        this.bannerTextAddress = bannerTextAddress;
    }

    @Override
    public String toString() {
        return "\n Office Name "+officeName
                +"\n Office Index "+ index
                +"\n Office ==> "+ civilOfficial;
    }
}
