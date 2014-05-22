package is.tru.truin;

import org.json.JSONObject;

import util.JSONParser;

import android.app.Fragment;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class DagurValinnFragment extends Fragment {
	
	TextView Tvtitill;
	TextView Tvlestur;
	TextView TvlesturTxt;
	TextView Tvlestur2;
	TextView Tvlestur2Txt;
	TextView TvSalmurNr;
	TextView TvSalmurText;
	TextView TvBaenText;
	TextView TvminnisversTxt;
	JSONObject jsonObject;
	String titill;
	String lestur;
	String lesturText;
	String lestur2;
	String lestur2Text;
	String SalmurNr;
	String SalmurText;
	String BaenText;
	String MinnisversText;
	View rootView;
	
	String AR = Constants.ar_selected;
	String MAN = Constants.manudur_selected;
	String DAG = Constants.dagur_selected;
		

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
 
        rootView = inflater.inflate(R.layout.fragment_dagur_valinn, container, false);
   
        new getJSONTask().execute();
    	
		return rootView;
    }
    
    private class getJSONTask extends  AsyncTask<Void, Void, Void>{
    	
    	ProgressDialog pDialog;
    	
    	private  String CalculateDay (String year, String month, String day) {
            
    		int dag = Integer.parseInt(day);
    		int dagur = 0;

    		if(month == "janúar") {

    			dagur = dag;
    		} else if(month == "febrúar") {
    			
    			dagur = 31 + dag;
    		} else if(month == "mars" && (year != "2012" && year != "2008" && year != "2004")) {
    			
    			dagur = 59 + dag;
    		} else if(month == "mars" && (year == "2012" || year == "2008" || year == "2004")) {
    			
    			dagur = 60 + dag;
    		} else if(month == "apríl" && (year != "2012" && year != "2008" && year != "2004")) {
    			
    			dagur = 90 + dag;
    		} else if(month == "apríl" && (year == "2012" || year == "2008" || year == "2004")) {
    			
    			dagur = 91 + dag;
    		} else if(month == "maí" && (year != "2012" && year != "2008" && year != "2004")) {
    			
    			dagur = 120 + dag;
    		} else if(month == "maí" && (year == "2012" || year == "2008" || year == "2004")) {
    			
    			dagur = 121 + dag;
    		} else if(month == "júní" && (year != "2012" && year != "2008" && year != "2004")) {
    			
    			dagur = 151 + dag;
    		} else if(month == "júní" && (year == "2012" || year == "2008" || year == "2004")) {
    			
    			dagur = 152 + dag;
    		} else if(month == "júlí" && (year != "2012" && year != "2008" && year != "2004")) {
    			
    			dagur = 181 + dag;
    		} else if(month == "júlí" && (year == "2012" || year == "2008" || year == "2004")) {
    			
    			dagur = 182 + dag;
    		} else if(month == "ágúst" && (year != "2012" && year != "2008" && year != "2004")) {
    			
    			dagur = 212 + dag;
    		} else if(month == "ágúst" && (year == "2012" || year == "2008" || year == "2004")) {
    			
    			dagur = 213 + dag;
    		} else if(month == "september" && (year != "2012" && year != "2008" && year != "2004")) {
    			
    			dagur = 243 + dag;
    		} else if(month == "september" && (year == "2012" || year == "2008" || year == "2004")) {
    			
    			dagur = 244 + dag;
    		} else if(month == "október" && (year != "2012" && year != "2008" && year != "2004")) {
    			
    			dagur = 273 + dag;
    		} else if(month == "október" && (year == "2012" || year == "2008" || year == "2004")) {
    			
    			dagur = 274 + dag;
    		} else if(month == "nóvember" && (year != "2012" && year != "2008" && year != "2004")) {
    			
    			dagur = 304 + dag;
    		} else if(month == "nóvember" && (year == "2012" || year == "2008" || year == "2004")) {
    			
    			dagur = 305 + dag;
    		} else if(month == "desember" && (year != "2012" && year != "2008" && year != "2004")) {
    				
    			dagur = 334 + dag;
    		} else if(month == "desember" && (year == "2012" || year == "2008" || year == "2004")) {
    			
    			dagur = 335 + dag;
    		}
    			
    		String DA = Integer.toString(dagur);
    		
    		return DA;
    	}
    	
    	
    	protected Void doInBackground(Void...params) {
	    	try {
	    		JSONParser jParser = new JSONParser();
				jsonObject = jParser.getJSONFromUrl(//"http://www2.tru.is/app/json.php?s=dagur&id=11&y=2012");
						"http://www2.tru.is/app/json.php?s=dagur&id="+CalculateDay(AR,MAN,DAG)+"&y="+AR);
				
				
				titill = jsonObject.getString("titill");
				lestur = jsonObject.getString("lestur");
				lesturText = jsonObject.getString("lestur_txt");
				lestur2 = jsonObject.getString("lestur2");
				lestur2Text = jsonObject.getString("lestur2_txt");
				SalmurNr = jsonObject.getString("salmur_numer");
				SalmurText = jsonObject.getString("salmur_txt");
				BaenText = jsonObject.getString("baen_txt");
				MinnisversText = jsonObject.getString("minnisvers_txt");
				
			} catch (Exception e) {
				e.printStackTrace();
			}
	    	return null;
    	}
		@Override
		protected void onPreExecute(){
			super.onPreExecute();
						
			Tvtitill = (TextView)rootView.findViewById(R.id.TvTitill);
			Tvlestur = (TextView)rootView.findViewById(R.id.TvLestur);
			TvlesturTxt = (TextView)rootView.findViewById(R.id.TvLesturTxt);
			Tvlestur2 = (TextView)rootView.findViewById(R.id.TvLestur2);
			Tvlestur2Txt = (TextView)rootView.findViewById(R.id.TvLestur2Txt);
			TvSalmurNr = (TextView)rootView.findViewById(R.id.TvSalmurNr);
			TvSalmurText = (TextView)rootView.findViewById(R.id.TvSalmurText);
			TvBaenText = (TextView)rootView.findViewById(R.id.TvBaenText);
			TvminnisversTxt = (TextView)rootView.findViewById(R.id.TvMinnisversTxt);
			
			
			
			CharSequence bidid = "Vinsamlega bÃ­Ã°iÃ°";
			CharSequence sendi = "SÃ¦ki Lestur"; 
			pDialog = ProgressDialog.show(getActivity(), bidid, sendi, true, false);
		}
		
		@Override
		protected void onPostExecute(Void aVoid){
			super.onPostExecute(aVoid);
			Tvtitill.setText(Html.fromHtml(titill));
			Tvlestur.setText(Html.fromHtml(lestur));
			TvlesturTxt.setText(Html.fromHtml(lesturText));
			Tvlestur2.setText(Html.fromHtml(lestur2));
			Tvlestur2Txt.setText(Html.fromHtml(lestur2Text));
			TvSalmurNr.setText(Html.fromHtml(SalmurNr));
			TvSalmurText.setText(Html.fromHtml(SalmurText));
			TvBaenText.setText(Html.fromHtml(BaenText));
			TvminnisversTxt.setText(Html.fromHtml(MinnisversText));
			pDialog.dismiss();
		}

    }

}
