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
                            KEY_ROWID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                            KEY_NAME + " TEXT NOT NULL, " +
                            KEY_BG + " TEXT NOT NULL);"
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
    public long addone(String nam) {

        ContentValues cv = new ContentValues();
        cv.put(KEY_NAME,nam);
        return ourdb.insert(DB_TABLE,null,cv);
    }

    public Cursor readAll(){
        Cursor c=ourdb.query(DB_TABLE,new String[]{KEY_ROWID,KEY_NAME,KEY_BG},null,null,null,null,null);
        if (c!=null)
            c.moveToFirst();
        return c;
    }

    public String getData() {
        String[] columns=new String[]{KEY_ROWID,KEY_NAME,KEY_BG};
        Cursor c=ourdb.query(DB_TABLE,columns,null,null,null,null,null);
        String result = "";

        int irow=c.getColumnIndex(KEY_ROWID);
        int iname=c.getColumnIndex(KEY_NAME);
        int ibg=c.getColumnIndex(KEY_BG);

        for(c.moveToFirst();!c.isAfterLast();c.moveToNext()){
            result = result + c.getString(irow) + "  " + c.getString(iname) + " " + c.getString(ibg) + " \n";
        }

        return result;
    }

    public Cursor readData() {
        String[] allColumns = new String[] { KEY_ROWID,KEY_NAME };
        Cursor c = ourdb.query(DB_TABLE, allColumns, null,null, null, null, null);
        if (c != null) {
            c.moveToFirst();
        }
        return c;
    }

    public int updateData(long memberID, String memberName, String memberbg) {
        ContentValues cvUpdate = new ContentValues();
        cvUpdate.put(KEY_NAME, memberName);
        cvUpdate.put(KEY_BG, memberbg);
        int i = ourdb.update(DB_TABLE, cvUpdate,
                KEY_ROWID + " = " + memberID, null);
        return i;
    }

    public void deleteData(long memberID) {
        ourdb.delete(DB_TABLE, KEY_ROWID + "="
                + memberID, null);
    }

}
