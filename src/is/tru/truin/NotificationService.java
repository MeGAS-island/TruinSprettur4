package is.tru.truin;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;

public class NotificationService extends Activity{
	
	// define sound URI, the sound to be played when there's a notification
  	Uri soundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
   	 
   	// intent triggered, you can add other intent for other actions
   	Intent intent = new Intent(this, NotificationReceiver.class);
   	PendingIntent pIntent = PendingIntent.getActivity(this, 0, intent, 0);
   	 
   	// this is it, we'll build the notification!
   	// in the addAction method, if you don't want any icon, just set the first param to 0
   	@SuppressLint("NewApi")
	Notification mNotification = new Notification.Builder(this)
   	.setContentTitle("New Post!")
   	.setContentText("Here's an awesome update for you!")
   	//.setSmallIcon(R.drawable.ic_bible_black)
   	.setContentIntent(pIntent)
   	//.setSound(soundUri)
   	//.addAction(R.drawable.ic_bible_black, "View", pIntent)
   //	.addAction(0, "Remind", pIntent)
   	.build();
   	 
   	NotificationManager notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
   	 
   	// If you want to hide the notification after it was selected, do the code below
   	// myNotification.flags |= Notification.FLAG_AUTO_CANCEL;
   	int notificationNumber = 0;
   	//notificationManager.notify(mNotification);

}
