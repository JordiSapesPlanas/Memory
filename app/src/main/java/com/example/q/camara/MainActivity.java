package com.example.q.camara;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

public class MainActivity extends ActionBarActivity implements View.OnClickListener {

    public static final String DEFAULT = "N?A";

    Button btn_search_opponent;
    Button btn_play;
    Button btn_login;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btn_search_opponent = (Button) findViewById(R.id.btn_search_opponent);
        btn_search_opponent.setOnClickListener(this);

        btn_play = (Button) findViewById(R.id.btn_play);
        btn_play.setOnClickListener(this);


        btn_login = (Button) findViewById(R.id.btn_login_main);
        btn_login.setOnClickListener(this);

        SharedPreferences sharedPreferences = getSharedPreferences("Data", Context.MODE_PRIVATE);
        String regId = sharedPreferences.getString("regId",DEFAULT);
//        if(regId.equals(DEFAULT))
            new GcmRegistrationAsyncTask(this).execute();

        System.out.println(regId);

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()){
            case R.id.btn_search_opponent :
                Intent intent = new Intent(this, CameraActivity.class);
                this.startActivity(intent);
                break;

            case R.id.btn_play :
                startActivity(new Intent(this, Configuration.class));
                break;

            case R.id.btn_login_main:
                startActivity(new Intent(this, LoginActivity.class));
                break;
        }


    }
}
