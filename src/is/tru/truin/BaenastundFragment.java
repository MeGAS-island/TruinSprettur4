package is.tru.truin;

import java.util.Random;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;


public class BaenastundFragment extends Fragment 
{
    Context context;
    TextView mannak;
    ViewPager pager;
	TruinPagerAdapter mPagerAdapter;
    
	public BaenastundFragment(){}
	
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
		
        View rootView = inflater.inflate(R.layout.fragment_baenastund, container, false);
       
        mannak = (TextView) rootView.findViewById(R.id.Mannakorn);
        context = getActivity();
        String[] Mannakorn = context.getResources().getStringArray(R.array.mannakorn);
        String randomMannakorn = Mannakorn[new Random().nextInt(Mannakorn.length)];
        
        pager = (ViewPager) rootView.findViewById(R.id.viewpager);
        
        mannak.setText(randomMannakorn);

        return rootView;
    }
	
	public boolean onCreateOptionsMenu(Menu menu) {
		getActivity().getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
	
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
	        case R.id.about:
	        	Intent AboutScreen = new Intent(getActivity().getApplicationContext(), About.class);
	   		 	startActivity(AboutScreen);
	            return true;
	        default:
	            return super.onOptionsItemSelected(item);
		}
	}
	
}

