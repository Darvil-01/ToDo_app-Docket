package com.example.ToDo_Docket.DbHelpers;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.Nullable;

import com.example.ToDo_Docket.Models.List_model;

import java.util.ArrayList;

public class List_DPHelper extends SQLiteOpenHelper {

    final static String DBName="listdatabase.db";
    final static int DBVersion=2;
    Context context;


    public List_DPHelper(@Nullable Context context) {
        super(context, DBName, null, DBVersion);

    }


    @Override
    public void onCreate(SQLiteDatabase db) {

        db.execSQL(
                "create table list "+
                "(id integer primary key autoincrement,"+
                "day text)"
        );
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("Drop table if exists list" );
        onCreate(db);
    }

    public int insertList(String day){
        SQLiteDatabase sqLiteDatabase=getReadableDatabase();

        if(checkIsEntity(day)) {
            return 2;
        }
        else {
            ContentValues values = new ContentValues();
            values.put("day", day);
            long id = sqLiteDatabase.insert("list", null, values);
//            Log.i("check", "insertList: at"+id);
            if (id <=0)
                return 0;
            else return 1;
        }
    }

    public boolean checkIsEntity(String days) {
        SQLiteDatabase database = this.getWritableDatabase();
        Cursor cursor = database.rawQuery("select * from list where day=\"" + days+"\";", null);
        if (cursor.getCount() <=0) {
            cursor.close();
            return false;
        }
        cursor.close();
        return true;
    }
    public ArrayList<List_model> getList(){
        ArrayList<List_model> arrayList=new ArrayList<>();
        SQLiteDatabase database=this.getWritableDatabase();
        Cursor cursor=database.rawQuery("select id,day from list",null);
        if(cursor.moveToFirst()){
            do{
                List_model model=new List_model(null,0);
                model.setList_text(cursor.getString(1));
                model.setList_id(cursor.getInt(0));

//                Log.i("get", "getList: "+cursor.getString(1)+cursor.getInt(0));
                arrayList.add(model);
            }
            while(cursor.moveToNext());


        }
        cursor.close();

        return arrayList;
    }
    public int deleteList(String day){
        SQLiteDatabase database=this.getWritableDatabase();
        return database.delete("list","day='"+day+"'",null);
    }


    public void changeDayName(String fromDay, String toDay) {
        SQLiteDatabase database=this.getReadableDatabase();
        //database.rawQuery("update list set day='"+toDay+"' where day='"+fromDay+"'",null);
        ContentValues values=new ContentValues();
        values.put("day",toDay);
        database.update("list",values,"day=?",new String[]{fromDay});
        Log.i("cn", "changeDayName: changed ");

    }
}
