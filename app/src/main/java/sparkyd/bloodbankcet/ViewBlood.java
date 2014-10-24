package moonblade.bloodbankcet;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ExpandableListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;
import android.view.View;
import android.widget.ExpandableListView;
import android.widget.ExpandableListView.OnChildClickListener;
import android.widget.ExpandableListView.OnGroupClickListener;
import android.widget.ExpandableListView.OnGroupCollapseListener;
import android.widget.ExpandableListView.OnGroupExpandListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import moonblade.bloodbankcet.R;
import moonblade.bloodbankcet.ExpandableListAdapter;

public class ViewBlood extends Activity {
RadioGroup choice,bg;
    RadioButton blood,branch;
    ExpandableListAdapter listAdapter;
    ExpandableListView expListView;
    List<String> listDataHeader;
    HashMap<String, List<String>> listDataChild;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_blood);
        choice=(RadioGroup)findViewById(R.id.choice);
        blood=(RadioButton)findViewById(R.id.blood);
        branch=(RadioButton)findViewById(R.id.branch);
        expListView=(ExpandableListView)findViewById(R.id.ExpList);
//        expListView.setVisibility(View.INVISIBLE);
        prepareListData();
        listAdapter = new ExpandableListAdapter(this, listDataHeader, listDataChild);

        // setting list adapter
        expListView.setAdapter(listAdapter);





blood.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
        expListView.setVisibility(View.VISIBLE);
    }
});

}

    private void prepareListData() {
        listDataHeader = new ArrayList<String>();
        listDataChild = new HashMap<String, List<String>>();

        // Adding child data
        listDataHeader.add("Choose");

//        Adding child data
        List<String> blood_group = new ArrayList<String>();
        blood_group.add("A+");
        blood_group.add("A-");
        blood_group.add("B+");
        blood_group.add("B-");
        blood_group.add("O+");
        blood_group.add("O-");
        blood_group.add("AB+");
        blood_group.add("AB-");

        listDataChild.put(listDataHeader.get(0), blood_group); // Header, Child data
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
