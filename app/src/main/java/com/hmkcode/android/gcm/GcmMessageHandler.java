package com.hmkcode.android.gcm;

import com.google.android.gms.gcm.GoogleCloudMessaging;

import android.app.IntentService;
import android.app.NotificationManager;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.provider.Settings;
import android.support.v4.app.NotificationCompat;
import android.util.Log;
import android.widget.Toast;

import org.json.simple.parser.ParseException;
import org.notifyme.currency.model.Notification;
import org.notifyme.currency.model.Pair;

public class GcmMessageHandler extends IntentService {

     String mes;
     private Handler handler;
	public GcmMessageHandler() {
		super("GcmMessageHandler");
	}

	@Override
	public void onCreate() {
		// TODO Auto-generated method stub
		super.onCreate();
		handler = new Handler();
	}
	@Override
	protected void onHandleIntent(Intent intent) {
        Bundle extras = intent.getExtras();
        
        GoogleCloudMessaging gcm = GoogleCloudMessaging.getInstance(this);
        // The getMessageType() intent parameter must be the intent you received
        // in your BroadcastReceiver.
        String messageType = gcm.getMessageType(intent);

        String notifications = extras.getString("notifications");
        Notification.Parser p = new Notification.Parser();
        try {
            for(Notification n: p.collectionFromJson(notifications)) {
                n.apply();
                Log.i("GCM", n.toString());
            }
        } catch (ParseException e) {
            Log.e("GCM", "Failed to parse notification", e);
        }
        Pair.Parser parser = new Pair.Parser();
        Pair pair = null;
        try {
            pair = parser.fromJson(extras.getString("pair"));
        } catch (ParseException e) {
            Log.e("GCM", "Failed to parse pair", e);
        }

       String newRate = extras.getString("new_rate");
       mes = String.format("New rate for %s -> %s: %s", pair.first(), pair.second(), newRate);
       showToast();
       sendNotification(intent);
       Log.i("GCM", "Received : (" +messageType+")  "+extras.getString("title"));
       String androidId = Settings.Secure.getString(this.getContentResolver(),
               Settings.Secure.ANDROID_ID);
       Log.i("GCM", "Android id: " + androidId);



        GcmBroadcastReceiver.completeWakefulIntent(intent);

	}
	public void sendNotification(Intent intent) {
        NotificationCompat.Builder mBuilder =
                new NotificationCompat.Builder(this)
                        .setContentTitle(mes)
                        .setContentText(mes)
                        .setSmallIcon(R.drawable.common_signin_btn_icon_pressed_light);
        int mNotificationId = 001;
        NotificationManager mNotifyMgr =
                (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        mNotifyMgr.notify(mNotificationId, mBuilder.build());

    }

	public void showToast(){
		handler.post(new Runnable() {
		    public void run() {
		        Toast.makeText(getApplicationContext(),mes , Toast.LENGTH_LONG).show();
		    }
		 });

	}
	
	

}
