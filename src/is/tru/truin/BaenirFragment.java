package is.tru.truin;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

public class BaenirFragment extends Fragment{
	
	public BaenirFragment(){}
	
	Button fyrirbaenaefni;
	
	@Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
 
        View rootView = inflater.inflate(R.layout.fragment_baenir, container, false);
        
        Button RandomBaen = (Button) rootView.findViewById(R.id.btnRandomBaen);
        RandomBaen.setOnClickListener(new View.OnClickListener() {
        
            @Override
            public void onClick(View v) {
            	Fragment newFragment = new RandomBaenFragment();
                android.app.FragmentTransaction transaction = getFragmentManager().beginTransaction();
                transaction.replace(R.id.frame_container, newFragment, "randombaen");
                //transaction.addToBackStack(null);
                transaction.commit();
            }
        });
        
        
         
        Button fyrirbaenaefni = (Button) rootView.findViewById(R.id.fyrirbaenaefni);
        fyrirbaenaefni.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Fragment newFragment = new FyrirbaenaefniFragment();
				android.app.FragmentTransaction transaction = getFragmentManager().beginTransaction();
				transaction.replace(R.id.frame_container, newFragment, "fyrirbaen");
				//transaction.addToBackStack(null);
				transaction.commit();
				
			}
		});
        
         
        return rootView;
    }
	
	public void onClick(View v){

		
	}
	
}
