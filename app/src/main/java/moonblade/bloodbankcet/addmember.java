package moonblade.bloodbankcet;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Toast;

import moonblade.bloodbankcet.R;

public class addmember extends Activity {
    Button baddentry;
    EditText etdbname,etdbclass,etdbmob,etdbhostel;
    RadioGroup rg;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addmember);
        baddentry=(Button)findViewById(R.id.baddentry);
        etdbname=(EditText)findViewById(R.id.etdbname);
        etdbclass=(EditText)findViewById(R.id.etdbclass);
        etdbmob=(EditText)findViewById(R.id.etmob);
        etdbhostel=(EditText)findViewById(R.id.ethostel);
        rg=(RadioGroup)findViewById(R.id.rg);
        final String[] bloodgroup = new String[1];
        rg.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId)
                {
                    case R.id.rap:
                        bloodgroup[0] ="A+";
                        break;
                    case R.id.ran:
                        bloodgroup[0] ="A-";
                        break;
                    case R.id.rbp:
                        bloodgroup[0] ="B+";
                        break;
                    case R.id.rbn:
                        bloodgroup[0] ="B-";
                        break;
                    case R.id.rabp:
                        bloodgroup[0] ="AB+";
                        break;
                    case R.id.rabn:
                        bloodgroup[0] ="AB-";
                        break;
                    case R.id.rop:
                        bloodgroup[0] ="O+";
                        break;
                    case R.id.ron:
                        bloodgroup[0] ="O-";
                        break;
                }
            }
        });
        baddentry.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name=etdbname.getText().toString();
                String clas=etdbclass.getText().toString();
                String mob=etdbmob.getText().toString();
                String hostel=etdbhostel.getText().toString();
                String bg=bloodgroup[0];
                blooddb add = new blooddb(addmember.this);
                add.open();
                add.addEntry(name,  bg, clas, mob, hostel);
                add.close();
                Toast.makeText(getApplicationContext(), "Entry Successfull", Toast.LENGTH_SHORT).show();
                Intent i=new Intent(addmember.this,Home.class);
                startActivity(i);
            }
        });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.addmember, menu);
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
