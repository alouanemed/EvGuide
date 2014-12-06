
package com.lpii.evma.view.organizer;

import com.lpii.evma.EvmaApp;
import com.lpii.evma.MainEv;
import com.lpii.evma.R;
import com.lpii.evma.controller.EventsController;

import android.app.ActionBar;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.LayerDrawable;
import android.graphics.drawable.TransitionDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.util.TypedValue;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TabHost.OnTabChangeListener;


public class OrganizerMainActivity extends FragmentActivity  {

	private final Handler handler = new Handler();

	private PagerSlidingTabStripEv tabs;
	private ViewPager pager;
	private MyPagerAdapter adapter;

	private Drawable oldBackground = null;
	private int currentColor = 0xFF666666;
	private ActionBar actionBar;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		tabs = (PagerSlidingTabStripEv) findViewById(R.id.tabs);
		pager = (ViewPager) findViewById(R.id.pagerA);
		adapter = new MyPagerAdapter(getSupportFragmentManager());
		
		pager.setAdapter(adapter);
		Intent mInt = getIntent();
		if (mInt != null) {
 
			if (!mInt.getStringExtra("type").equals("ShowEvent")) {
				pager.setCurrentItem(2);
			}
		}

		final int pageMargin = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 4, getResources()
				.getDisplayMetrics());
		pager.setPageMargin(pageMargin);

		tabs.setViewPager(pager);
		  
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.main_ev, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {

		System.out.println("dfddd");
		switch (item.getItemId()) {

		case R.id.action_refresh:
			QuickContactFragment dialog = new QuickContactFragment();
			dialog.show(getSupportFragmentManager(), "QuickContactFragment");
			return true;

		}

		return super.onOptionsItemSelected(item);
	}

	private void changeColor(int newColor) {

		tabs.setIndicatorColor(newColor);

		// change ActionBar color just if an ActionBar is available
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {

			Drawable colorDrawable = new ColorDrawable(newColor);
			Drawable bottomDrawable = getResources().getDrawable(R.drawable.actionbar_bottom);
			LayerDrawable ld = new LayerDrawable(new Drawable[] { colorDrawable, bottomDrawable });

			if (oldBackground == null) {

				if (Build.VERSION.SDK_INT < Build.VERSION_CODES.JELLY_BEAN_MR1) {
					ld.setCallback(drawableCallback);
				} else {
					getActionBar().setBackgroundDrawable(ld);
				}

			} else {

				TransitionDrawable td = new TransitionDrawable(new Drawable[] { oldBackground, ld });

				// workaround for broken ActionBarContainer drawable handling on
				// pre-API 17 builds
				// https://github.com/android/platform_frameworks_base/commit/a7cc06d82e45918c37429a59b14545c6a57db4e4
				if (Build.VERSION.SDK_INT < Build.VERSION_CODES.JELLY_BEAN_MR1) {
					td.setCallback(drawableCallback);
				} else {
					getActionBar().setBackgroundDrawable(td);
				}

				td.startTransition(200);

			}

			oldBackground = ld;

			// http://stackoverflow.com/questions/11002691/actionbar-setbackgrounddrawable-nulling-background-from-thread-handler
			getActionBar().setDisplayShowTitleEnabled(false);
			getActionBar().setDisplayShowTitleEnabled(true);

		}

		currentColor = newColor;

	}

	public void onColorClicked(View v) {

		int color = Color.parseColor(v.getTag().toString());
		changeColor(color);

	}

	@Override
	protected void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
		outState.putInt("currentColor", currentColor);
	}

	@Override
	protected void onRestoreInstanceState(Bundle savedInstanceState) {
		super.onRestoreInstanceState(savedInstanceState);
		currentColor = savedInstanceState.getInt("currentColor");
		changeColor(currentColor);
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

		private final String[] TITLES = { "Overview", "Infos", "Forfait", "Participants", "Parametres", "Contact"};

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
		            return new OrganizerEvOverViewFragment();
		        case 1:		            
		            return new OrganizerEvInfosFragment();
		        case 2:
		            return new OrganizerEvForfaitFragment();
		        case 3:
		            return new OrganizerEvParticipantsFragment();   
		        case 4:
		            return new OrganizerEvParticipantsFragment();   
		        case 5:
		        	return null;// return new OrganizerEvParticipantsFragment();        
			  }
		        return null;
		}

	}

	 
	public void showAddForfaitForms(View view){
		OrganizerEvForfaitFragment.showAddForfaitForms(view);
	}


	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        System.out.println("onActivityResult  OrganizerMainActivity");
        OrganizerEvInfosFragment mOrganizerEvInfosFragment = new OrganizerEvInfosFragment();
       // mOrganizerEvInfosFragment.onActivityResult(requestCode, resultCode, data);
         if (requestCode == MainEv.RESULT_LOAD_IMAGE && resultCode ==  RESULT_OK && null != data) {
//        	String NewEv = data.getExtras().getString("NewEv");
//            System.out.println("onActivityResult Iget it " + NewEv);
            System.out.println("onActivityResult wwwxxxresultCode  "  + resultCode);
        	Uri selectedImage = data.getData();
            String[] filePathColumn = { MediaStore.Images.Media.DATA };
 
            Cursor cursor =  getContentResolver().query(selectedImage,
                    filePathColumn, null, null, null);
            cursor.moveToFirst();
 
            int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
            String picturePath = cursor.getString(columnIndex);
            cursor.close();
			EvmaApp.helperpicturePath = picturePath;
			System.out.println("helperpicturePath" + EvmaApp.helperpicturePath);
			pager.setCurrentItem(1);
			//getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,mOrganizerEvInfosFragment).commit();
            //OrgaImg.setImageBitmap(BitmapFactory.decodeFile(picturePath));
         
        }
	 }
}
