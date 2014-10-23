package moonblade.bloodbankcet;

import android.app.Activity;
import android.database.Cursor;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;
import moonblade.bloodbankcet.sqldb;
import moonblade.bloodbankcet.R;

public class sqlview extends Activity {
ListView lv=(ListView)findViewById(R.id.list);
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sqlview);

        TextView tv= (TextView)findViewById(R.id.info);
        sqldb table = new sqldb(this);
        table.open();
//        String data = table.getData();
//        table.close();
//        tv.setText(data);

        Cursor cursor = sqldb.readData();
        String[] from = new String[] { KEY_ROWID, dbhelper.MEMBER_NAME };

        int[] to = new int[] { R.id., R.id.name };

        SimpleCursorAdapter adapter = new SimpleCursorAdapter(
                moonblade.bloodbankcet.Home.this, R.layout.view_member_entry, cursor, from, to);

        adapter.notifyDataSetChanged();
        lv.setAdapter(adapter);


    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.sqlview, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
