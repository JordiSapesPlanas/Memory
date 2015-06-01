package com.example.q.camara.Statistics;

/**
 * Created by Joaquim on 29/05/2015.
 */

        import android.content.Context;
        import android.content.SharedPreferences;
        import android.os.Handler;
        import android.os.Message;
        import android.support.v7.app.ActionBarActivity;
        import android.os.Bundle;
        import android.support.v7.widget.LinearLayoutManager;
        import android.util.Log;
        import android.view.Menu;
        import android.view.MenuItem;
        import android.support.v7.widget.RecyclerView;

        import com.example.q.camara.R;

        import org.json.JSONArray;
        import org.json.JSONException;
        import org.json.JSONObject;

        import java.io.StringWriter;
        import java.util.ArrayList;
        import java.util.List;



public class StatisticsActivity extends ActionBarActivity {

    RecyclerView recyclerView;
    Handler h;
    HttpThread thread1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_statistics);


        recyclerView = (RecyclerView) findViewById(R.id.rView_statistics);

        List<StatisticsInfo> statisticsInfo = new ArrayList<>();


        JSONObject object = new JSONObject();
        try {
            object.put("winner","joaquim");
        } catch (JSONException e) {
            e.printStackTrace();
        }

        /*JSONObject object2 = new JSONObject();
        try {
            object2.put("winner",false);
            object2.put("opponent","Jordi");
            object2.put("date","03/30/04");
        } catch (JSONException e) {
            e.printStackTrace();
        }

        JSONArray jsonArray = new JSONArray();
        jsonArray.put(object);
        //jsonArray.put(object2);

        JSONObject obj=new JSONObject();
        try {
            obj.put("data",jsonArray);
        } catch (JSONException e) {
            e.printStackTrace();
        }*/


        /*try {
            JSONArray jsonArray1 = obj.getJSONArray("data");
            for(int i=0; i<jsonArray1.length(); i++){
                JSONObject jsonObject = (JSONObject) jsonArray.get(i);
                String opponent = jsonObject.get("opponent").toString();
                String date = jsonObject.get("date").toString();
                Boolean isWon = jsonObject.getBoolean("winner");

                statisticsInfo.add(new StatisticsInfo(opponent,date,isWon));
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }*/

//
//        JSONObject obj2=new JSONObject();
//        try {
//            obj2.put("obj",obj);
//        } catch (JSONException e) {
//            e.printStackTrace();
//        }
//
        /*try {
            System.out.println(obj2.getJSONArray("obj"));
        } catch (JSONException e) {
            e.printStackTrace();
        }*/



        /*statisticsInfo.add(new StatisticsInfo("H","1",false));
        statisticsInfo.add(new StatisticsInfo("E","2",true));
        statisticsInfo.add(new StatisticsInfo("R","3",true));
        statisticsInfo.add(new StatisticsInfo("G","4",true));
        statisticsInfo.add(new StatisticsInfo("T","5",true));
        statisticsInfo.add(new StatisticsInfo("B","6",true));
        statisticsInfo.add(new StatisticsInfo("J","7",true));
        statisticsInfo.add(new StatisticsInfo("L","1",true));
        statisticsInfo.add(new StatisticsInfo("Ñ","2",true));*/


        StatisticsInfoAdapter statisticsInfoAdapter = new StatisticsInfoAdapter(statisticsInfo,this);
        recyclerView.setAdapter(statisticsInfoAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));


    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_statistics, menu);
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