package com.lpii.evma.view.organizer;

import android.app.ActionBar.Tab;
import android.app.ActionBar.TabListener;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
 

/**
 * Base launcher activity, to handle most of the common plumbing for samples.
 */
public class SampleActivityBase extends FragmentActivity implements TabListener{

    public static final String TAG = "SampleActivityBase";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected  void onStart() {
        super.onStart();
        initializeLogging();
    }

    /** Set up targets to receive log data */
    public void initializeLogging() {
        
        Log.i(TAG, "Ready");
    }

	@Override
	public void onTabReselected(Tab tab, FragmentTransaction ft) {
		// TODO Auto-generated method stub
		
		
	}

	@Override
	public void onTabSelected(Tab tab, FragmentTransaction ft) {
		// TODO Auto-generated method stub
		System.out.println("sleecteeeeeeeeeeeed");
		
	}

	@Override
	public void onTabUnselected(Tab tab, FragmentTransaction ft) {
		// TODO Auto-generated method stub
		
	}
}
