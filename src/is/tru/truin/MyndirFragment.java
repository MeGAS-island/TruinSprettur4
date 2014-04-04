package is.tru.truin;

import com.androidquery.AQuery;

import is.tru.truin.R;

import android.app.ListFragment;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class MyndirFragment extends ListFragment {
		
	private AQuery aq;
	
	public MyndirFragment(){}
	
	String[] imageUrl = Constants.IMAGES;
	String[] user = Constants.USERS;
	
	public class MyListAdapter extends ArrayAdapter<String> {
		  
		Context myContext;
	
		public MyListAdapter(Context context, int textViewResourceId,
			String[] objects) {
			super(context, textViewResourceId, objects);
			myContext = context;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			
			aq = new AQuery(getActivity(), convertView);
			   
			   
			LayoutInflater inflater = 
					(LayoutInflater)myContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			View row=inflater.inflate(R.layout.myndir_row, parent, false);
			TextView label=(TextView)row.findViewById(R.id.user);
			label.setText(user[position]);
			
			String url = imageUrl[position];
			
			aq = aq.recycle(row);
			   
			aq.id(R.id.image).image(url, true, true, 0, 0, null, 0, AQuery.RATIO_PRESERVE);
			
			return row;
		}
	}
				 
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		MyListAdapter myListAdapter = 
				new MyListAdapter(getActivity(), R.layout.myndir_row, user);
		setListAdapter(myListAdapter);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		return inflater.inflate(R.layout.myndir_listfragment, container, false);
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

}
    