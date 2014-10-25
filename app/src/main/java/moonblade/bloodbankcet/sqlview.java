package moonblade.bloodbankcet;

import android.app.Activity;
import android.database.Cursor;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;
import moonblade.bloodbankcet.sqldb;
import moonblade.bloodbankcet.R;

public class sqlview extends Activity {

    private SimpleCursorAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_sqlview);
        ListView lv=(ListView)findViewById(R.id.try2);
getdata(lv);
//        TextView tv= (TextView)findViewById(R.id.info);
//        sqldb table = new sqldb(this);
//        table.open();
//        String data = table.getData();
//        table.close();
//        tv.setText(data);

    }


    private void getdata(ListView lv){
sqldb table = new sqldb(this);
        table.open();
        Cursor c=table.readData();
        c.moveToFirst();

        String[] columns = new String[] {table.KEY_NAME};
        int[] to = new int[]{R.id.set_name};
        adapter = new SimpleCursorAdapter(sqlview.this,R.layout.listviewlayout,c,columns,to,0);
        lv.setAdapter(adapter);

        table.close();
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
