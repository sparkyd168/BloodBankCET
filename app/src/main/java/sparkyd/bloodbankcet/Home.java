package moonblade.bloodbankcet;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import sparkyd.bloodbankcet.ViewBlood;
import sparkyd.bloodbankcet.settings;


public class Home extends Activity {
int logged_in=0;
    Button sql,viewblood;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);


        viewblood=(Button)findViewById(R.id.viewblood);
        sql=(Button)findViewById(R.id.sql);

        viewblood.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent viewBlood=new Intent(Home.this, ViewBlood.class);
                startActivity(viewBlood);
            }
        });
        sql.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent sqlite=new Intent(Home.this, moonblade.bloodbankcet.sqltest.class);
                startActivity(sqlite);

            }
        });


        try{
            Intent logged = this.getIntent();
            if (logged!=null){
                logged_in=getIntent().getExtras().getInt("logged");
            }
        }
        catch (Exception e){

        }

        if(logged_in==1)
            invalidateOptionsMenu();
    }




    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.home, menu);
        if (logged_in==1) {

            MenuItem loginn=menu.findItem(R.id.action_login);
            loginn.setVisible(false);


        }

        if (logged_in==0) {

            MenuItem logout=menu.findItem(R.id.action_logout);
            logout.setVisible(false);
//            MenuItem admin=menu.findItem(R.id.action_admin);
//            admin.setVisible(false);

        }

        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        if (id == R.id.action_settings) {
            Intent setting = new Intent(Home.this,settings.class);
            setting.putExtra("admin", logged_in);
            startActivity(setting);
        }
        if (id == R.id.action_login) {
            Intent login =new Intent(Home.this,LoginPage.class);
            startActivity(login);
            finish();
        }
        if (id == R.id.action_logout){
            Intent logout =new Intent (Home.this,Home.class);
            logout.putExtra("logged",0);
            startActivity(logout);
            finish();
        }
        return super.onOptionsItemSelected(item);
    }
}
