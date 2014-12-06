package com.lpii.evma.view;

import com.lpii.evma.R;
import com.lpii.evma.model.SpinnerNavItem;
import com.lpii.evma.view.EventsUI; 
import com.lpii.evma.view.LoginEv;
import com.lpii.evma.adapter.TitleNavigationAdapter;
 

import java.util.ArrayList;
import java.util.List;
import android.app.ActionBar;
import android.app.ActionBar.OnNavigationListener;
import android.app.ActionBar.Tab;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button; 
import android.widget.Toast;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.widget.DrawerLayout;
import android.support.v4.widget.SwipeRefreshLayout;


public class EventDescriptionUI extends FragmentActivity implements OnNavigationListener,ActionBar.TabListener {

	
	Button btnLogin;
	
	// action bar
	private ActionBar actionBar;

	// Title navigation Spinner data
	private ArrayList<SpinnerNavItem> navSpinner;

	// Navigation adapter
	private TitleNavigationAdapter adapter;

	// Refresh menu item
	private MenuItem refreshMenuItem;
	
	List<Fragment> fragList = new ArrayList<Fragment>();
	
	//Test
	 private DrawerLayout mDrawerLayout;
	 private ActionBarDrawerToggle mDrawerToggle;
	 private SwipeRefreshLayout refreshLayout;
	//Test
	 
	 public static Context appContext;
	 

	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_side_bar);
		appContext = getApplicationContext();
		
		 
		 
		
		actionBar = getActionBar();

		// Hide the action bar title
		actionBar.setDisplayShowTitleEnabled(false);

		// Enabling Spinner dropdown navigation
		actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);//NAVIGATION_MODE_LIST
		 
		// Spinner title navigation data
		navSpinner = new ArrayList<SpinnerNavItem>();
		navSpinner.add(new SpinnerNavItem("Local", R.drawable.ic_action_locate));
		navSpinner.add(new SpinnerNavItem("My Places", R.drawable.ic_action_locate));
		navSpinner.add(new SpinnerNavItem("Checkins", R.drawable.ic_action_send));
		navSpinner.add(new SpinnerNavItem("Latitude", R.drawable.ic_action_video));

		// title drop down adapter
		adapter = new TitleNavigationAdapter(getApplicationContext(),
				navSpinner);

		// assigning the spinner navigation
		actionBar.setListNavigationCallbacks(adapter, this);

		// Changing the action bar icon
		actionBar.setIcon(R.drawable.ic_launcher);
		
		 
		 mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layoutw);
	     mDrawerToggle = new ActionBarDrawerToggle(
	                this,                  /* host Activity */
	                mDrawerLayout,         /* DrawerLayout object */
	                R.drawable.ic_drawer,  /* nav drawer icon to replace 'Up' caret */
	                R.string.drawer_open,  /* "open drawer" description */
	                R.string.drawer_close  /* "close drawer" description */
	        );

	    

	     // Set the drawer toggle as the DrawerListener
	    mDrawerLayout.setDrawerListener(mDrawerToggle);

    	actionBar.setDisplayHomeAsUpEnabled(true);
		actionBar.setHomeButtonEnabled(true);   
		
		actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
        
        ActionBar.Tab Evcard = actionBar.newTab().setText("Description").setTabListener(this);
        ActionBar.Tab EvParticipants = actionBar.newTab().setText("Participants").setTabListener(this);
        
         
        actionBar.addTab(Evcard);
        actionBar.addTab(EvParticipants);
		//
		
	}
	@Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        // Sync the toggle state after onRestoreInstanceState has occurred.
        mDrawerToggle.syncState();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        mDrawerToggle.onConfigurationChanged(newConfig);
    }

    

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main_ev, menu);

		return super.onCreateOptionsMenu(menu);

	}

	  

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Take appropriate action for each action item click
		System.out.println(item.getItemId());
		System.out.println("object:  " + item);
		if (mDrawerToggle.onOptionsItemSelected(item)) {
	          return true;
	        }
		switch (item.getItemId()) {
		case R.id.action_search:
			Intent i = new Intent(EventDescriptionUI.this,LoginEv.class);
			startActivity(i);
			return true;
		case R.id.action_settings:
			// location found
			LocationFound();
			return true;
		case R.id.action_refresh:
			// refresh
			refreshMenuItem = item;
			// load the data from server
			new SyncData().execute();
			return true;
		default:
			System.out.println("default");
			return super.onOptionsItemSelected(item);
		}
	}

 
	private void LocationFound() {
		Intent i = new Intent(EventDescriptionUI.this, LoginEv.class);
		startActivity(i);
	}

	/*
	 * Actionbar navigation item select listener
	 */
	@Override
	public boolean onNavigationItemSelected(int itemPosition, long itemId) {
		// Action to be taken after selecting a spinner item
		return false;
	}


	private class SyncData extends AsyncTask<String, Void, String> {
		@Override
		protected void onPreExecute() {
			// set the progress bar view
			refreshMenuItem.setActionView(R.layout.action_progressbar);

			refreshMenuItem.expandActionView();
		}

		@Override
		protected String doInBackground(String... params) {
			// not making real request in this demo
			// for now we use a timer to wait for sometime
			try {
				Thread.sleep(3000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			return null;
		}

		@Override
		protected void onPostExecute(String result) {
			refreshMenuItem.collapseActionView();
			// remove the progress bar view
			refreshMenuItem.setActionView(null);
		}
	}

	@Override
	public void onTabReselected(Tab tab, FragmentTransaction ft) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void onTabSelected(Tab tab, FragmentTransaction ft) {
		// TODO Auto-generated method stub
		System.out.println("tabslected");
		Event_Description_fragment Evdescirpion = new Event_Description_fragment();
		getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, Evdescirpion).commit();
		//ft.replace(R.id.fragment_container, fragment);
		
	}
	@Override
	public void onTabUnselected(Tab tab, FragmentTransaction ft) {
		// TODO Auto-generated method stub
		EventsUI androidversionlist = new EventsUI();
		getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, androidversionlist).commit();
		//ft.replace(R.id.fragment_container, fragment);
		
	}

	 
	
}

  