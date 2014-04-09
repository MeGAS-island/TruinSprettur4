package is.tru.truin;

import is.tru.adapter.NavDrawerListAdapter;
import is.tru.model.NavDrawerItem;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import util.JSONParser;
import util.XMLParser;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.res.Configuration;
import android.content.res.TypedArray;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import is.tru.truin.TruinViewPager;
import android.support.v4.widget.DrawerLayout;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

public class MainActivity extends FragmentActivity {
	
	// All static variables
	static final String URL = "http://api.androidhive.info/pizza/?format=xml";
	// XML node keys
	static final String KEY_ITEM = "item"; // parent node
	static final String KEY_ID = "id";
	static final String KEY_NAME = "name";
	static final String KEY_COST = "cost";
	static final String KEY_DESC = "description";
	
	JSONParser jParser = new JSONParser();
	JSONArray photos = null;
	private static String url = "http://blikar.is/app_afrit/app/truPhotos";
	static final String TAG_INSTAGRAM = "instagram";
	static final String TAG_PHOTO= "photo";
	static final String TAG_USERS = "user";
	
	private DrawerLayout mDrawerLayout;
	private ListView mDrawerList;
	private ActionBarDrawerToggle mDrawerToggle;
	private CharSequence mDrawerTitle;
	private CharSequence mTitle;
	private String[] navMenuTitles;
	private TypedArray navMenuIcons;
	private ArrayList<NavDrawerItem> navDrawerItems;
	private NavDrawerListAdapter adapter;
	TruinViewPager pager;
	TruinPagerAdapter mPagerAdapter;
	List<Fragment> fragments;
	boolean pageron = false;
	
	Boolean isInternetOn = false;
    ConnectionDetector cd;
    final Context context = this;
    CharSequence ok = "OK";
	

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		cd = new ConnectionDetector(getApplicationContext());
        isInternetOn = cd.isConnectingToInternet();
        if(!isInternetOn){
            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);
            alertDialogBuilder.setTitle("Engin nettenging");
            alertDialogBuilder
                   .setMessage("Þú ert ekki tengdur netinu. Vinsamlegast kveiktu á netinu til að nota appið.")
                   .setCancelable(false)
            	   .setPositiveButton(ok, new OnClickListener() {
            		   public void onClick(DialogInterface dialog, int id) { }
            		   
        			});
                   AlertDialog alertDialog = alertDialogBuilder.create();
                   alertDialog.show();
        		}
        else {
        	new LoadInstagramPhotos().execute();
			new LoadPostillur().execute();
        }

		mTitle = mDrawerTitle = getTitle();
		navMenuTitles = getResources().getStringArray(R.array.nav_drawer_items);
		navMenuIcons = getResources()
				.obtainTypedArray(R.array.nav_drawer_icons);
		mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
		mDrawerList = (ListView) findViewById(R.id.list_slidermenu);
		navDrawerItems = new ArrayList<NavDrawerItem>();
		pager = (TruinViewPager) findViewById(R.id.viewpager);

		// BÃ¦nastund
		navDrawerItems.add(new NavDrawerItem(navMenuTitles[0], navMenuIcons.getResourceId(0, -1)));
		// Pistlar og postillur
		navDrawerItems.add(new NavDrawerItem(navMenuTitles[1], navMenuIcons.getResourceId(1, -1)));
		// FyribÃ¦naefni
		navDrawerItems.add(new NavDrawerItem(navMenuTitles[2], navMenuIcons.getResourceId(2, -1)));
		// Myndir
		navDrawerItems.add(new NavDrawerItem(navMenuTitles[3], navMenuIcons.getResourceId(3, -1)));
		// Almanak
		navDrawerItems.add(new NavDrawerItem(navMenuTitles[4], navMenuIcons.getResourceId(4, -1)));
		
		navMenuIcons.recycle();
		mDrawerList.setOnItemClickListener(new SlideMenuClickListener());
		adapter = new NavDrawerListAdapter(getApplicationContext(),
				navDrawerItems);
		mDrawerList.setAdapter(adapter);
		

		getActionBar().setDisplayHomeAsUpEnabled(true);
		getActionBar().setHomeButtonEnabled(true);

		mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout,
				R.drawable.ic_drawer,
				R.string.app_name,
				R.string.app_name
		) {
			public void onDrawerClosed(View view) {
				getActionBar().setTitle(mTitle);
				invalidateOptionsMenu();
			}

			public void onDrawerOpened(View drawerView) {
				getActionBar().setTitle(mDrawerTitle);
				invalidateOptionsMenu();
			}
		};
		mDrawerLayout.setDrawerListener(mDrawerToggle);

		if (savedInstanceState == null) {
			displayView(0);
		}
	}

	private class SlideMenuClickListener implements
			ListView.OnItemClickListener {
		@Override
		public void onItemClick(AdapterView<?> parent, View view, int position,
				long id) {
			displayView(position);
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		if (mDrawerToggle.onOptionsItemSelected(item)) {
			return true;
		}
		switch (item.getItemId()) {
		case R.id.action_settings:
			return true;
		default:
			return super.onOptionsItemSelected(item);
		}
	}

	@Override
	public boolean onPrepareOptionsMenu(Menu menu) {
		boolean drawerOpen = mDrawerLayout.isDrawerOpen(mDrawerList);
		menu.findItem(R.id.action_settings).setVisible(!drawerOpen);
		return super.onPrepareOptionsMenu(menu);
	}

	private void displayView(int position) {
		boolean baenastund = false;
		AlmanakFragment Afragment = null;
		MyndirFragment Mfragment = null;
		BaenirFragment Bafragment = null;
		PostillurFragment Pfragment = null;
		switch (position) {
		case 0:
			baenastund = true;
			break;
		case 1:
			Afragment = new AlmanakFragment();
			break;
		case 2:
			Mfragment = new MyndirFragment();
			break;
		case 3:
			Bafragment = new BaenirFragment();
			break;
		case 4:
			Pfragment = new PostillurFragment();
			break;
		default:
			break;
		}
		if (Mfragment != null) {
			FragmentManager fragmentManager = getFragmentManager();
			FragmentTransaction transaction = fragmentManager.beginTransaction();
			transaction.replace(R.id.frame_container, Mfragment, "myndir");
			transaction.commit();
			
			if(pager.getPagingEnabled() == true) {
				pager.setPagingEnabled(false);
				this.initialisePaging(false);
			}
			
			mDrawerList.setItemChecked(position, true);
			mDrawerList.setSelection(position);
			setTitle(navMenuTitles[position]);
			mDrawerLayout.closeDrawer(mDrawerList);
		} else if (baenastund == true){
			FragmentManager fragmentManager = getFragmentManager();
			FragmentTransaction transaction = fragmentManager.beginTransaction();
			/*transaction.remove();
			transaction.commit();*/
			android.app.Fragment f;
			
			if(fragmentManager.findFragmentByTag("myndir")!=null) {
				f = fragmentManager.findFragmentByTag("myndir");
				transaction.remove(f);
				Log.d("m", "myndir");
			}
			else if(fragmentManager.findFragmentByTag("baenir")!=null) {
				f = fragmentManager.findFragmentByTag("baenir");
				transaction.remove(f);
				transaction.commit();
				Log.d("m", "baenir");
			}
			else if(fragmentManager.findFragmentByTag("postillur")!=null) {
				f = fragmentManager.findFragmentByTag("postillur");
				transaction.remove(f);
				transaction.commit();
				Log.d("m", "postillur");
			}
			else if(fragmentManager.findFragmentByTag("almanak")!=null) {
				f = fragmentManager.findFragmentByTag("almanak");
				transaction.remove(f);
				transaction.commit();
				Log.d("m", "almanak");
			}
				
			if(pageron == false) {
				this.initialisePaging(true);
			}
			else if(pager.getPagingEnabled() == false){
				this.initialisePaging(true);
				pager.setPagingEnabled(true);
			}

			mDrawerList.setItemChecked(position, true);
			mDrawerList.setSelection(position);
			setTitle(navMenuTitles[position]);
			mDrawerLayout.closeDrawer(mDrawerList); 
		} else if (Bafragment != null){
			FragmentManager fragmentManager = getFragmentManager();
			fragmentManager.beginTransaction()
					.replace(R.id.frame_container, Bafragment, "baenir").commit();
			
			if(pager.getPagingEnabled() == true) {
				pager.setPagingEnabled(false);
				this.initialisePaging(false);
			}
			
			mDrawerList.setItemChecked(position, true);
			mDrawerList.setSelection(position);
			setTitle(navMenuTitles[position]);
			mDrawerLayout.closeDrawer(mDrawerList);
		} else if (Afragment != null){
			FragmentManager fragmentManager = getFragmentManager();
			fragmentManager.beginTransaction()
					.replace(R.id.frame_container, Afragment, "almanak").commit();
			
			if(pager.getPagingEnabled() == true) {
				pager.setPagingEnabled(false);
				this.initialisePaging(false);
			}
			
			mDrawerList.setItemChecked(position, true);
			mDrawerList.setSelection(position);
			setTitle(navMenuTitles[position]);
			mDrawerLayout.closeDrawer(mDrawerList);
		} else if (Pfragment != null){
			FragmentManager fragmentManager = getFragmentManager();
			fragmentManager.beginTransaction()
					.replace(R.id.frame_container, Pfragment, "postillur").commit();
			
			if(pager.getPagingEnabled() == true) {
				pager.setPagingEnabled(false);
				this.initialisePaging(false);
			}
			
			mDrawerList.setItemChecked(position, true);
			mDrawerList.setSelection(position);
			setTitle(navMenuTitles[position]);
			mDrawerLayout.closeDrawer(mDrawerList);
		} else {
			Log.e("MainActivity", "Error in creating fragment");
		}
	}

	@Override
	public void setTitle(CharSequence title) {
		mTitle = title;
		getActionBar().setTitle(mTitle);
	}

	@Override
	protected void onPostCreate(Bundle savedInstanceState) {
		super.onPostCreate(savedInstanceState);
		mDrawerToggle.syncState();
	}

	@Override
	public void onConfigurationChanged(Configuration newConfig) {
		super.onConfigurationChanged(newConfig);
		mDrawerToggle.onConfigurationChanged(newConfig);
	}
	
	private void initialisePaging(boolean add) {
		fragments = new ArrayList<Fragment>();
		pageron = true;
		
		if(add==true) {
			fragments.add(0, new BaenastundFragment());
			fragments.add(1, new BaenastundKyrrdFragment());
			fragments.add(2, new BaenastundSignaFragment());
			fragments.add(3, new BaenastundOrdGudsFragment());
			fragments.add(4, new BaenastundBaeninFragment());
			fragments.add(5, new BaenastundBlessunFragment());
			
	//		this.mPagerAdapter = new TruinPagerAdapter(super.getSupportFragmentManager(), fragments);
			
		//	pager.setAdapter(this.mPagerAdapter);
		}
		else {
			fragments.clear();
			mPagerAdapter.notifyDataSetChanged();
		}

		this.mPagerAdapter = new TruinPagerAdapter(super.getSupportFragmentManager(), fragments);
		
		pager.setAdapter(this.mPagerAdapter);
	}
	
	private List<Fragment> getFragments(){
		List<Fragment> fragments = new ArrayList<Fragment>();
		
		fragments.add(new BaenastundFragment());
		fragments.add(new BaenastundKyrrdFragment());
		fragments.add(new BaenastundSignaFragment());
		fragments.add(new BaenastundOrdGudsFragment());
		fragments.add(new BaenastundBaeninFragment());
		fragments.add(new BaenastundBlessunFragment());
		
		return fragments;
	}
	
	
	class LoadInstagramPhotos extends AsyncTask<String, String, String> {
		
		@SuppressLint("NewApi")
		protected String doInBackground(String... args) {
			JSONObject json = jParser.getJSONFromUrl(url);
	
			try {
				photos = json.getJSONArray(TAG_INSTAGRAM);
				
				for(int i = 0; i < photos.length(); i++){
					JSONObject c = photos.getJSONObject(i);
					String photo = c.getString(TAG_PHOTO);
					String user = c.getString(TAG_USERS);
					Constants.IMAGES[i] = photo;
					Constants.USERS[i] = user;
				}
			} catch (JSONException e) {
				e.printStackTrace();
			}
		return null;		
		}				
	}
	
	class LoadPostillur extends AsyncTask<String, String, String> {

		@SuppressLint("NewApi")
		protected String doInBackground(String... args) {
			
			XMLParser parser = new XMLParser();
			String xml = parser.getXmlFromUrl(URL); // getting XML
			Document doc = parser.getDomElement(xml); // getting DOM element

			NodeList nl = doc.getElementsByTagName(KEY_ITEM);
			// looping through all item nodes <item>
			for (int i = 0; i < nl.getLength(); i++) {
				// creating new HashMap
				Element e = (Element) nl.item(i);
				
				String id = parser.getValue(e, KEY_ID);
				String name = parser.getValue(e, KEY_NAME);
				String cost = parser.getValue(e, KEY_COST);
				String desc = parser.getValue(e, KEY_DESC);
				
				Constants.id[i] = id;
				Constants.name[i] = name;
				Constants.cost[i] = cost;
				Constants.desc[i] = desc;
				
				Log.d("id: ", id);
				
			}
			return null;
		}
	}
	

}
