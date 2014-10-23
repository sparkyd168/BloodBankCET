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
                            KEY_NAME + " TEXT NOT NULL);"
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

    public long add(String nam) {

        ContentValues cv = new ContentValues();
        cv.put(KEY_NAME,nam);
        return ourdb.insert(DB_TABLE,null,cv);
    }

    public String getData() {
        String[] columns=new String[]{KEY_ROWID,KEY_NAME};
        Cursor c=ourdb.query(DB_TABLE,columns,null,null,null,null,null);
        String result = "";

        int irow=c.getColumnIndex(KEY_ROWID);
        int iname=c.getColumnIndex(KEY_NAME);

        for(c.moveToFirst();!c.isAfterLast();c.moveToNext()){
            result = result + c.getString(irow) + "  " + c.getString(iname) + " \n";
        }

        return result;
    }
}
