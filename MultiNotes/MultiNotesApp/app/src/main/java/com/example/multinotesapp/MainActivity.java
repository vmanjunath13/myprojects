package com.example.multinotesapp;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.JsonWriter;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import java.io.*;
import java.text.*;
import java.util.*;
import org.json.*;

public class MainActivity extends AppCompatActivity implements View.OnClickListener, View.OnLongClickListener{

        private List<Note> noteList = new ArrayList<>();
        private RecyclerView recyclerView;
        private NoteAdapter noteAdapter;

    private int noteToEdit = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = findViewById(R.id.recycler);
        noteAdapter = new NoteAdapter(noteList, this);

        recyclerView.setAdapter(noteAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        this.loadJSONFile();

        if(noteList.size()>0){
            getSupportActionBar().setTitle(getString(R.string.app_name) + "(" + noteList.size() + ")");
        }
        noteAdapter.notifyDataSetChanged();
    }

    @Override
    protected void onPause() {
        this.writeJSONFile();
        super.onPause();
    }

    @Override
    protected void onStop(){
        this.writeJSONFile();
        super.onStop();
    }

    //Onclick Listeners
    @Override
    public void onClick(View v){
        //Position for view holder
        noteToEdit = recyclerView.getChildLayoutPosition(v);

        //Setting up for the edit activity
        Intent toEdit = new Intent(this, EditActivity.class);

        Note currNote = noteList.get(noteToEdit);
        toEdit.putExtra("TITLE", currNote.getNoteTitle());
        toEdit.putExtra("NOTETEXT", currNote.getNoteText());

        startActivityForResult(toEdit, 2);
        noteAdapter.notifyDataSetChanged();
    }

    @Override
    public boolean onLongClick(View v){
        AlertDialog.Builder ADB = new AlertDialog.Builder(this);
        final int position = recyclerView.getChildLayoutPosition(v);

        //Dialog Box YES = Delete, No = Don't Delete
        ADB.setPositiveButton("YES!", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
            noteList.remove(position);
            //changes have been made to array list
            //notify adapter!
            if(noteList.size() > 0){
                getSupportActionBar().setTitle(getString(R.string.app_name) + "(" + noteList.size() + ")");
                noteAdapter.notifyDataSetChanged();
            }
            //No notes
            else{
                getSupportActionBar().setTitle(getString(R.string.app_name));
                noteAdapter.notifyDataSetChanged();
            }
            }
        });

        ADB.setNegativeButton("NO!", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                //Nothing to do here, just return back to keep things the same
            }
        });

        TextView noteTitle = v.findViewById(R.id.noteTitle);
        ADB.setTitle("Delete '" + noteTitle.getText().toString() + "'?");
        AlertDialog AD = ADB.create();
        AD.show();
        noteAdapter.notifyDataSetChanged();
        return true;
    }

    //Menu and Activity Stuff
    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_main, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        switch (item.getItemId()) {
            case(R.id.about):
                Intent toAbout = new Intent(this, AboutActivity.class);
                startActivity(toAbout);
                return super.onOptionsItemSelected(item);
            case(R.id.addNote):
                Intent toEdit = new Intent(this, EditActivity.class);
                startActivityForResult(toEdit, 1);
                return super.onOptionsItemSelected(item);
        }
        return super.onOptionsItemSelected(item);
    }

    public static String getPresentTime(){
        Date D = Calendar.getInstance().getTime();
        SimpleDateFormat SDF = new SimpleDateFormat("EEE MMM d, h:mm a");
        String lastSaveDate = SDF.format(D).toString();
        return lastSaveDate;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent receivedData){
        super.onActivityResult(requestCode, resultCode, receivedData);

        switch(requestCode){
            //New Note Created
            case 1:
                if(resultCode==0){
                    String noteTitle = receivedData.getStringExtra("NEW-TITLE");
                    String noteText = receivedData.getStringExtra("NEW-NOTETEXT");

                    noteList.add(0, new Note(noteTitle, noteText, getPresentTime()));
                    getSupportActionBar().setTitle(getString(R.string.app_name) + "(" + noteList.size() + ")");
                    noteAdapter.notifyDataSetChanged();
                }
                break;

            //Existing Note Edited
            case 2:
                if(resultCode==0){
                    String noteTitle = receivedData.getStringExtra("TITLE");
                    String noteText = receivedData.getStringExtra("NOTETEXT");
                    noteList.remove(noteToEdit);
                    noteList.add(0, new Note(noteTitle, noteText, getPresentTime()));
                    getSupportActionBar().setTitle(getString(R.string.app_name) + "(" + noteList.size() + ")");
                    noteAdapter.notifyDataSetChanged();
                }

                //Note Wasn't Edited
                else if(resultCode==-1){
                    noteToEdit = -1;
                }
                break;
        }
    }

    //JSON R/W
    private void loadJSONFile(){
        try{
            InputStream input = getApplicationContext().openFileInput(getString(R.string.notesFile));
            BufferedReader BR = new BufferedReader(new InputStreamReader(input, getString(R.string.encoding)));

            StringBuilder SB = new StringBuilder();
            String line;
            while((line = BR.readLine()) != null) {
                SB.append(line);
            }

            JSONArray jsonArray = new JSONArray(SB.toString());
            for(int i=0; i<jsonArray.length(); i++){

                JSONObject noteObject = jsonArray.getJSONObject(i);
                String lastSaveDate = noteObject.getString("lastSaveDate");
                String noteTitle = noteObject.getString("noteTitle");
                String noteText = noteObject.getString("noteText");
                noteList.add(new Note(noteTitle, noteText, lastSaveDate));
            }
            noteAdapter.notifyDataSetChanged();
        }
        catch (FileNotFoundException e){
            e.printStackTrace();
        }
        catch (UnsupportedEncodingException e){
            e.printStackTrace();
        }
        catch (IOException e){
            e.printStackTrace();
        }
        catch (JSONException e){
            e.printStackTrace();
        }
    }

    private void writeJSONFile(){
        try{
            FileOutputStream output = getApplicationContext().openFileOutput(getString(R.string.notesFile), Context.MODE_PRIVATE);

            JsonWriter JW = new JsonWriter(new OutputStreamWriter(output, getString(R.string.encoding)));
            JW.setIndent("  ");
            JW.beginArray();

            for(int i=0; i<noteList.size(); i++){
                JW.beginObject();
                JW.name("lastSaveDate").value(noteList.get(i).getLastSaveDate());
                JW.name("noteTitle").value(noteList.get(i).getNoteTitle());
                JW.name("noteText").value(noteList.get(i).getNoteText());
                JW.endObject();
            }
            JW.endArray();
            JW.close();
        }
        catch (UnsupportedEncodingException e){
            e.printStackTrace();
        }
        catch (FileNotFoundException e){
            e.printStackTrace();
        }
        catch (IOException e){
            e.printStackTrace();
        }
    }
}
