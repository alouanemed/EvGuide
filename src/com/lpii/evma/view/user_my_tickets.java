package com.lpii.evma.view;

 

import java.util.ArrayList;

import android.app.ActionBar;
import android.app.ActionBar.OnNavigationListener;
import android.app.ActionBar.Tab;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.LayerDrawable;
import android.graphics.drawable.TransitionDrawable;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.util.TypedValue;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

import com.lpii.evma.MainEv;
import com.lpii.evma.R; 
import com.lpii.evma.adapter.TitleNavigationAdapter;
import com.lpii.evma.model.SpinnerNavItem;
import com.lpii.evma.view.organizer.OrganizerEvForfaitFragment;
import com.lpii.evma.view.organizer.OrganizerEvInfosFragment;
import com.lpii.evma.view.organizer.OrganizerEvOverViewFragment;
import com.lpii.evma.view.organizer.OrganizerEvParticipantsFragment;
import com.lpii.evma.view.organizer.PagerSlidingTabStripEv;


public class user_my_tickets extends FragmentActivity implements  OnNavigationListener  {

	private final Handler handler = new Handler();

	private PagerSlidingTabStripEv tabs;
	private ViewPager pager;
	private MyPagerAdapter adapter;

	private Drawable oldBackground = null;
	private int currentColor = 0xFF666666;
	private ActionBar actionBar;
	private ArrayList<SpinnerNavItem> navSpinner;

	// Navigation adapter
	private TitleNavigationAdapter Navigationadapter;
	private DrawerLayout mDrawerLayout;
	private ActionBarDrawerToggle mDrawerToggle;
	Button btnDiscover;


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_user_my_tickets);

		tabs = (PagerSlidingTabStripEv) findViewById(R.id.tabsyTickets);
		pager = (ViewPager) findViewById(R.id.pagermMyTickets);
		adapter = new MyPagerAdapter(getSupportFragmentManager());
		
		pager.setAdapter(adapter);


		final int pageMargin = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 1, getResources()
				.getDisplayMetrics());
		pager.setPageMargin(pageMargin);

		tabs.setViewPager(pager);
		
		actionBar = getActionBar();

		// Hide the action bar title
		actionBar.setDisplayShowTitleEnabled(false);

		// Enabling Spinner dropdown navigation
		//actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);//NAVIGATION_MODE_LIST
		 
		// Spinner title navigation data
		navSpinner = new ArrayList<SpinnerNavItem>();
		navSpinner.add(new SpinnerNavItem("Local", R.drawable.ic_action_locate));
		navSpinner.add(new SpinnerNavItem("My Places", R.drawable.ic_action_locate));
		navSpinner.add(new SpinnerNavItem("Checkins", R.drawable.ic_action_send));
		navSpinner.add(new SpinnerNavItem("Latitude", R.drawable.ic_action_video));

		// title drop down adapter
		Navigationadapter = new TitleNavigationAdapter(getApplicationContext(),
				navSpinner);

 		actionBar.setListNavigationCallbacks(Navigationadapter, new OnNavigationListener() {
			
			@Override
			public boolean onNavigationItemSelected(int arg0, long arg1) {
				// TODO Auto-generated method stub
				System.out.println("clicked");
				return false;
			}
		});

 		actionBar.setIcon(R.drawable.ic_launcher);
		mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layoutMy);
	    mDrawerToggle = new ActionBarDrawerToggle(
	                this,                  /* host Activity */
	                mDrawerLayout,         /* DrawerLayout object */
	                R.drawable.ic_drawer,  /* nav drawer icon to replace 'Up' caret */
	                R.string.drawer_open,  /* "open drawer" description */
	                R.string.drawer_close  /* "close drawer" description */
	        );

	    
	    mDrawerLayout.setDrawerListener(mDrawerToggle);

    	actionBar.setDisplayHomeAsUpEnabled(true);
		actionBar.setHomeButtonEnabled(true);   
		
		actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_STANDARD);
        
		btnDiscover = (Button) findViewById(R.id.drawer_layoutuser__find_events);
		btnDiscover.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				Drawable d = getResources().getDrawable(R.drawable.drawer_item_selected);
				
				btnDiscover.setBackgroundDrawable(d);
				
				Intent intUser = new Intent(user_my_tickets.this,MainEv.class);
				startActivity(intUser);
			}
		});
		
		  
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.main_ev, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {

		if (mDrawerToggle.onOptionsItemSelected(item)) {
	          return true;
	        } 
		return super.onOptionsItemSelected(item);
	}
 

	 

	private Drawable.Callback drawableCallback = new Drawable.Callback() {
		@Override
		public void invalidateDrawable(Drawable who) {
			getActionBar().setBackgroundDrawable(who);
		}

		@Override
		public void scheduleDrawable(Drawable who, Runnable what, long when) {
			handler.postAtTime(what, when);
		}

		@Override
		public void unscheduleDrawable(Drawable who, Runnable what) {
			handler.removeCallbacks(what);
		}
	};

	public class MyPagerAdapter extends FragmentPagerAdapter {

		private final String[] TITLES = { "Comoing event", "Past event"};

		public MyPagerAdapter(FragmentManager fm) {
			super(fm);
		}

		@Override
		public CharSequence getPageTitle(int position) {
			return TITLES[position];
		}

		@Override
		public int getCount() {
			return TITLES.length;
		}

		@Override
		public Fragment getItem(int position) {
			System.out.println(" getItemposition " + position);
			 switch (position) {
		        case 0:
		            return new MyTicketUpcomingEvents();
		        case 1:		            
		            return new MyTicketUpcomingEvents();
		            
			  }
		        return null;
		}

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
	public boolean onNavigationItemSelected(int arg0, long arg1) {
		// TODO Auto-generated method stub
		return false;
	}



} 
