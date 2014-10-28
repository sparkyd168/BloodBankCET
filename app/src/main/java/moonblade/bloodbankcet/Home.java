package moonblade.bloodbankcet;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.SQLException;

import au.com.bytecode.opencsv.CSVReader;
import au.com.bytecode.opencsv.CSVWriter;


public class Home extends Activity {
    private int logged_in=0,is_admin=0;
    Button sql,viewblood,add;
    View seperatorview,seperatoradd;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        try{
            Intent logged = this.getIntent();
            if (logged!=null){
                SharedPreferences prefs = getSharedPreferences("Preferences", MODE_PRIVATE);
                logged_in=prefs.getInt("Logged_in", 0);
                is_admin=prefs.getInt(getResources().getString(R.string.pref_is_admin), 0);
            }
        }
        catch (Exception e){

        }

        if(logged_in==1)
            invalidateOptionsMenu();

        add=(Button)findViewById(R.id.add);
        viewblood=(Button)findViewById(R.id.viewblood);
        sql=(Button)findViewById(R.id.sql);
        seperatoradd=(View)findViewById(R.id.seperatoradd);
        seperatorview=(View)findViewById(R.id.seperatorview);

        sql.setVisibility(View.INVISIBLE);
        seperatoradd.setVisibility(View.INVISIBLE);

        if(logged_in==0){
            add.setVisibility(View.INVISIBLE);
            seperatorview.setVisibility(View.INVISIBLE);
        }else{
            add.setVisibility(View.VISIBLE);
            seperatorview.setVisibility(View.VISIBLE);
        }

        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(Home.this,addmember.class);
                startActivity(i);
            }
        });
        viewblood.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent view=new Intent(Home.this,ViewBlood.class);
                view.putExtra("logged",logged_in);
                startActivity(view);
            }
        });
        sql.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent sqlite=new Intent(Home.this,sqltest.class);
                startActivity(sqlite);

            }
        });



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
        }
        if(is_admin==0){
            MenuItem export=menu.findItem(R.id.action_export);
            export.setVisible(false);
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
        if (id == R.id.action_export) {
            try {
                CSVWriter writer = new CSVWriter(new FileWriter("filename"), '\t');
                sqldb rs=new sqldb(Home.this);
                rs.open();
                java.sql.ResultSet myResultSet = (java.sql.ResultSet) (java.sql.ResultSet) rs.readAll();
                rs.close();
                writer.writeAll(myResultSet,true);
            } catch (FileNotFoundException e) {
//                Toast.makeText(Home.this,"ERROR1",Toast.LENGTH_SHORT).show();

                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
                Toast.makeText(Home.this,"ERROR2",Toast.LENGTH_SHORT).show();
            } catch (SQLException e) {
                e.printStackTrace();
                Toast.makeText(Home.this,"ERROR3",Toast.LENGTH_SHORT).show();
            }


            return  true;
        }
        if (id == R.id.action_import) {
            return true;
        }
        if (id == R.id.action_logout){
            SharedPreferences.Editor editor = getSharedPreferences("Preferences", MODE_PRIVATE).edit();
            editor.putInt("Logged_in",0);
            editor.commit();
            Intent logout =new Intent (Home.this,Home.class);
            startActivity(logout);
            finish();
        }
        return super.onOptionsItemSelected(item);
    }
}
