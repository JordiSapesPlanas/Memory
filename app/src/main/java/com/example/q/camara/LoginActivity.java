package com.example.q.camara;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.q.camara.Statistics.HttpThread;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


public class LoginActivity extends ActionBarActivity implements View.OnClickListener{

    public static final String DEFAULT = "N?A";

    EditText et_email;
    EditText et_password;
    Button btn_logIn_log;
    Button btn_back;
    HttpThread thread1;
    Handler h;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        et_email = (EditText)findViewById(R.id.et_email);
        et_password = (EditText)findViewById(R.id.et_password);
        btn_logIn_log = (Button)findViewById(R.id.btn_logIn_log);
        btn_back = (Button)findViewById(R.id.btn_back);

        btn_logIn_log.setOnClickListener(this);
        btn_back.setOnClickListener(this);

        isUserAlreadyExist();
    }

    public void isUserAlreadyExist(){
        SharedPreferences sharedPreferences = getSharedPreferences("Data",Context.MODE_PRIVATE);
        String email = sharedPreferences.getString("email",DEFAULT);
        String password = sharedPreferences.getString("password",DEFAULT);

        if(!email.equals(DEFAULT))
            et_email.setText(email);
        if(!password.equals(DEFAULT))
            et_password.setText(password);
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_login, menu);
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
            case R.id.btn_logIn_log:
                /*if(et_email.getText().length()!=0 && et_password.getText().length()!=0 ){



                    SharedPreferences sharedPreferences = getSharedPreferences("Data", Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putString("email",et_email.getText().toString());
                    editor.putString("password",et_password.getText().toString());
                    editor.commit();





                    Toast.makeText(this,et_email.getText().toString()+" login successful",Toast.LENGTH_LONG).show();
                    startActivity(new Intent(this,MainActivity.class));
                    finish();
                }else
                    Toast.makeText(this,"login failed",Toast.LENGTH_LONG).show();
                break;
                */
                if (et_email.getText().length() != 0 && et_password.getText().length() != 0) {



                    JSONObject object = new JSONObject();
                    try {
                        object.put("username", et_email.getText().toString());
                        object.put("password", et_password.getText().toString());
                    }catch (JSONException e) {
                        e.printStackTrace();
                    }
                    h = new Handler() {
                        @Override
                        public void handleMessage(Message msg) {
                            super.handleMessage(msg);
                            JSONObject object2 = (JSONObject) msg.obj;
                            //Log.e("++++++++++++++++",object2.toString());
                            editor.putString("email", et_email.getText().toString());
                            editor.putString("password", et_password.getText().toString());
                            try {
                                JSONArray jsonArray = object2.getJSONArray("data");
                                JSONObject jsonObject2 = (JSONObject) jsonArray.get(0);
                                String id = (String) jsonObject2.get("_id");
                                String cookie = (String) jsonObject2.get("cookie");
                                editor.putString("id",id);
                                editor.putString("cookie",cookie);
                                editor.commit();
                                //Log.e("AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA",id+" "+cookie);
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    };
                    thread1 = new HttpThread("POST", "users/", object, h,this);
                    thread1.start();



                    Toast.makeText(this, et_email.getText().toString() + " login successful", Toast.LENGTH_LONG).show();
                    startActivity(new Intent(this, MainActivity.class));
                    finish();

                }else
                    Toast.makeText(this,"login failed",Toast.LENGTH_LONG).show();
                break;
            case R.id.btn_back:
                finish();
        }

    }
}
