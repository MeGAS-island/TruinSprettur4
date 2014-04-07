package is.tru.truin;

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

public class PostillurFragment extends ListFragment {
		
	
	public PostillurFragment(){}
	
	String[] string_id = Constants.id;
	String[] string_name = Constants.name;
	String[] string_cost = Constants.cost;
	String[] string_desc = Constants.desc;
	
	public class MyListAdapter extends ArrayAdapter<String> {
		  
		Context myContext;
	
		public MyListAdapter(Context context, int textViewResourceId,
			String[] objects, String[] string_name, String[] string_cost, String[] string_desc) {
			super(context, textViewResourceId, objects);
			myContext = context;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			   
			LayoutInflater inflater = 
					(LayoutInflater)myContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			View row=inflater.inflate(R.layout.postillur_row, parent, false);
			TextView id=(TextView)row.findViewById(R.id.id);
			TextView name=(TextView)row.findViewById(R.id.name);
			TextView cost=(TextView)row.findViewById(R.id.cost);
			TextView desc=(TextView)row.findViewById(R.id.desc);
			
			id.setText(string_id[position]);
			name.setText(string_name[position]);
			cost.setText(string_cost[position]);
			desc.setText(string_desc[position]);
			
						
			return row;
		}
	}
				 
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		MyListAdapter myListAdapter = 
				new MyListAdapter(getActivity(), R.layout.postillur_row, string_id, string_name, string_cost, string_desc);
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

}
    