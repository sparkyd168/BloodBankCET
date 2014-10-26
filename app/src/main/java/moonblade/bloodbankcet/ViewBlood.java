package moonblade.bloodbankcet;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.CursorAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.SimpleCursorAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class ViewBlood extends Activity implements AdapterView.OnItemSelectedListener{
    int long_clicked=0;
    int logged_in=0;
    Spinner blood_spinner;
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
        blood_spinner=(Spinner)findViewById(R.id.blood_spinner);

        blood_spinner.setOnItemSelectedListener(ViewBlood.this);

        List<String> list = new ArrayList<String>();
        list.add("All");
        list.add("A+");
        list.add("A-");
        list.add("B+");
        list.add("B-");
        list.add("O+");
        list.add("O-");
        list.add("AB+");
        list.add("AB-");
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, list);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        blood_spinner.setAdapter(dataAdapter);

        final String[] option = {"none"};
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

//             Button bdiagedit = (Button)dialog.findViewById(R.id.bdiagedit);
//             Button bdel = (Button)dialog.findViewById(R.id.bdiagdelete);
//             final AlertDialog.Builder alert = new AlertDialog.Builder(ViewFullDatabase.this);
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

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        String blood_group = parent.getItemAtPosition(position).toString();
//        Toast.makeText(ViewBlood.this,blood_group,Toast.LENGTH_SHORT).show();
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
        //There is some syntax error in blooddb database, so it force closes
//    blooddb table = new blooddb(this);
//
//    table.open();
//    Cursor c=table.readAll();
//    c.moveToFirst();
//
//    String[] columns = new String[] {table.KEY_NAME,table.KEY_BG};
//    int[] to = new int[]{R.id.set_name,R.id.set_bg};
//    adapter = new SimpleCursorAdapter(ViewBlood.this,R.layout.listviewlayout,c,columns,to,0);
//    data.setAdapter(adapter);
//
//    table.close();

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
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.view_blood, menu);
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
