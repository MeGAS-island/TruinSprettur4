package is.tru.truin;

import org.json.JSONObject;

import util.JSONParser;

import android.app.Fragment;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class SalmabokFragment extends Fragment {
	
	TextView salmur;
	JSONObject jsonObject;
	String salmurText;
	View rootView;

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
 
        rootView = inflater.inflate(R.layout.fragment_salmabok, container, false); 

        new getJSONTask().execute();
		
		return rootView;
    }
    
    private class getJSONTask extends  AsyncTask<Void, Void, Void>{
    	
    	ProgressDialog pDialog;
    	
    	protected Void doInBackground(Void...params) {
	    	try {
	    		JSONParser jParser = new JSONParser();
				jsonObject = jParser.getJSONFromUrl("http://www2.tru.is/app/json.php?s=salmur&id=50");
				
				salmurText = jsonObject.getString("texti");

				
			} catch (Exception e) {
				e.printStackTrace();
			}
	    	return null;
    	}
		@Override
		protected void onPreExecute(){
			super.onPreExecute();
			
			salmur = (TextView)rootView.findViewById(R.id.salmur);
			CharSequence bidid = "Vinsamlega bíðið";
			CharSequence sendi = "Sæki sálm"; 
			pDialog = ProgressDialog.show(getActivity(), bidid, sendi, true, false);
		}
		
		@Override
		protected void onPostExecute(Void aVoid){
			super.onPostExecute(aVoid);
			salmur.setText(salmurText);
			pDialog.dismiss();
		}

    }
}
