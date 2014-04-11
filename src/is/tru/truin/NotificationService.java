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
	
	 
	public void onStart(Intent intent, int startID){
		NotificationCompat.Builder builder = new NotificationCompat.Builder(this);
		builder.setContentTitle("B�n dagsins");
		builder.setContentText("test");
		builder.setTicker("n� skilabo�");
		
		Log.d("not", "create notfication");
		
		Intent resultIntent = new Intent(this, MainActivity.class);
		TaskStackBuilder stackBuilder = TaskStackBuilder.create(this);
		stackBuilder.addNextIntent(resultIntent);
		
		PendingIntent pendingIntent = stackBuilder.getPendingIntent(0, PendingIntent.FLAG_UPDATE_CURRENT);
		builder.setContentIntent(pendingIntent);
		
		NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
		notificationManager.notify(0, builder.build());
		Log.d("not", "send notification");
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
