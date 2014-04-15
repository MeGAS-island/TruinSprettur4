package is.tru.truin;

import java.util.Calendar;

import android.app.AlarmManager;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class NotificationReceiver extends BroadcastReceiver {
	
	@Override
	public void onReceive(Context context, Intent intent) {
   	   	Intent intent1 = new Intent(context, MainActivity.class);
   	   	PendingIntent pIntent = PendingIntent.getActivity(context, 0, intent1, 0);
    	
   	   	Notification mNotification = null;
   	   	if(Constants.isVibrate) {
	   	   	 mNotification = new Notification.Builder(context)
	   	   	.setContentTitle("Trúin og lífið")
	   	   	.setContentText("Ertu búin að fara í gegnum bænastundina í dag?")
	   	   	.setSmallIcon(R.drawable.ic_logo)
	   	   	.setContentIntent(pIntent)
	   	   	.setDefaults(Notification.DEFAULT_VIBRATE)
	   	   	.addAction(0, "Remind", pIntent)
	   	   	.build();
   	   	}
   	   	else if(Constants.isSound){
	   	   	mNotification = new Notification.Builder(context)
	   	   	.setContentTitle("Trúin og lífið")
	   	   	.setContentText("Ertu búin að fara í gegnum bænastundina í dag?")
	   	   	.setSmallIcon(R.drawable.ic_logo)
	   	   	.setContentIntent(pIntent)
	   	   	.setDefaults(Notification.DEFAULT_SOUND)
	   	   	.addAction(0, "Remind", pIntent)
	   	   	.build();
   	   	}
   	   	else if(Constants.isNotification){
	   	   	mNotification = new Notification.Builder(context)
	   	   	.setContentTitle("Trúin og lífið")
	   	   	.setContentText("Ertu búin að fara í gegnum bænastundina í dag?")
	   	   	.setSmallIcon(R.drawable.ic_logo)
	   	   	.setContentIntent(pIntent)
	   	   	.setDefaults(Notification.DEFAULT_ALL)
	   	   	.addAction(0, "Remind", pIntent)
	   	   	.build();
   	   	}
    	   	
   	    NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
   	   	mNotification.flags |= Notification.FLAG_AUTO_CANCEL; 
   	   	int notificationNumber = 0;
   	   	notificationManager.notify(notificationNumber, mNotification);
	}
	
	public void setAlarm(Context context) {
		if(!Constants.isNotification){
			Intent myIntent = new Intent(context , NotificationReceiver.class);     
	        AlarmManager alarmManager = (AlarmManager)context.getSystemService(Context.ALARM_SERVICE);
	        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, 0, myIntent, 0);
	        alarmManager.cancel(pendingIntent);
	        Calendar calendar = Calendar.getInstance();
	        calendar.set(Calendar.HOUR_OF_DAY, 14);
	        calendar.set(Calendar.MINUTE, 30);
	        calendar.set(Calendar.SECOND, 00);
	        long currTime = System.currentTimeMillis();
	        if(currTime < calendar.getTimeInMillis()) 
	        	alarmManager.set(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), pendingIntent);  //set repeating every 24 hours
		}
	}
}
