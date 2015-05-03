package com.example.q.camara;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.widget.Toast;

/**
 * Created by q on 16/03/15.
 */
public class SearchOpponentActivity extends ActionBarActivity {

    float getX;
    float getY;
    private int mida;
    private String oponent="";
    private String role;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_opponent);

        Intent intent = getIntent();

        if(intent.getBundleExtra("configuration")!=null){
            Bundle b = intent.getBundleExtra("configuration");
            mida = b.getInt("size");
            role = b.getString("role");
            //demenar al backend un oponent
            //Toast.makeText(this,mida.toString(),Toast.LENGTH_LONG).show();
        }
        else{
            getX = intent.getBundleExtra("camera").getFloat("getX");
            getY = intent.getBundleExtra("camera").getFloat("getY");
            mida = 20;
            // trobar opoment a la pos x i pos y
            // una vegada trobats guarda oponent a oponent
            Toast.makeText(this, "position touched--> "+ "X: " + getX + ", Y:" + getY, Toast.LENGTH_LONG).show();
        }

        new WaiteOpponent().execute();

    }

    private class WaiteOpponent extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... params) {
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            Bundle b = new Bundle();
            b.putInt("size", mida);
            b.putString("oponent", oponent);
            b.putString("ip", null);
            b.putString("role", role);
            startActivity(new Intent(SearchOpponentActivity.this, GameActivity.class).putExtras(b));

            SearchOpponentActivity.this.finish();
        }
    }

}