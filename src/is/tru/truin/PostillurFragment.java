package is.tru.truin;

import java.util.ArrayList;
import java.util.HashMap;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import util.XMLParser;
import is.tru.truin.R;

import android.app.ListFragment;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SimpleAdapter;

public class PostillurFragment extends ListFragment{


    static final String URL = "http://tru.is/postilla/rss2";
    
    static final String KEY_ITEM = "item";
    static final String KEY_TITLE = "title";
    static final String KEY_DESC = "description";
    
    
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
                map.put(KEY_DESC, parser.getValue(e, KEY_DESC));

                menuItems.add(map);

            }

        return null; 
        } 
        @Override protected void onPostExecute(String args)
        { 
            String[] from = { KEY_TITLE, KEY_DESC};
            int[] to = { R.id.title, R.id.desc};
            SimpleAdapter adapter = new SimpleAdapter(getActivity().getBaseContext(), menuItems, R.layout.postillur_row, from, to);        
            setListAdapter(adapter);

        } 
    }


}


    