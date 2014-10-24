package moonblade.bloodbankcet;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import moonblade.bloodbankcet.R;

public class LoginPage extends Activity {
    EditText username,password;
    Button action_login;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_page);
        username=(EditText)findViewById(R.id.username);
        password=(EditText)findViewById(R.id.password);
        action_login=(Button)findViewById(R.id.button_login);


        action_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int log=0;
                String user=username.getText().toString();
                String pass=password.getText().toString();

                String login_user="Nisham";
                String login_pass="pass";

                if(user.equals(login_user) && pass.equals(login_pass)){
                    Toast.makeText(LoginPage.this,"Success",Toast.LENGTH_SHORT).show();
                    log=1;
                }

                if(log==1){
                    Intent logged_in=new Intent(LoginPage.this,Home.class);
                    logged_in.putExtra("logged",log);
                    startActivity(logged_in);
                    finish();

                }else{
                    password.setText("");
                    Toast.makeText(LoginPage.this,"Username or Password incorrect",Toast.LENGTH_SHORT).show();

                }
            }
        });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.login_page, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            Toast.makeText(LoginPage.this,"Not implemented yet",Toast.LENGTH_SHORT).show();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent back=new Intent(LoginPage.this,Home.class);
        back.putExtra("logged",0);
        startActivity(back);
        finish();
    }
}
