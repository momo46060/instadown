package com.bomb.app.mosalah;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

import java.util.ArrayList;

public  class DBconnection {
    DBinfo  dBinfo;
    public DBconnection(Context context) {
        dBinfo=new DBinfo(context);
    }

    public long inserdata(String pos){
        SQLiteDatabase sqLiteDatabase=dBinfo.getWritableDatabase();
        ContentValues contentValues=new ContentValues();
        contentValues.put(DBinfo.name,pos);

        long id=sqLiteDatabase.insert(DBinfo.tabelname,null,contentValues);
        sqLiteDatabase.close();
        return id;

    }

    public ArrayList ViewData(){
        SQLiteDatabase sqLiteDatabase=dBinfo.getWritableDatabase();
        String[] columns ={DBinfo.UID, DBinfo.name};
        Cursor cursor=sqLiteDatabase.query(DBinfo.tabelname,columns,null,null,null,null,null);
        ArrayList arrayList=new ArrayList();
        while (cursor.moveToNext()){
            int n=cursor.getColumnIndex(DBinfo.name);
            String nameee=cursor.getString(n);
            arrayList.add(nameee);
        }

        return arrayList;
    }

    public String serch(String n){
        SQLiteDatabase sqLiteDatabase=dBinfo.getWritableDatabase();
        String[] columns ={DBinfo.name};
        Cursor cursor=sqLiteDatabase.query(DBinfo.tabelname,columns, DBinfo.name+" = '"+n+"'",null,null,null,null);

        StringBuffer stringBuffer=new StringBuffer();
        while (cursor.moveToNext()){
            int n1=cursor.getColumnIndex(DBinfo.name);
//            int u=cursor.getColumnIndex(DBinfo.username);
//            int p=cursor.getColumnIndex(DBinfo.password);
            String nameee=cursor.getString(n1);
//            String usernammm=cursor.getString(u);
//            String passwordd=cursor.getString(p);

//            stringBuffer.append(nameee+" "+n+" "+passwordd+" "+"\n");
        }

        return stringBuffer.toString();
    }

    public int delet(String n){
        SQLiteDatabase sqLiteDatabase=dBinfo.getWritableDatabase();
        String[] wherargs ={n};
        String s= DBinfo.name+" =? ";
        int count=sqLiteDatabase.delete(DBinfo.tabelname,s,wherargs);
        return count;
    }





   static class DBinfo extends SQLiteOpenHelper {
        private static final String databasename="FAV";
        private static final String tabelname="fav2 ";
        private static final int database_virsion=10;
        private static final String name="Name";
        private static final String UID="id";
        private static final String DROP_TABLE="DROP TABLE IF EXISTS "+tabelname;
        private static final String CREAT_TABLE="CREATE TABLE "+tabelname+" ( "+
                UID+"  INTEGER PRIMARY KEY AUTOINCREMENT, "

                +name+" VARCHAR (255));";
        public Context context ;
        public DBinfo( Context context) {
            super(context,databasename , null,database_virsion );
            this.context=context;
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            try {
                Toast.makeText(context, "onCreate", Toast.LENGTH_LONG).show();
                db.execSQL(CREAT_TABLE);
            }catch (SQLException e){
            }
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            try {
                Toast.makeText(context, "onUpgrade", Toast.LENGTH_LONG).show();
                db.execSQL(DROP_TABLE);
                onCreate(db);
                Toast.makeText(context, "onUpgrade", Toast.LENGTH_LONG).show();

            }catch (SQLException e){
                Toast.makeText(context, "onUpgrade"+e.getMessage(), Toast.LENGTH_LONG).show();
            }
        }
    }

}