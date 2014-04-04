package is.tru.truin;

import java.util.Random;

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

public class RandomBaenFragment extends Fragment {
	
	TextView baen;
	TextView titill;
	TextView stikkord;
	TextView kirkjuarid;
	JSONObject jsonObject;
	String baenText;
	String baenTitill;
	String baenStikkord;
	String baenKirkjuarid;
	View rootView;
	

	int Low = 1000;
	int High = 1710;
	int irandom = new Random().nextInt(High-Low) + Low;
	String srandom = Integer.toString(irandom);
	

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
 
        rootView = inflater.inflate(R.layout.fragment_random_baen, container, false); 

        new getJSONTask().execute();
		
		return rootView;
    }
    
    private class getJSONTask extends  AsyncTask<Void, Void, Void>{
    	
    	ProgressDialog pDialog;
    	
    	protected Void doInBackground(Void...params) {
	    	try {
	    		JSONParser jParser = new JSONParser();
				jsonObject = jParser.getJSONFromUrl("http://www2.tru.is/app/json.php?s=baen&id="+ srandom);
				
				baenText = jsonObject.getString("texti");
				baenTitill = jsonObject.getString("titill");
				baenStikkord = jsonObject.getString("stikkord");
				baenKirkjuarid = jsonObject.getString("kirkjuarid");
				

				
			} catch (Exception e) {
				e.printStackTrace();
			}
	    	return null;
    	}
		@Override
		protected void onPreExecute(){
			super.onPreExecute();
			
			baen = (TextView)rootView.findViewById(R.id.randomBaen);
			titill = (TextView)rootView.findViewById(R.id.randomTitill);
			stikkord = (TextView)rootView.findViewById(R.id.randomStikkord);
			kirkjuarid = (TextView)rootView.findViewById(R.id.randomKirkjuarid);
			CharSequence bidid = "Vinsamlega bíðið";
			CharSequence sendi = "Sæki Bæn"; 
			pDialog = ProgressDialog.show(getActivity(), bidid, sendi, true, false);
		}
		
		@Override
		protected void onPostExecute(Void aVoid){
			super.onPostExecute(aVoid);
			baen.setText(baenText);
			titill.setText(baenTitill);
			stikkord.setText(baenStikkord);
			kirkjuarid.setText(baenKirkjuarid);
			pDialog.dismiss();
		}

    }
}
