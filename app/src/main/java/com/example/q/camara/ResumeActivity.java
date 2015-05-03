package com.example.q.camara;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;


public class ResumeActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_resume);
        Button bt_play_again = (Button)findViewById(R.id.button_play_again);
        TextView tv_title = (TextView) findViewById(R.id.textview_resume_title);
        TextView tv_winner = (TextView) findViewById(R.id.textview_winner_player);
        String winner = getIntent().getStringExtra("winner");
        if(winner != null) {
            if (winner.equals("empat")) {
                tv_title.setText("");
                tv_winner.setText(winner);
            } else {
                tv_winner.setText(winner);
            }
        }else{
            tv_title.setText("error de conexio");
        }
        bt_play_again.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(ResumeActivity.this, Configuration.class));
                finish();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_resume, menu);
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
}
