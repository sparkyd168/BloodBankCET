package moonblade.bloodbankcet;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

public class ViewBlood extends Activity {
    RadioGroup choice;
    RadioButton blood,branch,none;
    EditText choice_branch;
    Button filter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_blood);
        choice=(RadioGroup)findViewById(R.id.choice);
        branch=(RadioButton)findViewById(R.id.branch);
        none=(RadioButton)findViewById(R.id.none);
        blood=(RadioButton)findViewById(R.id.blood);
        choice_branch=(EditText)findViewById(R.id.choice_branch);
        filter=(Button)findViewById(R.id.filter);
        none.setChecked(true);

        String[] blood_groups = getResources().getStringArray(R.array.bloodgroups);
        ArrayAdapter adapter=new ArrayAdapter<String>(this, R.layout.blood_item, R.id.label, blood_groups);

        final ListView data =(ListView)findViewById(R.id.data);
        final ListView lv = (ListView)findViewById(R.id.listview);
        lv.setVisibility(View.INVISIBLE);
        choice_branch.setVisibility(View.INVISIBLE);
        lv.setAdapter(adapter);
        filter.setVisibility(View.INVISIBLE);

        none.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        none.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                
            }
        });
        branch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
choice_branch.setVisibility(View.VISIBLE);
                filter.setVisibility(View.VISIBLE);
            }
        });
        branch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
choice_branch.setVisibility(View.INVISIBLE);
                filter.setVisibility(View.INVISIBLE);
            }
        });
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {

                // selected item
                String blood_group = ((TextView) view).getText().toString();
                lv.setVisibility(View.INVISIBLE);
//                Toast.makeText(ViewBlood.this,blood_group,Toast.LENGTH_SHORT).show();
            }
        });

        blood.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                lv.setVisibility(View.VISIBLE);
            }
        });
        blood.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                lv.setVisibility(View.INVISIBLE);
            }
        });
        displaylist();
    }


private void displaylist(){
//    blooddb.open();
//    Cursor c=blooddb.readAll();

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
