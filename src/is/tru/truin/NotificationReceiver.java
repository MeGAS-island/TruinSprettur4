package is.tru.truin;

import java.util.Calendar;

import android.app.AlarmManager;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.util.Log;

public class NotificationReceiver extends BroadcastReceiver {
	
/*	@Override
	protected void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
	    setContentView(R.layout.fragment_baenastund);
	  }*/

	@Override
	public void onReceive(Context context, Intent intent) {
		//Alarm triggered
		
		Log.d("reciever", "receiver");
		
	//	Intent myIntent = new Intent(context, NotificationService.class);
		
	//	if(intent.getBooleanExtra("notification", false)) {
			
    	  	Uri soundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
    	   	 
    	   	// intent triggered, you can add other intent for other actions
    	   	Intent intent1 = new Intent(context, ResultActivity.class);
    	   	PendingIntent pIntent = PendingIntent.getActivity(context, 0, intent1, 0);
    	   	 
    	   	// this is it, we'll build the notification!
    	   	// in the addAction method, if you don't want any icon, just set the first param to 0
    	   	Notification mNotification = new Notification.Builder(context)
    	   	.setContentTitle("Trúin og lífið")
    	   	.setContentText("Ertu búin að fara í gegnum bænastundina í dag?")
    	   	.setSmallIcon(R.drawable.ic_logo)
    	   	.setContentIntent(pIntent)
    	   	.setDefaults(Notification.DEFAULT_ALL)
    	   	.addAction(0, "Remind", pIntent)
    	   	.build();
    	   	
    	    NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
    	   	mNotification.flags |= Notification.FLAG_AUTO_CANCEL; 
    	   	// If you want to hide the notification after it was selected, do the code below
    	   	// myNotification.flags |= Notification.FLAG_AUTO_CANCEL;
    	   	int notificationNumber = 0;
    	   	notificationManager.notify(notificationNumber, mNotification);
    	   	
		//	}
    	   	
    	  // 	stopSelf();
		//myIntent.putExtra("notification", true);
	/*	Log.d("receiver", "newIntent");
		context.startService(myIntent);
		Log.d("receiver", "startservice");*/
	}
	
	public void setAlarm(Context context) {
		Intent myIntent = new Intent(context , NotificationReceiver.class);     
        AlarmManager alarmManager = (AlarmManager)context.getSystemService(Context.ALARM_SERVICE);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, 0, myIntent, 0);
        alarmManager.cancel(pendingIntent);
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, 13);
        calendar.set(Calendar.MINUTE, 40);
        calendar.set(Calendar.SECOND, 00);
        alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), 24*60*60*1000,  pendingIntent);  //set repeating every 24 hours
        Log.d("alarm", "set alarm");
	}
}
