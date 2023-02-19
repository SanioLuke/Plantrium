package com.sanioluke00.plantrium_beaplanter;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class UserDBHelper extends SQLiteOpenHelper {

    public UserDBHelper(@Nullable Context context) {
        super(context, "PlantriumDB", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL("CREATE TABLE userTable (id INTEGER PRIMARY KEY AUTOINCREMENT, fullname TEXT, emailid TEXT, password TEXT)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS userTable");
    }

    public int signUpToPlantrium(String fullname, String emailid, String password){
        SQLiteDatabase db= this.getWritableDatabase();
        String[] columns = new String[]{"fullname"};
        String selection = "emailid = ?";
        String[] selectionArgs = new String[]{emailid};

        Cursor cursor = db.query("userTable", columns, selection, selectionArgs, null, null, null);
        int cursorCount = cursor.getCount();
        if (cursorCount > 0) {
            return -1;
        }
        else{
            ContentValues values= new ContentValues();
            values.put("fullname",fullname);
            values.put("emailid",emailid);
            values.put("password",password);
            long result= db.insert("userTable",null,values);
            if(result>=0){
                return 1;
            }
            else {
                return 0;
            }
        }
    }

    public Cursor loginIntoPlantrium(String emailid, String password) {

        SQLiteDatabase db = this.getReadableDatabase();

        String[] columns = new String[]{"fullname","emailid"};
        String selection = "emailid = ? AND password = ?";
        String[] selectionArgs = new String[]{emailid,password};

        Cursor cursor = db.query("userTable", columns, selection, selectionArgs, null, null, null);
        return cursor;
    }

    public Cursor retrievePasswordPlantrium(String emailid) {

        SQLiteDatabase db = this.getReadableDatabase();

        String[] columns = new String[]{"password"};
        String selection = "emailid = ?";
        String[] selectionArgs = new String[]{emailid};

        Cursor cursor = db.query("userTable", columns, selection, selectionArgs, null, null, null);
        return cursor;
    }

}
