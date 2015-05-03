package com.example.q.camara;

import android.app.ListActivity;
import android.content.Intent;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.text.format.Formatter;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.StringTokenizer;


public class Configuration extends ListActivity{
   private String[] sizesPanel;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_configuration);
        sizesPanel = new String[]{ "12 Cells ",  "16 Cells" ,"20 Cells", "24 Cells","28 Cells", "32 Cells"};
        ArrayAdapter adapter = new ArrayAdapter<>(this, android.R.layout.simple_expandable_list_item_1, sizesPanel);
        setListAdapter(adapter);

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_configuration, menu);
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
    protected void onListItemClick(ListView l, View v, int position, long id) {
        super.onListItemClick(l, v, position, id);

        WifiManager wifiManager = (WifiManager)getSystemService(WIFI_SERVICE);
        String ip = Formatter.formatIpAddress(wifiManager.getConnectionInfo().getIpAddress());


        String size = sizesPanel[position];
        StringTokenizer s = new StringTokenizer(size);
        int mida = Integer.parseInt(s.nextToken());
        Intent i = new Intent(this, SearchOpponentActivity.class);
        Bundle b = new Bundle();
        b.putInt("size",mida);
        b.putString("role","server");
        i.putExtra("configuration", b);


//        System.out.println(ip);
        new SearchOpponentAsyncTask(this, ip, mida).execute();

        startActivity(i);
        finish();
    }

}
