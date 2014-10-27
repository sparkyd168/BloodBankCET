package moonblade.bloodbankcet;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import moonblade.bloodbankcet.R;

public class editEntry extends Activity {

    Button beditentry;
    EditText etdbname,etdbbranch,etdbmob,etdbhostel;
    RadioGroup rg;
    String name;
    RadioButton ap,an,bp,bn,abp,abn,op,on;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_entry);
        beditentry = (Button) findViewById(R.id.beeditentry);
        etdbname = (EditText) findViewById(R.id.etedbname);
        etdbbranch = (EditText) findViewById(R.id.etedbbranch);
        ap = (RadioButton) findViewById(R.id.reap);
        an = (RadioButton) findViewById(R.id.rean);
        bp = (RadioButton) findViewById(R.id.rebp);
        bn = (RadioButton) findViewById(R.id.rebn);
        abp = (RadioButton) findViewById(R.id.reabp);
        abn = (RadioButton) findViewById(R.id.reabn);
        op = (RadioButton) findViewById(R.id.reop);
        on = (RadioButton) findViewById(R.id.reon);
        etdbmob = (EditText) findViewById(R.id.etemob);
        etdbhostel = (EditText) findViewById(R.id.etehostel);
        rg = (RadioGroup) findViewById(R.id.rge);
        final String[] bloodgroup = new String[1];
        Bundle b = getIntent().getExtras();
        name = b.getString("name");
        String brancha = b.getString("branch");
        String bg = b.getString("bg");
        final String id = b.getString("id");
        String mob = b.getString("mob");
        String hostel = b.getString("hostel");
        etdbname.setText(name);
        etdbbranch.setText(brancha);
        etdbhostel.setText(hostel);
        etdbmob.setText(mob);
        if (bg.equals("A+")) {

            bloodgroup[0] = "A+";
            ap.setChecked(true);
        } else if (bg.equals("A-")) {
            bloodgroup[0] ="A-";
            an.setChecked(true);
        } else if (bg.equals("B+")) {
            bloodgroup[0] ="B+";
            bp.setChecked(true);
        } else if (bg.equals("B-")) {
            bloodgroup[0] ="B-";
            bn.setChecked(true);
        } else if (bg.equals("AB+")) {
            bloodgroup[0] ="AB+";
            abp.setChecked(true);
        }
        else if(bg.equals("AB-")) {
            bloodgroup[0] ="AB-";
            abn.setChecked(true);
        }
        else if(bg.equals("O+"))
        {bloodgroup[0] ="O+";
            op.setChecked(true);}
        else if(bg.equals("O-")){
            bloodgroup[0] ="O-";
            on.setChecked(true);}
        rg.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId)
                {
                    case R.id.reap:
                        bloodgroup[0] ="A+";
                        break;
                    case R.id.rean:
                        bloodgroup[0] ="A-";
                        break;
                    case R.id.rebp:
                        bloodgroup[0] ="B+";
                        break;
                    case R.id.rebn:
                        bloodgroup[0] ="B-";
                        break;
                    case R.id.reabp:
                        bloodgroup[0] ="AB+";
                        break;
                    case R.id.reabn:
                        bloodgroup[0] ="AB-";
                        break;
                    case R.id.reop:
                        bloodgroup[0] ="O+";
                        break;
                    case R.id.reon:
                        bloodgroup[0] ="O-";
                        break;
                }
            }
        });
        beditentry.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String namea=etdbname.getText().toString();
                String branc=etdbbranch.getText().toString();
                String mob=etdbmob.getText().toString();
                String hostel=etdbhostel.getText().toString();
                String bg=bloodgroup[0];
                sqldb add = new sqldb(editEntry.this);
                add.open();

//                add.updateData(Long.parseLong(id), namea, bg, branc, mob, hostel);
                add.close();
                Toast.makeText(getApplicationContext(), "Edit Successfull", Toast.LENGTH_SHORT).show();
                Intent i=new Intent(editEntry.this,ViewBlood.class);
                i.putExtra("logged",1);
                startActivity(i);
            }
        });
    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.edit_entry, menu);
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
