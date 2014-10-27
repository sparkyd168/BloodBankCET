package moonblade.bloodbankcet;

import android.app.ActionBar;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.SearchManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.CursorAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.SearchView;
import android.widget.SimpleCursorAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;
import java.util.zip.Inflater;

public class ViewBlood extends Activity implements AdapterView.OnItemSelectedListener{

    ArrayList<String> blood_list;
    ArrayAdapter<String> blood_adapter;

    int long_clicked=0;
    int logged_in=0;
    Spinner spinner_blood;
    ImageView green;
    private CursorAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_blood);

        try{
            Intent logged = this.getIntent();
            if (logged!=null){
                logged_in=getIntent().getExtras().getInt("logged");
            }
        }
        catch (Exception e){

        }
        green=(ImageView)findViewById(R.id.green);
        initialise_adapter();

        String[] blood_groups = getResources().getStringArray(R.array.bloodgroups);
        ArrayAdapter adapter=new ArrayAdapter<String>(this, R.layout.blood_item, R.id.label, blood_groups);

        final ListView data =(ListView)findViewById(R.id.lvdata);
          getdatanone(data);



     data.setOnItemClickListener(new AdapterView.OnItemClickListener() {
         @Override
         public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {
             final Cursor cursor = (Cursor) data.getItemAtPosition(position);
             final Dialog dialog = new Dialog(ViewBlood.this);
             dialog.setContentView(R.layout.dialoglayout);
             dialog.setTitle("Detail of Student");

             TextView namea = (TextView) dialog.findViewById(R.id.tvdiagname);
             TextView brancha = (TextView) dialog.findViewById(R.id.tvdiagbranch);
             TextView bg = (TextView) dialog.findViewById(R.id.tvdiagbg);
             TextView mob = (TextView) dialog.findViewById(R.id.tvdiagmob);
             TextView hos = (TextView) dialog.findViewById(R.id.tvdiaghostel);
             namea.setText(cursor.getString(cursor.getColumnIndexOrThrow("_name")));
             brancha.setText(cursor.getString(cursor.getColumnIndexOrThrow("_branch")));
             bg.setText(cursor.getString(cursor.getColumnIndexOrThrow("_bg")));
             mob.setText(cursor.getString(cursor.getColumnIndexOrThrow("_phone")));
             final String num = cursor.getString(cursor.getColumnIndexOrThrow("_phone"));

             hos.setText(cursor.getString(cursor.getColumnIndexOrThrow("_hostel")));
             final Button dbitton = (Button) dialog.findViewById(R.id.bdiagdok);
             final Button callbutton = (Button) dialog.findViewById(R.id.bdiagcall);

            dbitton.setOnClickListener(new View.OnClickListener() {
                 @Override
                 public void onClick(View v) {
                     dialog.cancel();
                 }
             });
             callbutton.setOnClickListener(new View.OnClickListener() {
                 @Override
                 public void onClick(View v) {

//                     dialog.cancel();
                     Intent callIntent = new Intent(Intent.ACTION_CALL);
                     callIntent.setData(Uri.parse("tel:" + num));
                     startActivity(callIntent);

                 }
             });
             if (long_clicked == 0)
                 dialog.show();
             long_clicked = 0;
         }
     });
        data.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                final Cursor cursor = (Cursor) data.getItemAtPosition(position);
                final Dialog dialog = new Dialog(ViewBlood.this);
                long_clicked=1;

                dialog.setContentView(R.layout.long_click_layout);
                dialog.setTitle("Admin Only");
                final Button editbutton = (Button)dialog.findViewById(R.id.edit_button);
                final Button okbutton = (Button)dialog.findViewById(R.id.ok_button);
                final Button deletebutton = (Button)dialog.findViewById(R.id.delete_button);
                okbutton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.cancel();
                    }
                });
                editbutton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (logged_in == 0) {
                            Toast.makeText(ViewBlood.this, "You do not have permission for editing", Toast.LENGTH_SHORT).show();
                        } else {
                            String id = cursor.getString(cursor.getColumnIndexOrThrow("_id"));
                            String name = cursor.getString(cursor.getColumnIndexOrThrow("_name"));
                            String brancha = cursor.getString(cursor.getColumnIndexOrThrow("_branch"));
                            String bg = cursor.getString(cursor.getColumnIndexOrThrow("_bg"));
                            String mob = cursor.getString(cursor.getColumnIndexOrThrow("_phone"));
                            String hostel = cursor.getString(cursor.getColumnIndexOrThrow("_hostel"));
                            Bundle b = new Bundle();
                            b.putString("name", name);
                            b.putString("id", id);
                            b.putString("branch", brancha);
                            b.putString("bg", bg);
                            b.putString("mob", mob);
                            b.putString("hostel", hostel);
                            Intent i = new Intent(ViewBlood.this, editEntry.class);
                            i.putExtras(b);
                            startActivity(i);
                        }
                    }
                });
                final AlertDialog.Builder alert = new AlertDialog.Builder(ViewBlood.this);
                deletebutton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        alert.setTitle("Confirm Delete");
                        alert
                                .setMessage("Do you want to DELETE this entry?")
                                .setCancelable(false)
                                .setPositiveButton("Do it!",new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog,int id) {
                                        // if this button is clicked, close
                                        // current activity
                                        String name=cursor.getString(cursor.getColumnIndexOrThrow("_name"));
                                        sqldb vi = new sqldb(ViewBlood.this);
                                        vi.open();
                                        vi.delete(name);
                                        vi.close();
                                        Intent i = new Intent(getApplicationContext(), ViewBlood.class);
                                        startActivity(i);
                                        finish();

                                    }
                                })
                                .setNegativeButton("I need this",new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog,int id) {
                                        // if this button is clicked, just close
                                        // the dialog box and do nothing
                                        dialog.cancel();
                                    }
                                });
                        AlertDialog alertDialog = alert.create();

                        // show it
                        alertDialog.show();
                    }
                });
                if(logged_in==1)
                    dialog.show();
                return false;
            }
        });
    }

    private void initialise_adapter() {
        blood_list = new ArrayList<String>();
        blood_list.add("All");
        blood_list.add("A+");
        blood_list.add("A-");
        blood_list.add("B+");
        blood_list.add("B-");
        blood_list.add("O+");
        blood_list.add("O-");
        blood_list.add("AB+");
        blood_list.add("AB-");
        blood_adapter = new ArrayAdapter<String>(this,
                R.layout.spinner_action_bar, blood_list);
        blood_adapter.setDropDownViewResource(R.layout.spinner_dropdown);
     }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        String blood_group = parent.getItemAtPosition(position).toString();
        ListView data=(ListView)findViewById(R.id.lvdata);
        if(blood_group.equals("All")){
            getdatanone(data);
        }else{
            getdatablood(data,blood_group);
        }
        }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    private void getdatanone(ListView data){

        sqldb table = new sqldb(this);
        table.open();
        Cursor c=table.readAll();
        c.moveToFirst();
        String[] columns = new String[] {table.KEY_NAME,table.KEY_BG};
        int[] to = new int[]{R.id.set_name,R.id.set_bg};
        adapter = new SimpleCursorAdapter(ViewBlood.this,R.layout.listviewlayout,c,columns,to,0);
        data.setAdapter(adapter);

        table.close();
    }

    private void getdatablood(ListView data,String blood_group){

        sqldb table = new sqldb(this);
        table.open();
        Cursor c=table.readBlood(blood_group);
        c.moveToFirst();
        String[] columns = new String[] {table.KEY_NAME,table.KEY_BG};
        int[] to = new int[]{R.id.set_name,R.id.set_bg};
        adapter = new SimpleCursorAdapter(ViewBlood.this,R.layout.listviewlayout,c,columns,to,0);
        data.setAdapter(adapter);

        table.close();
    }

    private void getdataBranch(ListView data,String branch){

        sqldb table = new sqldb(this);
        table.open();
        Cursor c=table.readBranch(branch);
        c.moveToFirst();
        String[] columns = new String[] {table.KEY_NAME,table.KEY_BG};
        int[] to = new int[]{R.id.set_name,R.id.set_bg};
        adapter = new SimpleCursorAdapter(ViewBlood.this,R.layout.listviewlayout,c,columns,to,0);
        data.setAdapter(adapter);

        table.close();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.viewblood_action, menu);
        spinner_blood=(Spinner)menu.findItem(R.id.blood_spinner2).getActionView();
        spinner_blood.setVisibility(View.INVISIBLE);
        spinner_blood.setAdapter(blood_adapter);

        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        SearchView searchView = (SearchView) menu.findItem(R.id.action_search)
                .getActionView();
        searchView.setQueryHint("Branch");
        if (null != searchView) {
            searchView.setSearchableInfo(searchManager
                    .getSearchableInfo(getComponentName()));
            searchView.setIconifiedByDefault(false);
        }

        SearchView.OnQueryTextListener queryTextListener = new SearchView.OnQueryTextListener() {
            public boolean onQueryTextChange(String newText) {
                // this is your adapter that will be filtered
                ListView data=(ListView)findViewById(R.id.lvdata);
                getdataBranch(data,newText);
//                spinner_blood.getSelectedItemPosition()
                spinner_blood.setSelection(0);
                return true;
            }

            public boolean onQueryTextSubmit(String query) {
                //Here u can get the value "query" which is entered in the search box.
//                Toast.makeText(ViewBlood.this,query,Toast.LENGTH_SHORT).show();
                ListView data=(ListView)findViewById(R.id.lvdata);
                getdataBranch(data,query);
                spinner_blood.setSelection(0);
                return true;
            }
        };
        searchView.setOnQueryTextListener(queryTextListener);

        spinner_blood.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String blood_group = parent.getItemAtPosition(position).toString();
                ListView data=(ListView)findViewById(R.id.lvdata);
                if(blood_group.equals("All")){
                    getdatanone(data);
                }else{
                    getdatablood(data,blood_group);
                }
//                if (((TextView) view) != null) {
//                    ((TextView) view).setText(null);
//                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

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
        if (id==R.id.action_search){

        }
        return super.onOptionsItemSelected(item);
    }


}
