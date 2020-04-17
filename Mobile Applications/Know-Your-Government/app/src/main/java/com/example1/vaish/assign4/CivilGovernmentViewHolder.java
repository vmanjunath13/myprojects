package com.example1.vaish.assign4;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

/**
 * Created by Vaishnavi on 03/20/2020.
 */

public class CivilGovernmentViewHolder extends RecyclerView.ViewHolder {
    public TextView civilOfficeNameText;
    public TextView civilOfficialNameText;

    public CivilGovernmentViewHolder(View itemView) {
        super(itemView);
        civilOfficeNameText = (TextView) itemView.findViewById(R.id.ofcNameText);
        civilOfficialNameText = (TextView) itemView.findViewById(R.id.officialNameText);
    }
}
