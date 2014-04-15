package is.tru.truin;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;

public class Settings extends Activity {
	RadioButton noficationButton;
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.action_settings);
		
		final RadioGroup radioGroup = (RadioGroup) findViewById(R.id.NotificationButtons);
		Button vistaButton = (Button) findViewById(R.id.vista);
		vistaButton.setOnClickListener(new OnClickListener(){
			public void onClick(View v) {
				int selectedId = radioGroup.getCheckedRadioButtonId();
				if(selectedId==2131361794){
					Constants.isSound=!Constants.isSound;
				}
				else if(selectedId==2131361795){
					Constants.isVibrate = !Constants.isVibrate;
				}
				else if(selectedId==2131361796){
					Constants.isNotification = !Constants.isNotification;
				}
			}	
		});
	}
}