package moonblade.bloodbankcet;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import java.sql.SQLException;

import moonblade.bloodbankcet.R;

public class sqldb{

    public static final String KEY_ROWID = "_id";
    public static final String KEY_NAME = "_name";
    public static final String KEY_BG = "_bg";
    public static final String KEY_BRANCH="_branch";
    public static final String KEY_PHONE="_phone";
    public static final String KEY_HOSTEL="_hostel";

    private static final String DB_NAME = "_db";
    private static final String DB_TABLE = "_table";
    private static final int DB_VERSION = 1;

    private dbhelper ourhelper;
    private final Context ourcontext;
    private SQLiteDatabase ourdb;




    private static class dbhelper extends SQLiteOpenHelper{

        public dbhelper (Context context){
            super(context,DB_NAME,null,DB_VERSION);

        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

            db.execSQL("DROP TABLE IF EXISTS " + DB_TABLE);
            onCreate(db);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            db.execSQL("CREATE TABLE " + DB_TABLE + " (" +
                            KEY_ROWID +  " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                            KEY_NAME +   " TEXT NOT NULL, " +
                            KEY_BG +     " TEXT NOT NULL, " +
                            KEY_BRANCH + " TEXT NOT NULL, " +
                            KEY_PHONE +  " TEXT NOT NULL, " +
                            KEY_HOSTEL + " TEXT NOT NULL);"
            );
        }


    }

    public sqldb(Context c){
        ourcontext=c;
    }

    public  sqldb open(){
        ourhelper = new dbhelper(ourcontext);
        ourdb = ourhelper.getWritableDatabase();
        return this;
    }

    public void close(){
        ourhelper.close();
    }

    public long add(String nam,String bgr) {

        ContentValues cv = new ContentValues();
        cv.put(KEY_NAME,nam);
        cv.put(KEY_BG,bgr);
        return ourdb.insert(DB_TABLE,null,cv);
    }

    public long addData(String nam,String bgr,String branch, String phone, String hostel) {

        ContentValues cv = new ContentValues();
        cv.put(KEY_NAME,nam);
        cv.put(KEY_BG,bgr);
        cv.put(KEY_BRANCH,branch);
        cv.put(KEY_PHONE,phone);
        cv.put(KEY_HOSTEL,hostel);
        return ourdb.insert(DB_TABLE,null,cv);
    }

    public Cursor readAll(){
        Cursor c=ourdb.query(DB_TABLE,new String[]{KEY_ROWID,KEY_NAME,KEY_BG,KEY_BRANCH,KEY_PHONE,KEY_HOSTEL},null,null,null,null,null);
        if (c!=null)
            c.moveToFirst();
        return c;
    }

    public Cursor readBlood(String s) {
        Cursor mc = null;

        if(s==null||s.length()==0)
        {
            mc= ourdb.query(DB_TABLE,new String[]{KEY_ROWID,KEY_NAME,KEY_BG,KEY_BRANCH,KEY_PHONE,KEY_HOSTEL},null,null,null,null,null);
        }
        else
        {
            mc= ourdb.query(DB_TABLE,new String[]{KEY_ROWID,KEY_NAME,KEY_BG,KEY_BRANCH,KEY_PHONE,KEY_HOSTEL},KEY_BG+" like '%"+ s+"%'",null,null,null,null,null);
        }
        if(mc!=null)
            mc.moveToFirst();
        return mc;
    }

    public Cursor readBranch(String s) {
        Cursor mc = null;
        if(s==null||s.length()==0)
        {
            mc= ourdb.query(DB_TABLE,new String[]{KEY_ROWID,KEY_NAME,KEY_BG,KEY_BRANCH,KEY_PHONE,KEY_HOSTEL},null,null,null,null,null);
        }
        else
        {
            mc= ourdb.query(DB_TABLE,new String[]{KEY_ROWID,KEY_NAME,KEY_BG,KEY_BRANCH,KEY_PHONE,KEY_HOSTEL},KEY_BRANCH+" like '%"+ s+"%'",null,null,null,null,null);
        }
        if(mc!=null)
            mc.moveToFirst();
        return mc;
    }

    public int updateData(long memberID, String memberName, String memberbg, String memberbranch, String memberphone, String memberhostel) {
        ContentValues cvUpdate = new ContentValues();
        cvUpdate.put(KEY_NAME, memberName);
        cvUpdate.put(KEY_BG, memberbg);
        cvUpdate.put(KEY_BRANCH,memberbranch);
        cvUpdate.put(KEY_PHONE,memberphone);
        cvUpdate.put(KEY_HOSTEL,memberhostel);
        int i = ourdb.update(DB_TABLE, cvUpdate,
                KEY_ROWID + " = " + memberID, null);
        return i;
    }

    public void deleteData(long memberID) {
        ourdb.delete(DB_TABLE, KEY_ROWID + "="
                + memberID, null);
    }

}
