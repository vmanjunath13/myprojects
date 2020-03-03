package com.example.multinotesapp;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

public class NoteAdapter extends RecyclerView.Adapter<NoteViewHolder>{
    private static final String TAG = "NoteAdapter";
    private List<Note> noteList;
    private MainActivity mainActivity;

    public NoteAdapter(List<Note> nl, MainActivity ma){
        this.noteList = nl;
        mainActivity = ma;
    }

    @NonNull
    @Override
    public NoteViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType){
        View itemView = LayoutInflater.from(parent.getContext())
            .inflate(R.layout.note_list_row, parent, false);
        itemView.setOnClickListener(mainActivity);
        itemView.setOnLongClickListener(mainActivity);
        return new NoteViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull NoteViewHolder holder, int position){
        Note N = noteList.get(position);
        holder.lastSaveDate.setText(N.getLastSaveDate());
        holder.noteTitle.setText(N.getNoteTitle());
        holder.trimText.setText(N.getTrimText());
    }

    @Override
    public int getItemCount(){
        return noteList.size();
    }
}