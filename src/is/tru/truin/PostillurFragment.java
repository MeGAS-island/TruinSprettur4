package is.tru.truin;

import java.util.ArrayList;
import java.util.HashMap;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import util.XMLParser;
import android.app.ListFragment;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

public class PostillurFragment extends ListFragment{


    static final String URL = "http://tru.is/pistlar/rss2";
    
    static final String KEY_ITEM = "item";
    static final String KEY_TITLE = "title";
    static final String KEY_CONTENT = "content:encoded";
    
    
    ArrayList<HashMap<String, String>> menuItems;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
                View view =inflater.inflate(R.layout.postillur_listfragment, null);
        return view;

    }
    @Override
    public void onResume() {
        super.onResume();
        new loadListView().execute();

    }
    
	@Override
	public void onListItemClick(ListView l, View v, int position, long id) {
		
		String varTitle = ((TextView) v.findViewById(R.id.title_list)).getText().toString();
		String varContent = ((TextView) v.findViewById(R.id.content_list)).getText().toString();
					
		Intent myIntent = new Intent();
		myIntent.setClassName("is.tru.truin", "is.tru.truin.PostillaValin");
		myIntent.putExtra(KEY_TITLE, varTitle);
		myIntent.putExtra(KEY_CONTENT, varContent);

		startActivity(myIntent);
		
	}
    



	public class loadListView extends AsyncTask<Integer, String, String> 
    {
        @Override protected void onPreExecute() 
        { 

        super.onPreExecute();
        } 
        @Override protected String doInBackground(Integer... args) 
        {
        menuItems = new ArrayList<HashMap<String, String>>();
            final XMLParser parser = new XMLParser();
            String xml = parser.getXmlFromUrl(URL);
            Document doc = parser.getDomElement(xml);

            NodeList nl = doc.getElementsByTagName(KEY_ITEM);
            for (int i = 0; i < nl.getLength(); i++) {
                HashMap<String, String> map = new HashMap<String, String>();
                Element e = (Element) nl.item(i);

                map.put(KEY_TITLE, parser.getValue(e, KEY_TITLE));
                map.put(KEY_CONTENT, parser.getValue(e, KEY_CONTENT));
                
                //Log.d("title: ", parser.getValue(e, KEY_TITLE));
                //Log.d("content: ", parser.getValue(e, KEY_CONTENT));

                menuItems.add(map);

            }

        return null; 
        } 
        @Override protected void onPostExecute(String args)
        { 
            String[] from = { KEY_TITLE, KEY_CONTENT};
            int[] to = { R.id.title_list, R.id.content_list};
            SimpleAdapter adapter = new SimpleAdapter(getActivity().getBaseContext(), menuItems, R.layout.postillur_row, from, to);        
            setListAdapter(adapter);

        } 
    }


}