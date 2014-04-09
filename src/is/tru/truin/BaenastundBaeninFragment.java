package is.tru.truin;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

public class BaenastundBaeninFragment extends Fragment  {
	
	public BaenastundBaeninFragment(){}
	
	TextView baenin;
	
	@Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
 
        View rootView = inflater.inflate(R.layout.fragment_baenastund_baenin, container, false);
        
        baenin = (TextView) rootView.findViewById(R.id.baendagsins);
        baenin.setText(Constants.baendagsins);
        
        return rootView;
	}
	
}
