package is.tru.truin;

import java.util.Calendar;
import org.json.JSONObject;
import util.JSONParser;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class BaenastundOrdGudsFragment extends Fragment {
	
	public BaenastundOrdGudsFragment(){}
	
	String url;
	View rootView;
	TextView ritningdagsins;
	JSONObject jsonObject;
	String textiRitning;
	
	@Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
 
        rootView = inflater.inflate(R.layout.fragment_baenastund_ord_guds, container, false);
            
        Calendar c = Calendar.getInstance();
        int dagur = c.get(Calendar.DAY_OF_YEAR);
        int ar = c.get(Calendar.YEAR);
        StringBuilder urlid = new StringBuilder();
        String url1 = "http://www2.tru.is/app/json.php?s=dagur&id=";
        String url2 = "&y=";
        urlid.append(url1);
        urlid.append(dagur);
        urlid.append(url2);
        urlid.append(ar);
        url = urlid.toString();
        
        new getJSONTask().execute();
        
        return rootView;
    }
    private class getJSONTask extends  AsyncTask<Void, Void, Void>{
    	ProgressDialog pDialog;
    	
    	protected Void doInBackground(Void...params) {
	    	try {
	    		JSONParser jParser = new JSONParser();
				jsonObject = jParser.getJSONFromUrl(url);
				
				textiRitning = jsonObject.getString("lestur_txt");
				Constants.baendagsins = jsonObject.getString("baen_txt");

			} catch (Exception e) {
				e.printStackTrace();
			}
	    	return null;
    	}
		@Override
		protected void onPreExecute(){
			super.onPreExecute();
			
			ritningdagsins = (TextView) rootView.findViewById(R.id.ritningdagsins);
			CharSequence bidid = "Vinsamlega bíðið";
			CharSequence sendi = "Sæki ritningu dagsins"; 
			pDialog = ProgressDialog.show(getActivity(), bidid, sendi, true, false);
		}
		
		@Override
		protected void onPostExecute(Void aVoid){
			super.onPostExecute(aVoid);
			ritningdagsins.setText(textiRitning);
			pDialog.dismiss();
		}

    }
  
}
