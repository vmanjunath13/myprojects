package com.example1.vaish.assign4;

import android.content.Context;
import android.location.Location;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Vaishnavi on 03/20/2020.
 */

public class CivilGovernmentAdaptor extends RecyclerView.Adapter<CivilGovernmentViewHolder> {
    MainActivity mainActivity;
    Context context;
    private List<Location> locationlist;
    List<CivilGovermentOfficial> civilGovernmentOfficialList;

    public CivilGovernmentAdaptor(MainActivity mainActivity, List<CivilGovermentOfficial> civilGovernmentOfficialList) {
        this.mainActivity =  mainActivity;
        this.civilGovernmentOfficialList = civilGovernmentOfficialList;
    }

    public CivilGovernmentAdaptor(ArrayList<Location> locationList, MainActivity mainActivity) {
        this.locationlist = locationList;
        mainActivity = mainActivity;
    }

    @Override
    public CivilGovernmentViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View thisItemsView = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_gov_list,
                parent, false);
        thisItemsView.setOnClickListener(mainActivity);

        return new CivilGovernmentViewHolder(thisItemsView);
    }

    @Override
    public void onBindViewHolder(CivilGovernmentViewHolder holder, int position) {
        CivilGovermentOfficial civilGovermentOfficial = civilGovernmentOfficialList.get(position);
        holder.civilOfficeNameText.setText(civilGovermentOfficial.getOfficeName());
        holder.civilOfficialNameText.setText(civilGovermentOfficial.getCivilOfficial().getOfficialName()+"("+ civilGovermentOfficial.getCivilOfficial().getOfficialParty()+")");

    }

    @Override
    public int getItemCount() {
        return civilGovernmentOfficialList.size();
    }

}
