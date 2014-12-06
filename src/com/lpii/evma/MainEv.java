package com.lpii.evma;

import java.util.ArrayList;
import java.util.List;

import android.R.integer;
import android.app.ActionBar;
import android.app.ActionBar.OnNavigationListener;
import android.app.ActionBar.Tab;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.database.Cursor;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.widget.DrawerLayout;
import android.support.v4.widget.SwipeRefreshLayout;
import android.text.format.Formatter;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

import com.lpii.evma.adapter.TitleNavigationAdapter;
import com.lpii.evma.controller.ConnectionDetector;
import com.lpii.evma.model.Event;
import com.lpii.evma.model.SpinnerNavItem;
import com.lpii.evma.view.EventsUI;
import com.lpii.evma.view.HomeStart;
import com.lpii.evma.view.LoginEv;
import com.lpii.evma.view.MyTicketUpcomingEvents;
import com.lpii.evma.view.NoEventsError;
import com.lpii.evma.view.organizer.Organizer_Events;
import com.lpii.evma.view.organizer.Organizer_event_create;

//ActionBarActivity 
public class MainEv extends FragmentActivity implements OnClickListener,
		OnNavigationListener, ActionBar.TabListener {

	// Button btnLogin;

	// action bar
	private ActionBar actionBar;
	ConnectionDetector cd;
    public static int RESULT_LOAD_IMAGE = 99;
    EvmaApp mEvmaApp;

	// Title navigation Spinner data
	private ArrayList<SpinnerNavItem> navSpinner;

	// Navigation adapter
	private TitleNavigationAdapter adapter;

	// Refresh menu item
	public MenuItem refreshMenuItem;

	List<Fragment> fragList = new ArrayList<Fragment>();

	// Test
	private DrawerLayout mDrawerLayout;
	private ActionBarDrawerToggle mDrawerToggle;
	private SwipeRefreshLayout refreshLayout;
	// Test

	public static Context appContext;
	Button btnMyTickets;
	Button btnDiscover;
	Button SignOut;
	 
	TextView tvuserName;
	Boolean isInternetPresent ;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.activity_main_ev);
		appContext = getApplicationContext();
		mEvmaApp =  (EvmaApp) getApplicationContext();
		cd = new ConnectionDetector(getApplicationContext());
		isInternetPresent = cd.isConnectingToInternet();
		btnMyTickets = (Button) findViewById(R.id.drawer_layout_my_tickets);
		btnDiscover = (Button) findViewById(R.id.drawer_layout_find_events);
		SignOut = (Button) findViewById(R.id.SignOut);
		tvuserName = (TextView) findViewById(R.id.userName);
		tvuserName.setText(EvmaApp.CurrentUser.getFull_name());
		btnDiscover.setOnClickListener(new OnClickListener() {
	 
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				System.out.println("cls");
				Drawable d = getResources().getDrawable(
						R.drawable.drawer_item_selected);
				btnDiscover.setBackgroundDrawable(d);

				d = getResources().getDrawable(
						R.drawable.drawer_item_background);
				btnMyTickets.setBackgroundDrawable(d);
				EvmaApp.MyTicketsReq = false;
				CreateDefaulTabs();
				// Intent intUser = new
				// Intent(MainEv.this,user_my_tickets.class);
				// startActivity(intUser);

			}
		});

		btnMyTickets.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				System.out.println("cls");
				
				Drawable d = getResources().getDrawable(R.drawable.drawer_item_selected);
				btnMyTickets.setBackgroundDrawable(d);
				
				d = getResources().getDrawable(R.drawable.drawer_item_background);
				btnDiscover.setBackgroundDrawable(d);
				EvmaApp.MyTicketsReq = true;
				CreatemyTicketTab();
				// Intent intUser = new
				// Intent(MainEv.this,user_my_tickets.class);
				// startActivity(intUser);

			}
		});
		
		SignOut.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				SharedPreferences preferences = getSharedPreferences("Mypref", 0);
				preferences.edit().remove("CurrentUser").commit();		
				Intent intx = new Intent(MainEv.this,HomeStart.class);
				startActivity(intx);
				}
		});
		
		WifiManager wifiMgr = (WifiManager) getSystemService(WIFI_SERVICE);
		WifiInfo wifiInfo = wifiMgr.getConnectionInfo();
		int ip = wifiInfo.getIpAddress();
		String ipAddress = Formatter.formatIpAddress(ip);
		System.out.println("====> " + ipAddress);
		
		Intent intbillet = getIntent();
		if(intbillet != null){
			if (intbillet.getStringExtra("Action") !=null) {
				Drawable d = getResources().getDrawable(R.drawable.drawer_item_selected);
				btnMyTickets.setBackgroundDrawable(d);
				
				d = getResources().getDrawable(R.drawable.drawer_item_background);
				btnDiscover.setBackgroundDrawable(d);
				EvmaApp.MyTicketsReq = true;
				CreatemyTicketTab();
			}
			 
		}

		// btnLogin = (Button) findViewById(R.id.btnLogin);
		// btnLogin.setOnClickListener(this);

		actionBar = getActionBar();
		Drawable d = getResources().getDrawable(R.drawable.action_bar_bg_darawable);
		actionBar.setBackgroundDrawable(d);
		

		// Hide the action bar title
		actionBar.setDisplayShowTitleEnabled(false);

		// Enabling Spinner dropdown navigation
		actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);// NAVIGATION_MODE_LIST
		/*
		 * Tab tab = actionBar.newTab();
		 * 
		 * tab.setText("Découvrir "); tab.setTabListener(this);
		 * actionBar.addTab(tab);
		 * 
		 * tab = actionBar.newTab(); tab.setText("Mes Plans ");
		 * tab.setTabListener(this); actionBar.addTab(tab);
		 */
		// Spinner title navigation data
		navSpinner = new ArrayList<SpinnerNavItem>();
		navSpinner
				.add(new SpinnerNavItem("Local", R.drawable.ic_action_locate));
		navSpinner.add(new SpinnerNavItem("My Places",
				R.drawable.ic_action_locate));
		navSpinner
				.add(new SpinnerNavItem("Checkins", R.drawable.ic_action_send));
		navSpinner.add(new SpinnerNavItem("Latitude",
				R.drawable.ic_action_video));

		// title drop down adapter
		adapter = new TitleNavigationAdapter(getApplicationContext(),
				navSpinner);

		// assigning the spinner navigation
		actionBar.setListNavigationCallbacks(adapter, this);

		// Changing the action bar icon
		actionBar.setIcon(R.drawable.ic_launcher);

		/*
		 * refreshLayout = (SwipeRefreshLayout)
		 * findViewById(R.id.refresh_layout);
		 * refreshLayout.setOnRefreshListener(new
		 * SwipeRefreshLayout.OnRefreshListener() {
		 * 
		 * @Override public void onRefresh() { new Handler().postDelayed(new
		 * Runnable() {
		 * 
		 * @Override public void run() { refreshLayout.setRefreshing(false); }
		 * }, 1000); } });
		 */

		//
		mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
		mDrawerToggle = new ActionBarDrawerToggle(this, /* host Activity */
		mDrawerLayout, /* DrawerLayout object */
		R.drawable.ic_drawer, /* nav drawer icon to replace 'Up' caret */
		R.string.drawer_open, /* "open drawer" description */
		R.string.drawer_close /* "close drawer" description */
		);

		mDrawerLayout.setDrawerListener(mDrawerToggle);

		actionBar.setDisplayHomeAsUpEnabled(true);
		actionBar.setHomeButtonEnabled(true);

		actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
		System.out.println("CurrentOrganizer  "  + EvmaApp.CurrentOrganizer.isEmpty()  +"    CurrentUsername"  +EvmaApp.CurrentUsername );
		if (EvmaApp.helperIAMGETTIHPIC) {
			System.out.println("EvmaApp.helperIAMGETTIHPIC  " +  EvmaApp.helperIAMGETTIHPIC);
		}else{
			if (!EvmaApp.CurrentOrganizer.isEmpty()) {
				ActionBar.Tab MyEventsTab = actionBar.newTab()
						.setText("Mes Events").setTabListener(this);
				actionBar.addTab(MyEventsTab);
				ActionBar.Tab NewEventTab = actionBar.newTab().setText("Nv. Event")
						.setTabListener(this);
				actionBar.addTab(NewEventTab);
			} else if (!EvmaApp.CurrentUsername.isEmpty()) {
				ActionBar.Tab EventsTab = actionBar.newTab().setText("Découvrir")
						.setTabListener(this);
				actionBar.addTab(EventsTab);
//				ActionBar.Tab MesPlansTab = actionBar.newTab().setText("Mes plans")
//						.setTabListener(this);
//				actionBar.addTab(MesPlansTab);

			}
		}
//		Boolean isInternetPresent = cd.isConnectingToInternet();
//		if (isInternetPresent) {
//			EventsUI androidversionlist = new EventsUI();
//			getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,androidversionlist).commit();
//		}

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
		refreshMenuItem = (MenuItem) findViewById(R.id.action_settings);

		System.out.println("sdfsdfd-------------");
		EvmaApp.refreshMenuItemx = refreshMenuItem;
		/*
		 * Associate searchable configuration with the SearchView SearchManager
		 * searchManager = (SearchManager)
		 * getSystemService(Context.SEARCH_SERVICE); SearchView searchView =
		 * (SearchView) menu.findItem(R.id.action_search) .getActionView();
		 * searchView.setSearchableInfo(searchManager
		 * .getSearchableInfo(getComponentName()));
		 */
		return super.onCreateOptionsMenu(menu);

	}

	@Override
	public void onClick(View arg0) {
		Intent i = new Intent(MainEv.this, LoginEv.class);
		startActivity(i);

	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Take appropriate action for each action item click
		System.out.println("item" + item.getItemId());
		System.out.println("object:  " + item);

		if (mDrawerToggle.onOptionsItemSelected(item)) {
			return true;
		}
		switch (item.getItemId()) {
		case R.id.action_search:
			Intent i = new Intent(MainEv.this, LoginEv.class);
			startActivity(i);
			return true;
		case R.id.action_settings:
			
			refreshMenuItem = item;
			refreshMenuItem.setActionView(R.layout.action_progressbar);
			refreshMenuItem.expandActionView();
			new SyncData().execute();

			return true;
		case R.id.action_refresh:
			// refresh
			refreshMenuItem = item;
			refreshMenuItem.setActionView(R.layout.action_progressbar);
			refreshMenuItem.expandActionView();
			// load the data from server
			new SyncData().execute();
			return true;
		default:
			System.out.println("default");
			return super.onOptionsItemSelected(item);
		}
	}

 

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
			System.out.println("refersh calllllled");
			if (isInternetPresent) {
				EventsUI androidversionlist = new EventsUI();
				getSupportFragmentManager()
						.beginTransaction()
						.replace(R.id.fragment_container,
								androidversionlist).commit();
			}

		}

		@Override
		protected String doInBackground(String... params) {
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
		System.out.println(tab.getPosition());

		Boolean isInternetPresent = cd.isConnectingToInternet();
		if (!isInternetPresent) {
			// ft.replace(R.id.fragment_container, fragment);
			System.out.println("Not Connected...");
			NoEventsError mNoEventsError = new NoEventsError();
			getSupportFragmentManager().beginTransaction()
					.replace(R.id.fragment_container, mNoEventsError).commit();

		} else {
			if (!EvmaApp.forceGetEvent) {

			} else {

				// EvmaApp.CurrentUsername = "Ahmedx";// adm1
				// EvmaApp.CurrentUserEmail =
				// "flahweb@gmail.com";//fidaghdour@synergie-media.com
				// EvmaApp.CurrentOrganizer = "";
				// EvmaApp.CurrentUsernameID = 11;
			}
			switch (tab.getPosition()) {
			case 0:
				if (EvmaApp.CurrentOrganizer.isEmpty()
						&& !EvmaApp.MyTicketsReq	
						) {
					System.out.println("get all events");
					EventsUI androidversionlist = new EventsUI();
					getSupportFragmentManager()
							.beginTransaction()
							.replace(R.id.fragment_container,
									androidversionlist).commit();
				} else if (EvmaApp.CurrentUsername.isEmpty()
						&& !EvmaApp.CurrentOrganizer.isEmpty()
						&& !EvmaApp.MyTicketsReq) {
					//EvmaApp.GlobaleventList = null;
					Organizer_Events Organizer_Eventslist = new Organizer_Events();
					getSupportFragmentManager()
							.beginTransaction()
							.replace(R.id.fragment_container,
									Organizer_Eventslist).commit();
				} else if (EvmaApp.CurrentUsername.isEmpty()
						&& !EvmaApp.CurrentOrganizer.isEmpty()
						&& EvmaApp.MyTicketsReq
						) {
					EvmaApp.MyTicketPast = false;

					MyTicketUpcomingEvents MyTicket = new MyTicketUpcomingEvents();
					getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, MyTicket).commit();
				}
				break;
			case 1:
				if (EvmaApp.CurrentOrganizer.isEmpty()
						&& !EvmaApp.CurrentUsername.isEmpty()  
						&& !EvmaApp.MyTicketsReq) {
					System.out.println("get all events");
					EventsUI androidversionlist = new EventsUI();
					getSupportFragmentManager()
							.beginTransaction()
							.replace(R.id.fragment_container,
									androidversionlist).commit();
				} else if (!EvmaApp.CurrentOrganizer.isEmpty()
						&& EvmaApp.CurrentUsername.isEmpty()
						&& !EvmaApp.MyTicketsReq
						) {
					Organizer_event_create mOrganizer_Event_create = new Organizer_event_create();
//					Bundle b  = new Bundle();
//					b.putParcelable(key, value);
//					mOrganizer_Event_create.setArguments(b);
					//Organizer_Event_create.setmEvmaApp(mEvmaApp);
					getSupportFragmentManager()
							.beginTransaction()
							.replace(R.id.fragment_container,
									mOrganizer_Event_create).commit();
				} else if (!EvmaApp.CurrentUsername.isEmpty()
						&& EvmaApp.CurrentOrganizer.isEmpty()
						&& EvmaApp.MyTicketsReq) {
					System.out.println("paaaast");
					EvmaApp.MyTicketPast = true;
					MyTicketUpcomingEvents MyTicketx = new MyTicketUpcomingEvents();
					getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, MyTicketx).commit();
				}
				break;
			default:
				break;
			}

		}

		// ft.replace(R.id.fragment_container, fragment);

	}

	@Override
	public void onTabUnselected(Tab tab, FragmentTransaction ft) {
		// TODO Auto-generated method stub
		// EventsUI androidversionlist = new EventsUI();
		// getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
		// androidversionlist).commit();
		// ft.replace(R.id.fragment_container, fragment);

	}

	public void CreatemyTicketTab() {
		actionBar.removeAllTabs();
		Boolean isInternetPresent = cd.isConnectingToInternet();
		ActionBar.Tab NewEventTab = actionBar.newTab().setText("Comming Events")
				.setTabListener(this);
		actionBar.addTab(NewEventTab);
		ActionBar.Tab xNewEventTab = actionBar.newTab().setText("Past Events")
				.setTabListener(this);
		actionBar.addTab(xNewEventTab);
		
		if (!isInternetPresent) {
			System.out.println("Not Connected...");
			NoEventsError mNoEventsError = new NoEventsError();
			getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, mNoEventsError).commit();
		} else {
			MyTicketUpcomingEvents MyTicket = new MyTicketUpcomingEvents();
			getSupportFragmentManager().beginTransaction()
					.replace(R.id.fragment_container, MyTicket)
					.commit();
		}
		
	 
	}

	public void CreateDefaulTabs() {
		actionBar.removeAllTabs();
		Boolean isInternetPresent = cd.isConnectingToInternet();
		if (!EvmaApp.CurrentOrganizer.isEmpty()) {
			ActionBar.Tab MyEventsTab = actionBar.newTab().setText("Mes Events").setTabListener(this);
			actionBar.addTab(MyEventsTab);
			ActionBar.Tab NewEventTab = actionBar.newTab().setText("Nv. Event").setTabListener(this);
			actionBar.addTab(NewEventTab);
		}else if ( EvmaApp.CurrentOrganizer.isEmpty()) {
			ActionBar.Tab EventsTab = actionBar.newTab().setText("Découvrir")
					.setTabListener(this);
			actionBar.addTab(EventsTab);
//			ActionBar.Tab MesPlansTab = actionBar.newTab().setText("Mes plans")
//					.setTabListener(this);
//			actionBar.addTab(MesPlansTab);
		}
		if (!isInternetPresent) {
			System.out.println("Not Connected...");
			NoEventsError mNoEventsError = new NoEventsError();
			getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, mNoEventsError).commit();

	 			 
		} else {
			if (!EvmaApp.CurrentOrganizer.isEmpty()) {

				if (isInternetPresent) {
					Organizer_Events Organizer_Eventslist = new Organizer_Events();
					getSupportFragmentManager()
							.beginTransaction()
							.replace(R.id.fragment_container,
									Organizer_Eventslist).commit();
				}
	 			 
			} else if ( EvmaApp.CurrentOrganizer.isEmpty()) {
				 
				if (isInternetPresent) {
					EventsUI androidversionlist = new EventsUI();
					getSupportFragmentManager()
							.beginTransaction()
							.replace(R.id.fragment_container,
									androidversionlist).commit();
				}

			}
		}
		
	}

	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        System.out.println("onActivityResult");
		Organizer_event_create mOrganizer_Event_create = new Organizer_event_create();
		mOrganizer_Event_create.onActivityResult(requestCode, resultCode, data);
         if (requestCode == RESULT_LOAD_IMAGE && resultCode ==  RESULT_OK && null != data) {
//        	String NewEv = data.getExtras().getString("NewEv");
//            System.out.println("onActivityResult Iget it " + NewEv);
            System.out.println("resultCode  "  + resultCode);
        	Uri selectedImage = data.getData();
            String[] filePathColumn = { MediaStore.Images.Media.DATA };
 
            Cursor cursor =  getContentResolver().query(selectedImage,
                    filePathColumn, null, null, null);
            cursor.moveToFirst();
 
            int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
            String picturePath = cursor.getString(columnIndex);
            cursor.close();
			//mOrganizer_Event_create = new Organizer_event_create();
			 
			//mEvmaApp =  (EvmaApp) getApplication();
			//mEvmaApp.helperNewEv = mEv;
			EvmaApp.helperpicturePath = picturePath;
			 

			 
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,mOrganizer_Event_create).commit();
            //OrgaImg.setImageBitmap(BitmapFactory.decodeFile(picturePath));
         
        }
	 }
}
