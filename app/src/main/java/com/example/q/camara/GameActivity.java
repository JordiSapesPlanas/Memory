package com.example.q.camara;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.GridView;
import android.widget.Toast;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketAddress;
import java.nio.channels.ServerSocketChannel;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.logging.LogRecord;

public class GameActivity extends ActionBarActivity implements SensorEventListener{
    private List<Cell> oldNumbers = new ArrayList<>();
    private int mida;
    private GridView gv;
    private int nMoves;
    private Player player1;
    private SensorManager sensorManager;
    private Sensor sensorLight;
    private View view;
    private String ip;
    //private String role ;
    Handler handler;
    private Tauler tauler;
    private SocketConection con;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (savedInstanceState==null) {
            Bundle b = getIntent().getExtras();
            mida = b.getInt("size");
            SharedPreferences s = getSharedPreferences("Data", MODE_PRIVATE);
            String username = s.getString("email","N?A");

            tauler = new Tauler(initlnumbers(), 0);
            oldNumbers = copyList(tauler.getTauler());
            player1 = new Player(b.getString("role"), username ); // TODO CAUGHT NAME FROM SHARED PREFERENCES
            if(player1.getRole().equals("server")){
                player1.setIsMyTurn(true);
            }else{
                ip = b.getString("ip");
                player1.setIsMyTurn(false);
            }
        }else{
            mida = savedInstanceState.getInt("size");
            player1 = (Player)savedInstanceState.getSerializable("player1");
            tauler = (Tauler) savedInstanceState.getSerializable("tauler");
            oldNumbers = copyList(tauler.getTauler());
        }
        sensorManager = (SensorManager)getSystemService(Context.SENSOR_SERVICE);
        setContentView(R.layout.activity_game);
        view = this.getWindow().getDecorView();
        gv = (GridView) findViewById(R.id.gridview1);
        gv.setNumColumns(4);

        handler = new Handler(){
            @Override
            public void handleMessage(Message msg) {
                String s = (String)msg.obj;
                if(msg.what == 1){
                    try {
                        tauler.setTauler(oldNumbers);
                        Thread.sleep(2000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                if(msg.what == 2){
                    Toast.makeText(getApplicationContext(), "It's your turn", Toast.LENGTH_SHORT).show();
                }
                gv.setAdapter(new NumberAdapter(GameActivity.this));



            }
        };
        con = new SocketConection();
        con.start();

    }

    private List<Cell> copyList(List<Cell> src) {
        List<Cell> destination = new ArrayList<>();
        for (Cell c : src){
            Cell c1 = new Cell(c.getHiddenNumber());
            c1.setClicked(c.isClicked());
            c1.setPlayer(c.getPlayer());
            destination.add(c1);
        }
        return destination;
    }

    private List<Cell> initlnumbers() {  // TODO Guardar persistentment no fer de forma aleatoria
        List<Cell> lnumbers = new ArrayList<>();
        int a=1, i=1, j=0;
        int lnumbers2[] = new int[mida];
        boolean c = false;

        for(int x = 0; x < mida; x++){
            lnumbers2[x] = 0;
        }
        while(i<=mida/2){
            j = (int) (Math.random()*mida+1);
            c = false;
            while(c!=true){
                if(lnumbers2[j-1] == 0) {
                    lnumbers2[j - 1] = i;
                    if (a == 2) {
                        i++;
                        a = 1;
                        c = true;
                    } else {
                        a++;
                        j = (int) (Math.random() * mida + 1);
                    }
                }
                else{

                        j = (int) (Math.random()*mida+1);
                }
            }
        }

        for(int x = 0; x<mida;x++){
            lnumbers.add(new Cell(Integer.toString(lnumbers2[x])));
        }

        return lnumbers;
    }
    @Override
    protected void onResume() {
        super.onResume();
        sensorLight = sensorManager.getDefaultSensor(Sensor.TYPE_LIGHT);
        if(sensorLight != null){
            sensorManager.registerListener(this, sensorLight, SensorManager.SENSOR_DELAY_NORMAL);
        }
        gv.setAdapter(new NumberAdapter(this));

    }

    @Override
    public void onBackPressed() {
        con.closeSocket();
        this.finish();
        super.onBackPressed();
    }

    @Override
    public void onSensorChanged(SensorEvent sensorEvent) {
        modifyBackground(sensorEvent);
    }
    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {

    }
    private void modifyBackground(SensorEvent sensorEvent) {

        if(sensorEvent.values[0]<20){
            view.setBackgroundColor(Color.BLACK);
        }else {
            view.setBackgroundColor(Color.GRAY);
        }
    }
    @Override
    protected void onPause() {
        super.onPause();
        sensorManager.unregisterListener(this);
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
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putSerializable("tauler", tauler);
        outState.putSerializable("player1", player1);
        outState.putInt("size", mida);
        //outState.putInt("nMoves", nMoves);
    }
    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
    }
    private class SocketConection extends Thread {
        private ServerSocket serverSocket;
        private Socket socket;
        ObjectInputStream input;
        ObjectOutputStream output;

        int port = 8080;

        @Override
        public void run() {
            tauler.setPlayer(player1);
            Message msg;
            if (player1.getRole().equals("server")) {
                try {
                    serverSocket = new ServerSocket(port);

                    //serverSocket.setSoTimeout(1000000000);
                    new Thread(){
                        @Override
                        public void run() {
                            //try {
                                while (tauler.getMove2() == null) {
                                    //sleep(500); per consumir menys recursos.
                                }
                                player1.setIsMyTurn(false);
//                            } catch (InterruptedException e) {
//                                e.printStackTrace();
//                            }
                        }
                    }.start();

                    socket = serverSocket.accept();

                    doMove();
                    while (tauler.getAllPoints() < tauler.getTauler().size()/2) {

                        waitMove();
                        msg = new Message();
                        msg.what = 2;
                        msg.obj = "socket connection";
                        handler.sendMessage(msg);
                        doMove();
                    }

                } catch (Exception ignored) {

                    GameActivity.this.finish();
                    GameActivity.this.startActivity(new Intent(getApplicationContext(), ResumeActivity.class ));
                }finally {
                    try {
                        serverSocket.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    finish();
                }
            }

            if (GameActivity.this.player1.getRole().equals("client")) {

                try {

                    sleep(2000);
                    socket = new Socket(ip, port);
                    tauler.setPlayer(player1);

                    while (tauler.getAllPoints() < tauler.getTauler().size()/2 ){
                        waitMove();
                        msg = new Message();
                        msg.what = 2;

                        String s = "socket connection";
                        msg.obj = s;
                        handler.sendMessage(msg);
                        doMove();
                    }
                } catch (Exception e) {

                    GameActivity.this.finish();
                    GameActivity.this.startActivity(new Intent(getApplicationContext(), ResumeActivity.class));
                }finally {
                    finish();
                }

            }



        }
        private void finish() {
            if (socket != null && !socket.isClosed()) {
                String winner = tauler.getWinner();
                try {
                    if (tauler.getFirstAskWinner()) {
                        output = new ObjectOutputStream(socket.getOutputStream());
                        output.writeObject(tauler);
                    }
                    this.closeSocket();
                } catch (Exception e) {

                }finally{
                    GameActivity.this.finish();
                    GameActivity.this.startActivity(new Intent(getApplicationContext(), ResumeActivity.class).putExtra("winner", winner));
                }


            }
        }

        private void waitMove() throws IOException, InterruptedException, ClassNotFoundException {
            int i=0;
            while(socket.getInputStream().available()<=0 && i<120){
                sleep(1000); i++;
            }
            input = new ObjectInputStream(socket.getInputStream());
            player1.setIsMyTurn(true);
            tauler = (Tauler)input.readObject();
            oldNumbers = copyList(tauler.getTauler());
        }
        private void doMove() throws IOException, InterruptedException {
            if(tauler.getAllPoints() < tauler.getTauler().size()/2) {
                while (tauler.getMove2() == null) {
                    sleep(1000);
                }
                sleep(2000);
                tauler.reset();
                output = new ObjectOutputStream(socket.getOutputStream());
                output.writeObject(tauler);
                player1.setIsMyTurn(false);
            }
        }
        public void closeSocket(){
            try {
                if (!serverSocket.isClosed()) {
                    serverSocket.close();
                }
                if (!socket.isClosed()) {
                    socket.close();
                }
            }catch(Exception e){}
        }

    }
    public class NumberAdapter extends BaseAdapter {
        private Context mContext;

        public NumberAdapter(Context c) {
            this.mContext = c;
        }

        @Override
        public int getCount() {
            return tauler.getTauler().size();
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {
            Button btn;
            if (convertView == null) {  // if it's not recycled, initialize some attributes
                btn = new Button(mContext);
                btn.setLayoutParams(new GridView.LayoutParams( GridView.LayoutParams.MATCH_PARENT,100));
                //btn.setScaleType(ImageView.ScaleType.CENTER_CROP);
                btn.setPadding(8, 8, 8, 8);
            } else {
                btn = (Button) convertView;
            }

            btn.setText(tauler.getTauler().get(position).getNumber());
            btn.setBackgroundColor(tauler.getTauler().get(position).getColor());
            btn.setId(position);
            btn.setOnClickListener(new OnMyClickListener());
            return btn;
        }
    }
    private class OnMyClickListener implements View.OnClickListener {

        @Override
        public void onClick(View v) {
            Integer i = v.getId();
            Button b = (Button) findViewById(v.getId());
            if(!tauler.getTauler().get(i).isClicked() && player1.getIsMyTurn())
            {
                tauler.setMove(i);

                b.setText(tauler.getTauler().get(i).getNumber());
                tauler.getTauler().get(i).setPlayer(player1);
                b.setBackgroundColor(tauler.getTauler().get(i).getColor());
                if(tauler.getTurn() == 2){
                    tauler.resetTurn();
                    player1.setIsMyTurn(false);

                    if(tauler.compareMoves()){
                        player1.addPoint();
                        tauler.setPlayer(player1);
                    }else{
                        tauler.setTauler(oldNumbers);
                        Message msg = new Message();
                        msg.obj = "hello";
                        msg.what = 1;
                        handler.sendMessage(msg);
                    }
                }


            }
            else{
                Toast.makeText(GameActivity.this, "Is Clicked or isn't your turn\n TRY AGAIN", Toast.LENGTH_SHORT).show();
            }
        }
    }


}
