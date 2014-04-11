package is.tru.truin;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class NotificationReceiver extends BroadcastReceiver {
	
/*	@Override
	protected void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
	    setContentView(R.layout.fragment_baenastund);
	  }*/

	@Override
	public void onReceive(Context context, Intent intent) {
		//Alarm triggered
		
		Intent myIntent = new Intent(context, NotificationService.class);
		context.startService(myIntent);
	}
}
