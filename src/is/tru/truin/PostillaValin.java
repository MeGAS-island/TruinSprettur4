package is.tru.truin;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

public class PostillaValin extends Activity {
	private static final String KEY_TITLE = "title";
	private static final String KEY_CONTENT = "content";

	
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.postilla_valin);
        
        Intent myIntent = getIntent();

        String varTitle = myIntent.getStringExtra(KEY_TITLE);
        String varContent = myIntent.getStringExtra(KEY_CONTENT);

        TextView lblTitle = (TextView) findViewById(R.id.TitleValinn);
        TextView lblContent = (TextView) findViewById(R.id.ContentValinn);

        
        lblTitle.setText(varTitle);
        lblContent.setText(varContent);

    }
}