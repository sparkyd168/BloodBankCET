package sparkyd.bloodbankcet;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ExpandableListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import moonblade.bloodbankcet.R;

public class ViewBlood extends Activity {
RadioGroup choice,bg;
    RadioButton blood,branch;
    ExpandableListView el;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_blood);
        choice=(RadioGroup)findViewById(R.id.choice);
        blood=(RadioButton)findViewById(R.id.blood);
        branch=(RadioButton)findViewById(R.id.branch);
        el=(ExpandableListView)findViewById(R.id.ExpList);
        el.setVisibility(View.INVISIBLE);
        blood.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            el.setVisibility(View.VISIBLE);
            }
        });
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
