package is.tru.truin;

import java.util.ArrayList;
import java.util.HashMap;

import org.json.JSONObject;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import util.JSONParser;
import util.XMLParser;
import is.tru.truin.R;

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
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

public class PostillurFragment extends ListFragment{


    static final String URL = "http://api.androidhive.info/pizza/?format=xml";
    // XML node keys
    static final String KEY_ITEM = "item"; // parent node
    //static final String KEY_ID = "id";
    static final String KEY_ID = "id";
    static final String KEY_NAME = "name";
    static final String KEY_COST = "cost";
    static final String KEY_DESC = "desc";
    
    
    ArrayList<HashMap<String, String>> menuItems;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
                View view =inflater.inflate(R.layout.postillur_listfragment, null);
        return view;

    }
    @Override
    public void onResume() {
        super.onResume(); // don't forget to call super.onResume()
        new loadListView().execute();

    }

    public class loadListView extends AsyncTask<Integer, String, String> 
    {
        @Override protected void onPreExecute() 
        { 

            Toast.makeText(getActivity(), "Ucitavanje...", Toast.LENGTH_LONG).show();

        super.onPreExecute();
        } 
        @Override protected String doInBackground(Integer... args) 
        { // updating UI from Background Thread 
        menuItems = new ArrayList<HashMap<String, String>>();
            final XMLParser parser = new XMLParser();
            String xml = parser.getXmlFromUrl(URL); // getting XML
            Document doc = parser.getDomElement(xml); // getting DOM element

            NodeList nl = doc.getElementsByTagName(KEY_ITEM);
            // looping through all item nodes <item>
            for (int i = 0; i < nl.getLength(); i++) {
                // creating new HashMap
                HashMap<String, String> map = new HashMap<String, String>();
                Element e = (Element) nl.item(i);
                // adding each child node to HashMap key => value

                map.put(KEY_NAME, parser.getValue(e, KEY_NAME));
                map.put(KEY_COST, "Datum: " + parser.getValue(e, KEY_COST));
                map.put(KEY_DESC, parser.getValue(e, KEY_DESC));

                // adding HashList to ArrayList
                menuItems.add(map);

            }

        return null; 
        } 
        @Override protected void onPostExecute(String args)
        { 
            Toast.makeText(getActivity(), "Ucitano", Toast.LENGTH_LONG).show();
            String[] from = { KEY_NAME, KEY_DESC, KEY_COST};

            /** Ids of views in listview_layout */
            int[] to = { R.id.name, R.id.cost, R.id.desc};

            // Instantiating an adapter to store each items
            // R.layout.listview_layout defines the layout of each item
            SimpleAdapter adapter = new SimpleAdapter(getActivity().getBaseContext(), menuItems, R.layout.postillur_row, from, to);        

            // Setting the adapter to the listView
            setListAdapter(adapter);



                } 
                }


}


    