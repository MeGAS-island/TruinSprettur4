package is.tru.truin;

import java.util.Calendar;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
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
		
		Intent myIntent = new Intent(context, NotificationService.class);
		Log.d("receiver", "newIntent");
		context.startService(myIntent);
		Log.d("receiver", "startservice");
	}
	
	public void setAlarm(Context context) {
		Intent myIntent = new Intent(context , NotificationReceiver.class);     
        AlarmManager alarmManager = (AlarmManager)context.getSystemService(Context.ALARM_SERVICE);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, 0, myIntent, 0);
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, 14);
        calendar.set(Calendar.MINUTE, 00);
        calendar.set(Calendar.SECOND, 00);
        alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), 24*60*60*1000 , pendingIntent);  //set repeating every 24 hours
        Log.d("alarm", "set alarm");
	}
}
