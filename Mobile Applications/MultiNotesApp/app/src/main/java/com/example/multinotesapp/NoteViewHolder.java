package com.example.multinotesapp;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

public class NoteViewHolder extends RecyclerView.ViewHolder{
    public TextView lastSaveDate;
    public TextView noteTitle;
    public TextView trimText;
    public NoteViewHolder(View v){
        super(v);
        lastSaveDate = v.findViewById(R.id.lastSaveDate);
        noteTitle = v.findViewById(R.id.noteTitle);
        trimText = v.findViewById(R.id.trimText);
    }
}
