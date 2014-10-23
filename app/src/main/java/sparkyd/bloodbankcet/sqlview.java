package moonblade.bloodbankcet;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.database.Cursor;
import android.os.Bundle;
import android.util.SparseBooleanArray;
import android.view.ActionMode;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;
import android.widget.Toast;

import moonblade.bloodbankcet.sqldb;
import moonblade.bloodbankcet.R;


public class sqlview extends Activity {

    Cursor c;
    private SimpleCursorAdapter dataAdapter;
    static ListView listview;
    sqldb view;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sqlview);

//        TextView tv= (TextView)findViewById(R.id.info);
//        sqldb table = new sqldb(this);
//        table.open();
//        String data = table.getData();
//        table.close();
//        tv.setText(data);
        listview=(ListView)findViewById(R.id.listviewname);
        view = new sqldb(sqlview.this);
        displaylistview();

    }

    private void displaylistview() {
        view.open();

        c = view.fecthalladmins();
        String[] columns = new String[] {sqldb.KEY_NAME};
        int[] to = new int[]{R.id.tvdname};
        dataAdapter = new SimpleCursorAdapter(sqlview.this,R.layout.activity_sqlview,c,columns,to,0);
        listview.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE_MODAL);
        listview.setMultiChoiceModeListener(new ModeCallback());
        listview.setAdapter(dataAdapter);



        view.close();
    }

    class ModeCallback implements ListView.MultiChoiceModeListener {

        public boolean onCreateActionMode(ActionMode mode, Menu menu) {
            MenuInflater inflater = getMenuInflater();
            inflater.inflate(R.menu.list_layout_menu, menu);
            mode.setTitle("Select Items");
            return true;
        }

    public boolean onPrepareActionMode(ActionMode mode, Menu menu) {
        return true;
    }

    public boolean onActionItemClicked(ActionMode mode, MenuItem item) {
        switch (item.getItemId()) {
            case R.id.share:
                final int c=listview.getCheckedItemCount();
                final int count = listview.getCount();
                final SparseBooleanArray checked = listview.getCheckedItemPositions();
                final AlertDialog.Builder alert = new AlertDialog.Builder(sqlview.this);
                alert.setTitle("Confirm Delete");
                alert
                        .setMessage("Do you want to DELETE these entry?")
                        .setCancelable(false)
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                // if this button is clicked, close
                                // current activity
                                view.open();
                                for (int i = 0; i < count; i++) {
                                    if (checked.valueAt(i) == true) {
                                        Cursor cursor = (Cursor)( dataAdapter.getItem(checked.keyAt(i)));


                                        view.deleteadmin(cursor.getString(cursor.getColumnIndexOrThrow("_username")));

                                    }

                                }
                                view.close();
                                Toast.makeText(sqlview.this, "Deleted " + c +
                                        " items", Toast.LENGTH_SHORT).show();

                                displaylistview();
                            }
                        })
                        .setNegativeButton("No", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                // if this button is clicked, just close
                                // the dialog box and do nothing
                                dialog.cancel();
                            }
                        });
                AlertDialog alertDialog = alert.create();

                // show it
                alertDialog.show();
                mode.finish();
                break;
            default:
                Toast.makeText(sqlview.this, "Clicked " + item.getTitle(),
                        Toast.LENGTH_SHORT).show();
                break;
        }
        return true;
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
