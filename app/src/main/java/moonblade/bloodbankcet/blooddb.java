package moonblade.bloodbankcet;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class blooddb{

    public static final String KEY_ROWID="_id";
    public static final String KEY_NAME="_name";
    public static final String KEY_BG="_bg";
    public static final String KEY_BRANCH="_branch";
    public static final String KEY_PHONE="_phone";
    public static final String KEY_HOSTEL="_hostel";

    private static final String DB_NAME="_db";
    private static final String DB_TABLE="_table";
    private static final int DB_VERSION=2;

    private dbhelper ourhelper;
    private final Context ourcontext;
    private SQLiteDatabase ourdb;


    public blooddb(Context c){
        ourcontext=c;
    }

    public blooddb open(){
        ourhelper = new dbhelper(ourcontext);
        ourdb = ourhelper.getWritableDatabase();
        return this;
    }

    public void close(){
        ourhelper.close();
    }

    private static class dbhelper extends SQLiteOpenHelper{

        public dbhelper(Context context){
            super(context,DB_NAME,null,DB_VERSION);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            db.execSQL("create table " + DB_TABLE + " ("
                    + KEY_ROWID + " integer primary key autoincrement, "
                    + KEY_NAME + " text not null, "
                    + KEY_BG + " text not null, "
                    + KEY_BRANCH + " text not null, "
                    + KEY_PHONE + " text not null "
                    + KEY_HOSTEL + " text not null);");
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            db.execSQL("drop table if exists " + DB_TABLE);
            onCreate(db);
        }
    }



    public long addEntry(String name,String bg,String branch, String phone, String hostel ){
        ContentValues cv=new ContentValues();
        cv.put(KEY_NAME,name);
        cv.put(KEY_BG,bg);
        cv.put(KEY_BRANCH,branch);
        cv.put(KEY_PHONE,phone);
        cv.put(KEY_HOSTEL,hostel);
        return ourdb.insert(DB_TABLE,null,cv);
    }

    public void delete (String position){
        ourdb.delete(DB_TABLE,KEY_NAME + "=?",new String[] {position});
    }

    public void editEntry(String id,String name,String bg,String branch, String phone, String hostel ){
        ContentValues cv = new ContentValues();
        cv.put(KEY_NAME,name);
        cv.put(KEY_BG,bg);
        cv.put(KEY_BRANCH,branch);
        cv.put(KEY_PHONE,phone);
        cv.put(KEY_HOSTEL,hostel);
        int id1= Integer.parseInt(id);
        ourdb.update(DB_TABLE,cv,KEY_ROWID + "=?" + id1,null);
    }

    public Cursor readAll(){
        Cursor c=ourdb.query(DB_TABLE,new String[]{KEY_ROWID,KEY_NAME,KEY_BRANCH,KEY_BG,KEY_PHONE,KEY_HOSTEL},null,null,null,null,null);
        if (c!=null)
           c.moveToFirst();
        return c;
    }

    public Cursor readBG(String s) {
        Cursor mc = null;

        if(s==null||s.length()==0)
        {
            mc= ourdb.query(DB_TABLE,new String[]{KEY_ROWID,KEY_NAME,KEY_BRANCH,KEY_BG,KEY_PHONE,KEY_HOSTEL},null,null,null,null,null);
        }
        else
        {
            mc= ourdb.query(DB_TABLE,new String[]{KEY_ROWID,KEY_NAME,KEY_BRANCH,KEY_BG,KEY_PHONE,KEY_HOSTEL},KEY_BG+" like '%"+ s+"%'",null,null,null,null,null);
        }
        if(mc!=null)
            mc.moveToFirst();
        return mc;
    }

}
