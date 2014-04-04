package is.tru.truin;

import org.json.JSONObject;

import util.JSONParser;

import android.app.Fragment;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
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
	String DA = "";

	
	int dagur = 0;
		

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
 
        rootView = inflater.inflate(R.layout.fragment_dagur_valinn, container, false); 

        new getJSONTask().execute();
        
        Log.d("typa DAG: ", DAG.getClass().getName());
        
        if(MAN == "janúar") {
        	int dag = Integer.parseInt(DAG);
        	dagur = dag;
        } else if(MAN == "febrúar" && (AR != "2012" || AR != "2008" || AR != "2004")) {
        	int dag = Integer.parseInt(DAG);
        	dagur = 31 + dag;
        } else if(MAN == "febrúar" && (AR == "2012" || AR == "2008" || AR == "2004")) {
        	int dag = Integer.parseInt(DAG);
        	dagur = 31 + dag;
        } else if(MAN == "mars" && (AR != "2012" || AR != "2008" || AR != "2004")) {
        	int dag = Integer.parseInt(DAG);
        	dagur = 59 + dag;
        } else if(MAN == "mars" && (AR == "2012" || AR == "2008" || AR == "2004")) {
        	int dag = Integer.parseInt(DAG);
        	dagur = 60 + dag;
        } else if(MAN == "apríl" && (AR != "2012" || AR != "2008" || AR != "2004")) {
        	int dag = Integer.parseInt(DAG);
        	dagur = 90 + dag;
        } else if(MAN == "apríl" && (AR == "2012" || AR == "2008" || AR == "2004")) {
        	int dag = Integer.parseInt(DAG);
        	dagur = 91 + dag;
        } else if(MAN == "maí" && (AR != "2012" || AR != "2008" || AR != "2004")) {
        	int dag = Integer.parseInt(DAG);
        	dagur = 120 + dag;
        } else if(MAN == "maí" && (AR == "2012" || AR == "2008" || AR == "2004")) {
        	int dag = Integer.parseInt(DAG);
        	dagur = 121 + dag;
        } else if(MAN == "júní" && (AR != "2012" || AR != "2008" || AR != "2004")) {
        	int dag = Integer.parseInt(DAG);
        	dagur = 151 + dag;
        } else if(MAN == "júní" && (AR == "2012" || AR == "2008" || AR == "2004")) {
        	int dag = Integer.parseInt(DAG);
        	dagur = 152 + dag;
        } else if(MAN == "júlí" && (AR != "2012" || AR != "2008" || AR != "2004")) {
        	int dag = Integer.parseInt(DAG);
        	dagur = 181 + dag;
        } else if(MAN == "júlí" && (AR == "2012" || AR == "2008" || AR == "2004")) {
        	int dag = Integer.parseInt(DAG);
        	dagur = 182 + dag;
        } else if(MAN == "ágúst" && (AR != "2012" || AR != "2008" || AR != "2004")) {
        	int dag = Integer.parseInt(DAG);
        	dagur = 212 + dag;
        } else if(MAN == "ágúst" && (AR == "2012" || AR == "2008" || AR == "2004")) {
        	int dag = Integer.parseInt(DAG);
        	dagur = 213 + dag;
        } else if(MAN == "september" && (AR != "2012" || AR != "2008" || AR != "2004")) {
        	int dag = Integer.parseInt(DAG);
        	dagur = 243 + dag;
        } else if(MAN == "september" && (AR == "2012" || AR == "2008" || AR == "2004")) {
        	int dag = Integer.parseInt(DAG);
        	dagur = 244 + dag;
        } else if(MAN == "október" && (AR != "2012" || AR != "2008" || AR != "2004")) {
        	int dag = Integer.parseInt(DAG);
        	dagur = 273 + dag;
        } else if(MAN == "október" && (AR == "2012" || AR == "2008" || AR == "2004")) {
        	int dag = Integer.parseInt(DAG);
        	dagur = 274 + dag;
        } else if(MAN == "nóvember" && (AR != "2012" || AR != "2008" || AR != "2004")) {
        	int dag = Integer.parseInt(DAG);
        	dagur = 304 + dag;
        } else if(MAN == "nóvember" && (AR == "2012" || AR == "2008" || AR == "2004")) {
        	int dag = Integer.parseInt(DAG);
        	dagur = 305 + dag;
        } else if(MAN == "desember" && (AR != "2012" || AR != "2008" || AR != "2004")) {
        	int dag = Integer.parseInt(DAG);
        	dagur = 334 + dag;
        } else if(MAN == "desember" && (AR == "2012" || AR == "2008" || AR == "2004")) {
        	int dag = Integer.parseInt(DAG);
        	dagur = 335 + dag;
        }
        
        DA = Integer.toString(dagur);
    	Log.d("ValueDA: ", DA);
		
		return rootView;
    }
    
    private class getJSONTask extends  AsyncTask<Void, Void, Void>{
    	
    	ProgressDialog pDialog;
    	
    	
    	protected Void doInBackground(Void...params) {
	    	try {
	    		JSONParser jParser = new JSONParser();
				jsonObject = jParser.getJSONFromUrl("http://www2.tru.is/app/json.php?s=dagur&id="+DA+"&y="+AR);
				
				
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
			
			
			
			CharSequence bidid = "Vinsamlega bíðið";
			CharSequence sendi = "Sæki Lestur"; 
			pDialog = ProgressDialog.show(getActivity(), bidid, sendi, true, false);
		}
		
		@Override
		protected void onPostExecute(Void aVoid){
			super.onPostExecute(aVoid);
			Tvtitill.setText(titill);
			Tvlestur.setText(lestur);
			TvlesturTxt.setText(lesturText);
			Tvlestur2.setText(lestur2);
			Tvlestur2Txt.setText(lestur2Text);
			TvSalmurNr.setText(SalmurNr);
			TvSalmurText.setText(SalmurText);
			TvBaenText.setText(BaenText);
			TvminnisversTxt.setText(MinnisversText);
			pDialog.dismiss();
		}

    }
}
