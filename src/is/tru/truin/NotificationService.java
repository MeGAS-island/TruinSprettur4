package is.tru.truin;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.IBinder;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.TaskStackBuilder;
import android.util.Log;
import android.view.View;

public class NotificationService extends Service{
	
	private NotificationReceiver alarm;
	
	 public void onCreate(Bundle savedInstanceState) {
	    super.onCreate();
	    alarm = new NotificationReceiver();
     }
	
	 
	@SuppressWarnings("deprecation")
	public void onStart(Intent intent, int startID){
		this.getApplicationContext();
		/*NotificationCompat.Builder builder = new NotificationCompat.Builder(this);
		builder.setContentTitle("Bæn dagsins");
		builder.setContentText("test");
		builder.setTicker("ný skilaboð");*/
		
		Log.d("not", "create notfication");
		/*
		Intent resultIntent = new Intent(this, MainActivity.class);
		TaskStackBuilder stackBuilder = TaskStackBuilder.create(this);
		stackBuilder.addNextIntent(resultIntent);
		
		PendingIntent pendingIntent = stackBuilder.getPendingIntent(0, PendingIntent.FLAG_UPDATE_CURRENT);
		builder.setContentIntent(pendingIntent);
		
		NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
		notificationManager.notify(0, builder.build());
		Log.d("not", "send notification");*/
	/*	NotificationManager mManager = (NotificationManager) this.getApplicationContext()
                .getSystemService(                                                         
                		Context.NOTIFICATION_SERVICE);
		Intent intent1 = new Intent(this.getApplicationContext(),
                MainActivity.class);
		
		Notification notification = new Notification(R.drawable.ic_logo,
                "Ertu búin að fara í gegnum bænastundina í dag?", System.currentTimeMillis());

                 intent1.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP
                  | Intent.FLAG_ACTIVITY_CLEAR_TOP);

                 PendingIntent pendingNotificationIntent = PendingIntent.getActivity(
                 this.getApplicationContext(), 0, intent1,
                 PendingIntent.FLAG_UPDATE_CURRENT);

                 notification.flags |= Notification.FLAG_AUTO_CANCEL;

                notification.setLatestEventInfo(this.getApplicationContext(), "Trúin og lífið",
                "Ertu búin að fara í gegnum bænastundina í dag?", pendingNotificationIntent);

                mManager.notify(0, notification);*/
                
             // define sound URI, the sound to be played when there's a notification
		
				if(intent.getBooleanExtra("notification", false)) {
		
        	  	Uri soundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        	   	 
        	   	// intent triggered, you can add other intent for other actions
        	   	Intent intent1 = new Intent(this, MainActivity.class);
        	   	PendingIntent pIntent = PendingIntent.getActivity(this, 0, intent1, 0);
        	   	 
        	   	// this is it, we'll build the notification!
        	   	// in the addAction method, if you don't want any icon, just set the first param to 0
        	   	Notification mNotification = new Notification.Builder(this)
        	   	.setContentTitle("Trúin og lífið")
        	   	.setContentText("Ertu búin að fara í gegnum bænastundina í dag?")
        	   	.setSmallIcon(R.drawable.ic_logo)
        	   	.setContentIntent(pIntent)
        	   	.setDefaults(Notification.DEFAULT_ALL)
        	   	.addAction(0, "Remind", pIntent)
        	   	.build();
        	   	
        	   	mNotification.flags |= Notification.FLAG_AUTO_CANCEL;
        	   	
        	   	NotificationManager notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        	   	 
        	   	// If you want to hide the notification after it was selected, do the code below
        	   	// myNotification.flags |= Notification.FLAG_AUTO_CANCEL;
        	   	int notificationNumber = 0;
        	   	notificationManager.notify(notificationNumber, mNotification);
        	   	
				}
        	   	
        	   	stopSelf();
	}

	public void startRepeatingTimer(View view){
		Context context = this.getApplicationContext();
		alarm.setAlarm(context);
	}


	@Override
	public IBinder onBind(Intent intent) {
		// TODO Auto-generated method stub
		return null;
	}
}
