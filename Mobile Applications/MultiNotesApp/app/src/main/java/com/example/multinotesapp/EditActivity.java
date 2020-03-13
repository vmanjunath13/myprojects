package com.example.multinotesapp;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Toast;

public class EditActivity extends AppCompatActivity {

    private EditText editTitle;
    private EditText editNoteText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);

        editTitle = findViewById(R.id.editTitle);
        editNoteText = findViewById(R.id.editNoteText);
        editNoteText.setMovementMethod(new ScrollingMovementMethod());

        Intent i = getIntent();
        if(i.getExtras() != null){
            editTitle.setText(i.getStringExtra("TITLE"));
            editNoteText.setText(i.getStringExtra("NOTETEXT"));
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_edit, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        if(item.getItemId() == R.id.saveNote){
            Intent toMain = new Intent();
            //Untitled Note doesn't get saved -- returning to main activity
            if(editTitle.getText().toString().isEmpty()){
                setResult(-1, toMain);
                finish();
                Toast.makeText(this, "Untitled Note wasn't saved.", Toast.LENGTH_LONG).show();
            }
            //Save Note
            else{
                Intent i = getIntent();
                //Editting An Old Note
                if(i.getExtras() != null){
                    String title = editTitle.getText().toString();
                    String noteText = editNoteText.getText().toString();
                    //No Changes To Old Note
                    if(noteText.equals(i.getStringExtra("NOTETEXT")) && title.equals(i.getStringExtra("TITLE"))){
                        setResult(-1, toMain);
                        finish();
                    }
                    //Changes To Old Note
                    else{
                        toMain.putExtra("TITLE", editTitle.getText().toString());
                        toMain.putExtra("NOTETEXT", editNoteText.getText().toString());
                        setResult(0, toMain);
                        finish();
                    }
                }
                //New Note
                else{
                    toMain.putExtra("NEW-TITLE", editTitle.getText().toString());
                    toMain.putExtra("NEW-NOTETEXT", editNoteText.getText().toString());
                    setResult(0, toMain);
                    finish();
                }
            }
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed(){
        //Note has no title
        if(editTitle.getText().toString().isEmpty()){
            Intent toMain = new Intent();
            setResult(-1, toMain);
            Toast.makeText(this, "Untitled Note wasn't saved.", Toast.LENGTH_SHORT).show();
            finish();
        }
        //Note has a title
        else{
            Intent intent1 = new Intent();
            Intent intent2 = getIntent();
            //User wants to edit note
            if(intent2.getExtras() != null) {
                String title = editTitle.getText().toString();
                String noteText = editNoteText.getText().toString();
                //User made no edits
                if (noteText.equals(intent2.getStringExtra("NOTETEXT")) && title.equals(intent2.getStringExtra("TITLE"))) {
                    setResult(-1, intent1);
                    finish();
                }
                //Show dialog box if user did make edits
                else {
                    AlertDialog.Builder ADB = new AlertDialog.Builder(this);
                    //User wants to save note
                    ADB.setPositiveButton("YES!", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            Intent toMain = new Intent();
                            Intent i = getIntent();
                            //Editting An Old Note
                            if(i.getExtras() != null){
                                String title = editTitle.getText().toString();
                                String noteText = editNoteText.getText().toString();
                                //No Changes To Old Note
                                if(noteText.equals(i.getStringExtra("NOTETEXT")) && title.equals(i.getStringExtra("TITLE"))){
                                    setResult(-1, toMain);
                                    finish();
                                }
                                //Changes To Old Note
                                else{
                                    toMain.putExtra("TITLE", editTitle.getText().toString());
                                    toMain.putExtra("NOTETEXT", editNoteText.getText().toString());
                                    setResult(0, toMain);
                                    finish();
                                }
                            }
                            //New Note
                            else{
                                toMain.putExtra("NEW-TITLE", editTitle.getText().toString());
                                toMain.putExtra("NEW-NOTETEXT", editNoteText.getText().toString());
                                setResult(0, toMain);
                                finish();
                            }
                        }
                    });

                    ADB.setNegativeButton("NO!", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            Intent toMain = new Intent();
                            setResult(-1, toMain);
                            finish();
                        }
                    });

                    //Dialog Box
                    ADB.setTitle("Note is not saved!");
                    ADB.setMessage("Save '" + editTitle.getText().toString() + "' note?");
                    AlertDialog AD = ADB.create();
                    AD.show();
                }
            }
            //Save New Note
            else{
                    //Another alert dialog
                    AlertDialog.Builder ADB = new AlertDialog.Builder(this);

                    //User wants to save the note
                    ADB.setPositiveButton("YES!", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            Intent toMain = new Intent();
                            toMain.putExtra("NEW-TITLE", editTitle.getText().toString());
                            toMain.putExtra("NEW-NOTETEXT", editNoteText.getText().toString());
                            setResult(0, toMain);
                            finish();
                        }
                    });

                    ADB.setNegativeButton("No", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            Intent toMain = new Intent();
                            setResult(-1, toMain);
                            finish();
                        }
                    });

                    //Dialog Box
                    ADB.setTitle("Note is not saved!");
                    ADB.setMessage("Save '" + editTitle.getText().toString() + "' note?");
                    AlertDialog AD = ADB.create();
                    AD.show();
            }
        }

    }

}
