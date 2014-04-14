package is.tru.truin;


import java.util.Arrays;
import java.util.List;

import android.app.Fragment;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

public class AlmanakFragment extends Fragment implements OnClickListener {

	Button Salmabok;
	
	public AlmanakFragment(){}
	
	Spinner ar;
	Spinner manudur;
	Spinner dagur;
	
	ArrayAdapter<String> aradapter;
	ArrayAdapter<String> manuduradapter;
	ArrayAdapter<String> daguradapter;
	
	String [] arValues;
	String [] manudurValues;
	String [] dagurValues;

	@Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
 
        View rootView = inflater.inflate(R.layout.fragment_almanak, container, false);
        
        setSpinnerContent(rootView);
        
        
        Button Salmabok = (Button) rootView.findViewById(R.id.Salmabok);
        Salmabok.setOnClickListener(new View.OnClickListener() {
        
            @Override
            public void onClick(View v) {
            	Fragment newFragment = new SalmabokFragment();
                android.app.FragmentTransaction transaction = getFragmentManager().beginTransaction();
                transaction.replace(R.id.frame_container, newFragment, "salmabok");
                transaction.addToBackStack(null);
                transaction.commit();
            }
        });
        
             
        Button VeljaDag = (Button) rootView.findViewById(R.id.btnVeljaDag);
        VeljaDag.setOnClickListener(new View.OnClickListener() {
        
            @Override
            public void onClick(View v) {
            	Fragment newFragment = new DagurValinnFragment();
                android.app.FragmentTransaction transaction = getFragmentManager().beginTransaction();
                transaction.replace(R.id.frame_container, newFragment, "dagurvalinn");
                transaction.addToBackStack(null);
                transaction.commit();
            }
        });
        
        return rootView;
    }
	
	public boolean isLeapYear(String str_year) {
		
		int year = Integer.parseInt(str_year);
		boolean isLeapYear = ((year % 4 == 0) && (year % 100 != 0) || (year % 400 == 0));
		
		return isLeapYear;
	}
	
	public boolean isFebruary(String str_month) {
		
		if (str_month.equals("febrúar")) return true;
		
		return false;
	}
	
	

	private void setSpinnerContent( View view )
	{
		String [] arValues = {"2005", "2006", "2007", "2008", "2009", "2010", "2011", "2012", "2013", "2014", };
		String [] manudurValues = {"janúar", "febrúar", "mars", "apríl", "maí", "júní", "júlí", "ágúst", "september", "október", "nóvember","desember", };
		String [] dagurValues = {"1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20", "21", "22", "23", "24", "25", "26", "27", "28", "29", "30", "31",};

		ar = (Spinner)view.findViewById(R.id.ar);
        aradapter = new ArrayAdapter<String>(this.getActivity(), android.R.layout.simple_spinner_item, arValues);
        ar.setAdapter( aradapter );
        //ar.setOnItemSelectedListener(new MyOnItemSelectedListener());
        ar.setOnItemSelectedListener(new OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view,
                    int pos, long id) {
                List<String> s = Arrays.asList(getResources().getStringArray(R.array.dagarValues));
                if (isFebruary((String) manudur.getSelectedItem()) && isLeapYear((String) ar.getSelectedItem())) {
                	s = s.subList(0,29);
                    daguradapter = new  ArrayAdapter<String>(parent.getContext(), android.R.layout.simple_spinner_item,s);
                    dagur.setAdapter(daguradapter);
                } else if (isFebruary((String) manudur.getSelectedItem()) && !isLeapYear((String) ar.getSelectedItem())) {
                    s = s.subList(0,28);                    
                    daguradapter = new  ArrayAdapter<String>(parent.getContext(), android.R.layout.simple_spinner_item,s);
                    dagur.setAdapter(daguradapter);
                } 
            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
                // TODO Auto-generated method stub

            }
        });
         
        manudur = (Spinner)view.findViewById(R.id.manudur);
        manuduradapter = new ArrayAdapter<String>(this.getActivity(), android.R.layout.simple_spinner_item, manudurValues);
        manudur.setAdapter( manuduradapter );
        //manudur.setOnItemSelectedListener(new MyOnItemSelectedListener());
        
        manudur.setOnItemSelectedListener(new OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view,
                    int pos, long id) {
                List<String> s = Arrays.asList(getResources().getStringArray(R.array.dagarValues));
                if (pos == 0 || pos == 2 || pos == 4 || pos == 6 || pos == 7 || pos == 9 || pos == 11) {
                    daguradapter = new  ArrayAdapter<String>(parent.getContext(), android.R.layout.simple_spinner_item,s);
                    dagur.setAdapter(daguradapter);
                } else if (pos == 1 && !isLeapYear((String) ar.getSelectedItem())) {
                    s = s.subList(0,28);                    
                    daguradapter = new  ArrayAdapter<String>(parent.getContext(), android.R.layout.simple_spinner_item,s);
                    dagur.setAdapter(daguradapter);
                } else if (pos == 1 && isLeapYear((String) ar.getSelectedItem())) {
                    s = s.subList(0,29);                    
                    daguradapter = new  ArrayAdapter<String>(parent.getContext(), android.R.layout.simple_spinner_item,s);
                    dagur.setAdapter(daguradapter);
                } else {
                    s = s.subList(0,30);                    
                    daguradapter = new  ArrayAdapter<String>(parent.getContext(), android.R.layout.simple_spinner_item,s);
                    dagur.setAdapter(daguradapter);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
                // TODO Auto-generated method stub

            }
        });
        
        
        dagur = (Spinner)view.findViewById(R.id.dagur);
        daguradapter = new ArrayAdapter<String>(this.getActivity(), android.R.layout.simple_spinner_item, dagurValues);
        dagur.setAdapter( daguradapter );
        dagur.setOnItemSelectedListener(new MyOnItemSelectedListener());
        

}

@Override
public void onClick(View v) {
// TODO Auto-generated method stub

}

public class MyOnItemSelectedListener implements OnItemSelectedListener{


public void onItemSelected(AdapterView<?> parent, View v, int pos,long id) {
	Constants.ar_selected = (String) ar.getSelectedItem();
	Constants.manudur_selected = (String) manudur.getSelectedItem();
	Constants.dagur_selected = (String) dagur.getSelectedItem();
	

/*
	if (manudur.getSelectedItem().equals("apríl") || manudur.getSelectedItem().equals("júní") || manudur.getSelectedItem().equals("september") || manudur.getSelectedItem().equals("nóvember")) {
		daguradapter = ArrayAdapter.createFromResource(parent.getContext(), R.array.dagarValuesStuttur,android.R.layout.simple_spinner_item);
		daguradapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		dagur.setAdapter(daguradapter);
	}
	else if (manudur.getSelectedItem().equals("febrúar") && (ar.getSelectedItem().equals("2012") || ar.getSelectedItem().equals("2008") || ar.getSelectedItem().equals("2004"))) {
		daguradapter = ArrayAdapter.createFromResource(parent.getContext(), R.array.dagarValuesFebHlaup,android.R.layout.simple_spinner_item);
		daguradapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		dagur.setAdapter(daguradapter);
	}
	else if (manudur.getSelectedItem().equals("febrúar") && (!ar.getSelectedItem().equals("2012") && !ar.getSelectedItem().equals("2008") && !ar.getSelectedItem().equals("2004"))) {
		daguradapter = ArrayAdapter.createFromResource(parent.getContext(), R.array.dagarValuesFeb,android.R.layout.simple_spinner_item);
		daguradapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		dagur.setAdapter(daguradapter);
	}
	else {
		daguradapter = ArrayAdapter.createFromResource(parent.getContext(), R.array.dagarValues,android.R.layout.simple_spinner_item);
		daguradapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		dagur.setAdapter(daguradapter);
	}
*/
}



public void onNothingSelected(AdapterView<?> arg0) {
// TODO Auto-generated method stub

}

}


}

