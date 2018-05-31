package com.nilesh.nsec;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;


public class DatabaseHelper extends SQLiteOpenHelper {
    public static final String DATABASE_NAME = "nerd.db";
    public static final String TABLE_NAME = "temp_table";
    public static final String COL_1 = "ID";
    public static final String COL_2 = "POSITION";
    public static final String COL_3 = "TIME";
    public static final String COL_4= "MONDAY";
    public static final String COL_5 = "TUESDAY";
    public static final String COL_6 = "WEDNESDAY";
    public static final String COL_7 = "THURSDAY";
    public static final String COL_8 = "FRIDAY";
    public static final String COL_9 = "SATURDAY";







    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table " + TABLE_NAME +" (ID INTEGER PRIMARY KEY AUTOINCREMENT,POSITION TEXT,TIME TEXT,MONDAY TEXT,TUESDAY TEXT,WEDNESDAY TEXT,THURSDAY TEXT,FRIDAY TEXT,SATURDAY TEXT)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS "+TABLE_NAME);
        onCreate(db);
    }

    public boolean insertData(String ID,String pos,String TYM,String MON,String TUE,String WED,String THUR,String FRI,String SAT) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL_1,ID);
        contentValues.put(COL_2,pos);
        contentValues.put(COL_3,TYM);
        contentValues.put(COL_4,MON);
        contentValues.put(COL_5,TUE);
        contentValues.put(COL_6,WED);
        contentValues.put(COL_7,THUR);
        contentValues.put(COL_8,FRI);
        contentValues.put(COL_9,SAT);






        long result = db.insert(TABLE_NAME,null ,contentValues);
        if(result == -1)
            return false;
        else
            return true;
    }

    public Cursor getAllData() {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("select * from "+TABLE_NAME,null);
        return res;
    }

    public boolean updateData(String ID,String pos,String TYM,String MON,String TUE,String WED,String THUR,String FRI,String SAT) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL_1,ID);
        contentValues.put(COL_2,pos);
        contentValues.put(COL_3,TYM);
        contentValues.put(COL_4,MON);
        contentValues.put(COL_5,TUE);
        contentValues.put(COL_6,WED);
        contentValues.put(COL_7,THUR);
        contentValues.put(COL_8,FRI);
        contentValues.put(COL_9,SAT);




        db.update(TABLE_NAME, contentValues, "ID = ?",new String[] { ID });
        return true;
    }

    public Integer deleteData (String id) {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete(TABLE_NAME, "ID = ?",new String[] {id});
    }
    public Integer deleteAll () {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete(TABLE_NAME, null,null);
    }
}