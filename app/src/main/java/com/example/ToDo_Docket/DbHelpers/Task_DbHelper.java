package com.example.ToDo_Docket.DbHelpers;

import static android.database.sqlite.SQLiteDatabase.openOrCreateDatabase;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.example.ToDo_Docket.Models.Task_model;

import java.util.ArrayList;

public class Task_DbHelper {

   String dbname="listdatabase.db";
  final static int version=1;
  Context context;
  String tbname;





    public Task_DbHelper(Context context ,String tbname) {
        this.context = context;
        this.tbname=tbname;
        Log.i("getcontext", "is the "+context);
    }



        public SQLiteDatabase create (String table,Context context){
            SQLiteDatabase db=context.openOrCreateDatabase(dbname,context.MODE_PRIVATE,null);
            db.execSQL("CREATE TABLE IF NOT EXISTS \"" + table + "\"( id integer primary key autoincrement," +
                    "discription text," +
                    "duedate text," +
                    "duetime text," +
                    "category text,"+
                    "status Integer);");
            Log.i("getcreate", "yes table is created");

            return db;
        }

        public boolean insert(String disc, String duedate,String duetime,String catr, String table){
           SQLiteDatabase db= context.openOrCreateDatabase(dbname,context.MODE_ENABLE_WRITE_AHEAD_LOGGING,null);
           try {
               ContentValues values=new ContentValues();
               values.put("discription",disc);
               values.put("duedate",duedate);
               values.put("duetime",duetime);
               values.put("category",catr);
               values.put("status",0);
               //db.execSQL("Insert into "+table+" values("+disc+","+duedate+","+duetime+","+catr+",0)");
               db.insert("\""+table+"\"",null,values);
//               db.execSQL("insert into "+table+"(discription,duedate,duetime,category,status)values("+disc+","+duedate+","+duetime+","+catr+",0)");
           }catch (Exception ex){
               ex.printStackTrace();
               return false;

           }finally {
               return true;
           }

        }
        public boolean updateTask(String disc, String duedate,String duetime,String catr, String table,int id,int status) {
            SQLiteDatabase db = context.openOrCreateDatabase(dbname, context.MODE_ENABLE_WRITE_AHEAD_LOGGING, null);
//
            try{
               ContentValues values=new ContentValues();
               values.put("discription",disc);
               values.put("duedate",duedate);
               values.put("duetime",duetime);
               values.put("category",catr);
               values.put("status",status);
               db.update("\""+table+"\"",values,"id="+id,null);
//
            } catch (Exception ex) {
                ex.printStackTrace();
                return false;

            } finally {
                return true;
            }
        }


        public ArrayList<Task_model>getList(String table, String catr) {
            ArrayList<Task_model> list = new ArrayList<>();
            SQLiteDatabase db=context.openOrCreateDatabase(dbname,context.MODE_PRIVATE,null);
            Cursor cur = db.rawQuery("select id,discription,duedate,duetime,category,status from \"" + table + "\" where category=\"" + catr+"\";", null);
            if(cur.getCount()<=0)
                Log.i("getdata","data is not taken");
            else {
                if (cur.moveToFirst()) {
                    do {
                        Task_model model = new Task_model(null, null, null, null);
                        model.setTask_discp(cur.getString(1));
                        model.setTask_id(cur.getInt(0));
                        model.setDate(cur.getString(2));
                        model.setTime(cur.getString(3));
                        model.setCatgri(cur.getString(4));
                        model.setStatus(cur.getInt(5));

//                Log.i("get", "getList: "+cursor.getString(1)+cursor.getInt(0));
                        Log.i("getdata", "data is taken");
                        list.add(model);
                    }
                    while (cur.moveToNext());
                }
            }
            cur.close();

            return list;
        }
        public ArrayList<Task_model>getALLList(String table) {
            ArrayList<Task_model> list = new ArrayList<>();
            SQLiteDatabase db=context.openOrCreateDatabase(dbname,context.MODE_PRIVATE,null);
            Cursor cur = db.rawQuery("select id,discription,duedate,duetime,category,status from \"" + table+"\"", null);
            if(cur.getCount()<=0)
                Log.i("getdata","data is not taken");
            else {
                if (cur.moveToFirst()) {
                    do {
                        Task_model model = new Task_model(null, null, null, null);
                        model.setTask_discp(cur.getString(1));
                        model.setTask_id(cur.getInt(0));
                        model.setDate(cur.getString(2));
                        model.setTime(cur.getString(3));
                        model.setCatgri(cur.getString(4));
                        model.setStatus(cur.getInt(5));

//                Log.i("get", "getList: "+cursor.getString(1)+cursor.getInt(0));
                        Log.i("getdata", "data is taken");
                        list.add(model);
                    }
                    while (cur.moveToNext());
                }
            }
            cur.close();

            return list;
        }
        public boolean deleteTable(String table){
            SQLiteDatabase db=context.openOrCreateDatabase(dbname,context.MODE_PRIVATE,null);
            //db.delete(table,null,null);
            db.execSQL("Drop table if exists \""+table+"\"");

            return true;
        }
       public void changeStatus(int id,int status){
           SQLiteDatabase db=context.openOrCreateDatabase(dbname,context.MODE_PRIVATE,null);
           db.execSQL("update \""+tbname+"\" set status= "+status+" where id="+id);

       }

       public boolean deleteTask(int id ){
           SQLiteDatabase db=context.openOrCreateDatabase(dbname,context.MODE_PRIVATE,null);
           db.execSQL("delete from \""+tbname+"\" where id= "+id);
           return true;
       }
       public String getTaskDiscp(String duetime){
           SQLiteDatabase db=context.openOrCreateDatabase(dbname,context.MODE_PRIVATE,null);
           Cursor cur = db.rawQuery("select discription from Today where duetime=\""+duetime+"\" and status=0", null);
           if(cur.getCount()>0) {
               cur.moveToFirst();
               return cur.getString(0);
           }
           else
               return "null";
       }

    public void changeTableName(String fromDay,String toDay){
        SQLiteDatabase  db=context.openOrCreateDatabase(dbname,context.MODE_PRIVATE,null);
        db.execSQL("ALTER TABLE  '"+fromDay+"' RENAME TO "+toDay);
    }

    public boolean checkIsEntity(String days) {
        SQLiteDatabase  db=context.openOrCreateDatabase(dbname,context.MODE_PRIVATE,null);
        //Cursor cursor = db.rawQuery("select * from sqlite_master where type='table' and name='{"+days+"}'", null);
        Cursor cursor = db.rawQuery("select * from sqlite_master where tbl_name = '" + days+ "'", null);
        if (cursor.getCount() <=0) {
            cursor.close();
            return false;
        }
        cursor.close();
        return true;
    }
    public boolean checkDay(String days) {
        SQLiteDatabase  db=context.openOrCreateDatabase(dbname,context.MODE_PRIVATE,null);
        //Cursor cursor = db.rawQuery("select * from sqlite_master where type='table' and name='{"+days+"}'", null);
        Cursor cursor = db.rawQuery("select * from '"+ days+"'", null);
        if (cursor.getCount() <=0) {
            cursor.close();
            return false;
        }
        cursor.close();
        return true;
    }

}
