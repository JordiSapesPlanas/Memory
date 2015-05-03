
package com.example.q.camara;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.Toast;


import com.example.q.camara.backend.messaging.Messaging;
import com.example.q.camara.backend.play.Play;

import com.example.q.camara.backend.play.model.PlayRecord;
import com.google.android.gms.gcm.GoogleCloudMessaging;
import com.google.api.client.extensions.android.http.AndroidHttp;
import com.google.api.client.extensions.android.json.AndroidJsonFactory;

import java.io.IOException;


/**
* Created by q on 27/04/15.
*/
public class SearchOpponentAsyncTask extends AsyncTask<String, Void, String> {
    private static final String DEFAULT = "N?A";
//    private static final String SENDER_ID = "369393698491";

    private static Play playService = null;
    private static Messaging messagingService = null;
    private Context context;
    private String ip;
    private Integer mida;
    String regId;

    public SearchOpponentAsyncTask(Context context, String ip, Integer mida){
        this.context = context;
        this.ip = ip;
        this.mida = mida;
    }

    @Override
    protected String doInBackground(String... params) {
        if (playService == null) {
            Play.Builder builder = new Play.Builder(AndroidHttp.newCompatibleTransport(), new AndroidJsonFactory(), null)
                    .setRootUrl("https://ferrous-octane-93011.appspot.com/_ah/api/");

            playService = builder.build();
        }
        if(messagingService == null){
            Messaging.Builder messageBuilder = new Messaging.Builder(AndroidHttp.newCompatibleTransport(), new AndroidJsonFactory(), null)
                    .setRootUrl("https://ferrous-octane-93011.appspot.com/_ah/api/");

            messagingService = messageBuilder.build();
        }

//        SharedPreferences sharedPreferences = context.getSharedPreferences("Data", Context.MODE_PRIVATE);
//        regId = sharedPreferences.getString("regId",DEFAULT);
//
//        if(!regId.equals(DEFAULT)){
//            try {
//
//                PlayRecord playRecord = playService.play(ip, mida, regId).execute();
//
//                System.out.println("my: "+ip);
//                System.out.println("my: "+regId);
//                System.out.println("my: "+mida);
//
//                if(playRecord!=null){
//                    messagingService.messagingEndpoint().sendMessage
//                            ("you are invited to play. Opponent ip: " + ip, playRecord.getRegId()).execute();
//                    System.out.println(playRecord.getRegId());
//                }else
//                    System.out.println("there are no player");
//
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
            String port = "3000";

            try {
                messagingService.messagingEndpoint().
                        sendMessage(ip+"/"+port+"/"+mida.toString()).execute();

            } catch (IOException e) {
                e.printStackTrace();
            }

        return null;
    }


}
