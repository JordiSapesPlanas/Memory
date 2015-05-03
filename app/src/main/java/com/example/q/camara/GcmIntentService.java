package com.example.q.camara;

import android.app.IntentService;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.text.format.Formatter;
import android.widget.Toast;

import com.google.android.gms.gcm.GoogleCloudMessaging;


import java.util.StringTokenizer;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
* Created by jordi on 07/04/2015.
*/
public class GcmIntentService extends IntentService {

    public GcmIntentService() {
        super("GcmIntentService");
    }

    // Variables de la notificacion
    NotificationManager nm;
    Notification notif;
    static String ns = Context.NOTIFICATION_SERVICE;

    //Defino los iconos de la notificacion en la barra de notificacion
    int icono_v = R.drawable.nortification2;
    int icono_r = R.drawable.notification;

    String port;
    String ip;
    String size;

    @Override
    protected void onHandleIntent(Intent intent) {
        Bundle extras = intent.getExtras();
        GoogleCloudMessaging gcm = GoogleCloudMessaging.getInstance(this);
        // The getMessageType() intent parameter must be the intent you received
        // in your BroadcastReceiver.
        String messageType = gcm.getMessageType(intent);

        if (extras != null && !extras.isEmpty()) {  // has effect of unparcelling Bundle
            // Since we're not using two way messaging, this is all we really to check for
            if (GoogleCloudMessaging.MESSAGE_TYPE_MESSAGE.equals(messageType)) {
                Logger.getLogger("GCM_RECEIVED").log(Level.INFO, extras.toString());

                tokenMsg(extras.getString("message"));

                WifiManager wifiManager = (WifiManager)getSystemService(WIFI_SERVICE);
                String myIp = Formatter.formatIpAddress(wifiManager.getConnectionInfo().getIpAddress());

                if(!myIp.equals(ip))
                    showNotification();
//              showToast(extras.getString("message"));
            }
        }
        GcmBroadcastReceiver.completeWakefulIntent(intent);
    }

    protected void showToast(final String message) {
        new Handler(Looper.getMainLooper()).post(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(getApplicationContext(), message, Toast.LENGTH_LONG).show();
            }
        });
    }

    private void showNotification(){
        // Inicio el servicio de notificaciones accediendo al servicio
        nm = (NotificationManager) getSystemService(ns);

        // Realizo una notificacion por medio de un metodo hecho por mi
        notificacion(icono_r, "play", "play", "play");

        // Lanzo la notificacion creada en el paso anterior
        nm.notify(1, notif);

    }


    public void notificacion(int icon, CharSequence textoEstado, CharSequence titulo, CharSequence texto) {
        // Capturo la hora del evento
        long hora = System.currentTimeMillis();

        // Definimos la accion de la pulsacion sobre la notificacion (esto es opcional)
        Context context = getApplicationContext();

        Bundle bundle = new Bundle();
        bundle.putString("ip",ip);
        bundle.putString("port",port);
        bundle.putString("role","client");
        bundle.putInt("size",new Integer(size));

        Intent notificationIntent = new Intent(this, GameActivity.class);
        notificationIntent.putExtras(bundle);

        PendingIntent contentIntent = PendingIntent.getActivity(this, 0, notificationIntent, 0);

        // Defino la notificacion, icono, texto y hora
        notif = new Notification(icon, textoEstado, hora);
        notif.setLatestEventInfo(context, titulo, texto, contentIntent);
        notif.flags = Notification.FLAG_AUTO_CANCEL;

        //Defino que la notificacion sea permamente
//        notif.flags = Notification.FLAG_ONGOING_EVENT;
//        nm.cancel(1);
    }

    private void tokenMsg(String msg){
        StringTokenizer tokens = new StringTokenizer(msg,"/");
        ip = tokens.nextToken();
        port = tokens.nextToken();
        size = tokens.nextToken();

        System.out.println(size);
    }

}