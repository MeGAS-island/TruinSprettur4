package is.tru.truin;

import org.json.JSONObject;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import util.JSONParser;
import util.XMLParser;
import is.tru.truin.R;
import is.tru.truin.MainActivity.LoadPostillur;

import android.annotation.SuppressLint;
import android.app.ListFragment;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class PostillurFragment extends ListFragment {
	
	static final String URL = "http://api.androidhive.info/pizza/?format=xml";

	static final String KEY_ITEM = "item";
	static final String KEY_ID = "id";
	static final String KEY_NAME = "name";
	static final String KEY_COST = "cost";
	static final String KEY_DESC = "description";
	
	TextView id;
	TextView name;
	TextView cost;
	TextView desc;
	
	String str_id;
	String str_name;
	String str_cost;
	String str_desc;
	
	View rootView;
		
	public PostillurFragment(){}
	
	
	public class MyListAdapter extends ArrayAdapter<String> {
		  
		Context myContext;
	
		public MyListAdapter(Context context, int textViewResourceId,
			String str_id, String str_name, String str_cost, String str_desc) {
			super(context, textViewResourceId);
			myContext = context;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			
			new LoadPostillur().execute();
			   
			LayoutInflater inflater = 
					(LayoutInflater)myContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			View row=inflater.inflate(R.layout.postillur_row, parent, false);
							
			return row;
		}
	}
				 
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		MyListAdapter myListAdapter = 
				new MyListAdapter(getActivity(), R.layout.postillur_row, str_id, str_name, str_cost, str_desc);
		setListAdapter(myListAdapter);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		return inflater.inflate(R.layout.postillur_listfragment, container, false);
	}
	
	@Override
	public void onListItemClick(ListView l, View v, int position, long id) {
		// TODO Auto-generated method stub
		
		//Getum gert eitthvad annad herna ef vid viljum
		Toast.makeText(
				getActivity(), 
				getListView().getItemAtPosition(position).toString(), 
				Toast.LENGTH_LONG).show();
	}
	
    private class LoadPostillur extends  AsyncTask<Void, Void, Void>{
    	
    	ProgressDialog pDialog;
    	
    	protected Void doInBackground(Void...params) {
	    	try {
	    		
				XMLParser parser = new XMLParser();
				String xml = parser.getXmlFromUrl(URL);
				Document doc = parser.getDomElement(xml);

				NodeList nl = doc.getElementsByTagName(KEY_ITEM);
	
				for (int i = 0; i < nl.getLength(); i++) {

					Element e = (Element) nl.item(i);
					
					str_id = parser.getValue(e, KEY_ID);
					str_name = parser.getValue(e, KEY_NAME);
					str_cost = parser.getValue(e, KEY_COST);
					str_desc = parser.getValue(e, KEY_DESC);
										
				}
				return null;
				
			} catch (Exception e) {
				e.printStackTrace();
			}
	    	return null;
    	}
		@Override
		protected void onPreExecute(){
			super.onPreExecute();
			
			id = (TextView)rootView.findViewById(R.id.id);
			name = (TextView)rootView.findViewById(R.id.name);
			cost = (TextView)rootView.findViewById(R.id.cost);
			desc = (TextView)rootView.findViewById(R.id.desc);
			CharSequence bidid = "Vinsamlega bíðið";
			CharSequence sendi = "Sæki postillur"; 
			pDialog = ProgressDialog.show(getActivity(), bidid, sendi, true, false);
		}
		
		@Override
		protected void onPostExecute(Void aVoid){
			super.onPostExecute(aVoid);
			id.setText(str_id);
			name.setText(str_name);
			cost.setText(str_cost);
			desc.setText(str_desc);
			pDialog.dismiss();
		}

    }
	
	

}
    