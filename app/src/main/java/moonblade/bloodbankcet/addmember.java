package moonblade.bloodbankcet;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.GregorianCalendar;

import moonblade.bloodbankcet.R;

public class addmember extends Activity {
    Button baddentry;
    EditText etdbname,etdbclass,etdbmob,etdbhostel;
//    private int day,month,year;
    Spinner add_spinner;
    DatePicker datePicker;
    static final int DATE_DIALOG_ID = 999;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addmember);
        baddentry=(Button)findViewById(R.id.baddentry);
        etdbname=(EditText)findViewById(R.id.etdbname);
        etdbclass=(EditText)findViewById(R.id.etdbclass);
        etdbmob=(EditText)findViewById(R.id.etmob);
        etdbhostel=(EditText)findViewById(R.id.ethostel);
        datePicker=(DatePicker)findViewById(R.id.date_Picker);
        final String[] bloodgroup = new String[1];
        add_spinner=(Spinner)findViewById(R.id.add_spinner);
        add_spinner.setPrompt("Blood Group");
        add_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                bloodgroup[0] = add_spinner.getSelectedItem().toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });



        baddentry.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final int day = datePicker.getDayOfMonth();
                final int month = datePicker.getMonth();
                final int year = datePicker.getYear();
                final Date date = new Date(year,month,day);

                final long millisec=date.getTime();

                String name=etdbname.getText().toString();
                String clas=etdbclass.getText().toString();
                String mob=etdbmob.getText().toString();
                String hostel=etdbhostel.getText().toString();
                String bg=bloodgroup[0];
                sqldb add = new sqldb(addmember.this);
                Date dAte = new Date(millisec);
                SimpleDateFormat df2 = new SimpleDateFormat("dd/MM/yy");

//                Toast.makeText(addmember.this,df2.format(dAte),Toast.LENGTH_SHORT).show();

                add.open();
                add.addData(name, bg, clas, mob, hostel,millisec);
                add.close();
                Toast.makeText(getApplicationContext(), "Entry Successfull", Toast.LENGTH_SHORT).show();
                Intent i=new Intent(addmember.this,Home.class);
                i.putExtra("logged",1);
                startActivity(i);
                finish();
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
