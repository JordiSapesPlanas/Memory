package com.example.q.camara.Statistics;

/**
 * Created by Joaquim on 29/05/2015.
 */

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;


import android.content.Context;
import android.content.SharedPreferences;
import android.os.Handler;
        import android.os.Message;
        import android.util.Log;

        import org.json.JSONObject;

        import java.io.IOException;
        import java.io.OutputStream;
        import java.net.HttpURLConnection;
        import java.net.URL;

/**
 * Created by q on 27/05/15.
 */
public class HttpThread extends Thread {


    private Handler h;
    private Context context;
    private String method;
    private JSONObject jsonObject;
    private String url;
    private OutputStream outputStream;
    private Message message;

    public HttpThread(String method, String url, JSONObject jsonObject, Handler h, Context c) {
        this.method = method;
        this.context = c;
        this.url = url;
        this.jsonObject = jsonObject;
        this.h = h;
    }

    @Override
    public void run() {
        try {
            String ip = "192.168.1.128";
            String port = "3000";
            URL ur = new URL("http://" + ip + ":" + port + "/" + url);
            HttpURLConnection httpURLConnection = (HttpURLConnection) ur.openConnection();
            if (method.equals("GET")) {
                httpURLConnection.setDoOutput(false);
                httpURLConnection.setRequestMethod("GET");

                if (httpURLConnection.getResponseCode() == 200) {
                    message = new Message();
                    message.obj = "ha arribat correctament";
                    h.sendMessage(message);
                } else {
                    Log.e("****",ur.toString());
                    message = new Message();
                    message.obj = "received" + httpURLConnection.getResponseCode();
                    h.sendMessage(message);
                }
            }
            else if(method.equals("POST")){
                SharedPreferences sharedPreferences = context.getSharedPreferences("Data", Context.MODE_PRIVATE);
                String cookie = sharedPreferences.getString("cookie","A?N");
                httpURLConnection.setRequestProperty("cookie",cookie);
                httpURLConnection.setDoOutput(true);
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setRequestProperty("Content-Type", "application/json; charset=UTF-8");
                outputStream = httpURLConnection.getOutputStream();
                outputStream.write(jsonObject.toString().getBytes("UTF-8"));
                if (httpURLConnection.getResponseCode() == 200) {
                    message = new Message();
                        BufferedReader br = new BufferedReader(new InputStreamReader((httpURLConnection.getInputStream())));
                        StringBuilder sb = new StringBuilder();
                        String output;

                        while ((output = br.readLine()) != null) {
                            sb.append(output);
                        }
                    message.obj = new JSONObject(sb.toString());
                    h.sendMessage(message);

                } else {
                    message = new Message();
                    message.obj = "received" + httpURLConnection.getResponseCode();
                    h.sendMessage(message);
                }
            }
            else if(method.equals("PUT")){
                httpURLConnection.setDoOutput(true);
                httpURLConnection.setRequestMethod("PUT");
                httpURLConnection.setRequestProperty("Content-Type", "application/json; charset=UTF-8");
                outputStream = httpURLConnection.getOutputStream();
                outputStream.write(jsonObject.toString().getBytes("UTF-8"));
                if (httpURLConnection.getResponseCode() == 200) {
                    message = new Message();
                    message.obj = "ha arribat PUT correctament";
                    h.sendMessage(message);

                } else {
                    message = new Message();
                    message.obj = "received" + httpURLConnection.getResponseCode();
                    h.sendMessage(message);
                }
            }
            else if(method.equals("DELETE")){
                httpURLConnection.setDoOutput(true);
                httpURLConnection.setRequestMethod("DELETE");
                httpURLConnection.setRequestProperty("Content-Type", "application/json; charset=UTF-8");
                outputStream = httpURLConnection.getOutputStream();
                outputStream.write(jsonObject.toString().getBytes("UTF-8"));
                if (httpURLConnection.getResponseCode() == 200) {
                    message = new Message();
                    message.obj = "ha arribat DELETE correctament";
                    h.sendMessage(message);

                } else {
                    message = new Message();
                    message.obj = "received" + httpURLConnection.getResponseCode();
                    h.sendMessage(message);
                }
            }

        /*} catch (IOException e) {
            message = new Message();
            message.obj = e.toString();
            h.sendMessage(message);
*/
        } catch (Exception e) {
            Log.e("uuuuuu", e.toString());
            e.printStackTrace();
        }
    }
}