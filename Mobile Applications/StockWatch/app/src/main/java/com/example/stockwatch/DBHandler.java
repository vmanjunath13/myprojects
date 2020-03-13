package com.example.stockwatch;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;

public class DBHandler extends SQLiteOpenHelper {


    //Initializing a lot of stuff for the database
    private static final String TAG = "DBHandler";
    private static final String dbName = "StockDB";
    private static final int dbVersion = 1;
    private SQLiteDatabase sqldb;

    //Constructor
    //Open Database
    public DBHandler(Context context){
        super(context, dbName, null, dbVersion);
        sqldb = getWritableDatabase();
    }

    //Table and columns for stockTable
    private static final String stockTable = "stockTable";
    private static final String symbol = "SYMBOL";
    private static final String name = "NAME";

    //Create SQL Command
    private static String createCommand = "CREATE TABLE " + stockTable + " (" + symbol + " TEXT not null unique, " + name + " TEXT not null)";

    //This has to be overriden to actually use the SQLiteOpenHelper
    @Override
    public void onUpgrade(SQLiteDatabase sqldb, int oldVer, int newVer){
        return;
    }

    //On the create method, we execute the SQL CREATE command
    @Override
    public void onCreate(SQLiteDatabase sqldb) {
        sqldb.execSQL(createCommand);
    }

    //Close Database
    public void shutDown(){
        sqldb.close();
    }

    public void delStock(String symbolValue){
        //Delete a stock from the sqldb and returns the count of how many were deleted
        int cnt = sqldb.delete(stockTable, symbol + " = ?", new String[]{symbol});
        //Log.d(TAG, "delStock: Stock Deleted!" + cnt);
    }

    public void addStock(String symbolValue, String nameValue){
        //Use content values to setup data (symbol:name) for insertion
        ContentValues stockValues = new ContentValues();
        stockValues.put(symbol, symbolValue);
        stockValues.put(name, nameValue);

        //add to database and get h database
        long key = sqldb.insert(stockTable, null, stockValues);
        //Log.d(TAG, "addStock: Stock Added!" + key);
    }

    //Returns the stocks that are in the database
    public ArrayList<String[]> getStocks(){
        //Init ArrayList to be returned
        ArrayList<String[]> stockArrayList = new ArrayList<String[]>();

        //Cursor object used to make queries
        Cursor c = sqldb.query(stockTable, new String[]{symbol, name}, null, null, null, null, null);

        //Cursor not null
        if(c!=null){
            if(c.moveToFirst()){ //moves cursor to first row
                for(int i = 0; i < c.getCount(); i++){
                    //get the values for s=symbol n=name, add to array list, move the cursor
                    String s = c.getString(0);
                    String n = c.getString(1);
                    stockArrayList.add(new String[]{s, n});
                    Log.d(TAG, "getStocks: Added another stock to array list");
                    c.moveToNext();
                }
                c.close();
            }
        }
        Log.d(TAG, "getStocks: Done adding stocks to array list");
        return stockArrayList;
    }

    //Dumps Database content to log
    public void dumpLog(){
        //Cursor object used to make queries
        Cursor c = sqldb.query(stockTable, new String[]{symbol, name}, null, null, null, null, null);

        //Cursor not null
        if(c!=null){
            if(c.moveToFirst()) { //moves cursor to first row
                for (int i = 0; i < c.getCount(); i++) {
                    //get the values for s = symbol n =name
                    String s = c.getString(0);
                    String n = c.getString(1);

                    //data gets logged here then after, cursor moves
                    Log.d(TAG, "dumpLog: SYMBOL:" + s + " NAME:" + n);
                    c.moveToNext();
                }
                c.close();
            }
        }
    }
}
