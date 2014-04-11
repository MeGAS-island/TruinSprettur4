package is.tru.truin;

import is.tru.adapter.NavDrawerListAdapter;
import is.tru.model.NavDrawerItem;
import java.util.ArrayList;
import java.util.Calendar;
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
import android.app.AlarmManager;
import android.app.AlertDialog;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.DialogInterface.OnClickListener;
import android.content.res.Configuration;
import android.content.res.TypedArray;
import android.media.RingtoneManager;
import android.net.Uri;
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

@SuppressLint("NewApi")
public class MainActivity extends FragmentActivity {
		
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
                   .setMessage("�� ert ekki tengdur netinu. Vinsamlegast kveiktu � netinu til a� nota appi�.")
                   .setCancelable(false)
            	   .setPositiveButton(ok, new OnClickListener() {
            		   public void onClick(DialogInterface dialog, int id) { }
            		   
        			});
                   AlertDialog alertDialog = alertDialogBuilder.create();
                   alertDialog.show();
        		}
        else {
        	new LoadInstagramPhotos().execute();
        }

        
        Intent myIntent = new Intent(MainActivity.this , NotificationService.class);     
        AlarmManager alarmManager = (AlarmManager)getSystemService(ALARM_SERVICE);
        PendingIntent pendingIntent = PendingIntent.getService(MainActivity.this, 0, myIntent, 0);
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, 20);
        calendar.set(Calendar.MINUTE, 35);
        calendar.set(Calendar.SECOND, 00);
        alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), 24*60*60*1000 , pendingIntent);  //set repeating every 24 hours
        
		mTitle = mDrawerTitle = getTitle();
		navMenuTitles = getResources().getStringArray(R.array.nav_drawer_items);
		navMenuIcons = getResources()
				.obtainTypedArray(R.array.nav_drawer_icons);
		mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
		mDrawerList = (ListView) findViewById(R.id.list_slidermenu);
		navDrawerItems = new ArrayList<NavDrawerItem>();
		pager = (TruinViewPager) findViewById(R.id.viewpager);

		// Bænastund
		navDrawerItems.add(new NavDrawerItem(navMenuTitles[0], navMenuIcons.getResourceId(0, -1)));
		// Pistlar og postillur
		navDrawerItems.add(new NavDrawerItem(navMenuTitles[1], navMenuIcons.getResourceId(1, -1)));
		// Fyribænaefni
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
	        case R.id.about:
	        	Intent AboutScreen = new Intent(getApplicationContext(), About.class);
	   		 	startActivity(AboutScreen);
	            return true;
	        default:
	            return super.onOptionsItemSelected(item);
		}
	}
	
	@SuppressLint("NewApi")
	public Notification createNotification(){
		// define sound URI, the sound to be played when there's a notification
	  	Uri soundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
	   	 
	   	// intent triggered, you can add other intent for other actions
	   	Intent intent = new Intent(this, NotificationReceiver.class);
	   	PendingIntent pIntent = PendingIntent.getActivity(this, 0, intent, 0);
	   	 
	   	// this is it, we'll build the notification!
	   	// in the addAction method, if you don't want any icon, just set the first param to 0
	   	Notification mNotification = new Notification.Builder(this)
	   	.setContentTitle("New Post!")
	   	.setContentText("Here's an awesome update for you!")
	   	.setSmallIcon(R.drawable.ic_bible_black)
	   	.setContentIntent(pIntent)
	   	.setSound(soundUri)
	   	.addAction(R.drawable.ic_bible_black, "View", pIntent)
	   	.addAction(0, "Remind", pIntent)
	   	.build();
	   	 
	   	NotificationManager notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
	   	 
	   	// If you want to hide the notification after it was selected, do the code below
	   	// myNotification.flags |= Notification.FLAG_AUTO_CANCEL;
	   	int notificationNumber = 0;
	   	//notificationManager.notify(mNotification);
	   	return mNotification;
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
			
			else if(fragmentManager.findFragmentByTag("dagurvalinn")!=null) {
				f = fragmentManager.findFragmentByTag("dagurvalinn");
				transaction.remove(f);
				transaction.commit();
				Log.d("m", "dagurvalinn");
			}
			
			else if(fragmentManager.findFragmentByTag("randombaen")!=null) {
				f = fragmentManager.findFragmentByTag("randombaen");
				transaction.remove(f);
				transaction.commit();
				Log.d("m", "randombaen");
			}
			
			else if(fragmentManager.findFragmentByTag("salmabok")!=null) {
				f = fragmentManager.findFragmentByTag("salmabok");
				transaction.remove(f);
				transaction.commit();
				Log.d("m", "salmabok");
			}
			
			else if(fragmentManager.findFragmentByTag("fyrirbaen")!=null) {
				f = fragmentManager.findFragmentByTag("fyrirbaen");
				transaction.remove(f);
				transaction.commit();
				Log.d("m", "fyrirbaen");
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
			
		}
		else {
			fragments.clear();
			mPagerAdapter.notifyDataSetChanged();
		}

		this.mPagerAdapter = new TruinPagerAdapter(super.getSupportFragmentManager(), fragments);
		
		pager.setAdapter(this.mPagerAdapter);
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
	
	

}
