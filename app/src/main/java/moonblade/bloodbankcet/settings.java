package moonblade.bloodbankcet;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class settings extends Activity {
int logged_in = 0;
    Button save,cancel;
    EditText months;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        save=(Button)findViewById(R.id.save);
        cancel=(Button)findViewById(R.id.cancel);
        months=(EditText)findViewById(R.id.number_of_months);

        try {
            Intent logged = this.getIntent();
            if (logged != null) {
                SharedPreferences prefs = getSharedPreferences("Preferences", MODE_PRIVATE);
                logged_in=prefs.getInt("Logged_in", 0);
            }
        } catch (Exception e) {

        }

        if (logged_in == 1)
            invalidateOptionsMenu();

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                int safe_months=Integer.parseInt(months.getText().toString());
                if(safe_months>2) {
                    SharedPreferences.Editor editor = getSharedPreferences("Preferences", MODE_PRIVATE).edit();
                    editor.putInt(String.valueOf(R.string.pref_months), safe_months);
                    editor.commit();
                }else{
                    Toast.makeText(settings.this,"Minimum Value is two")
                }

            }
        });

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.settings, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        return super.onOptionsItemSelected(item);
    }
}
