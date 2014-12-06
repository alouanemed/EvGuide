

package com.lpii.evma.view.organizer;

import com.lpii.evma.R; 
import android.os.Bundle;
import android.support.v4.app.Fragment; 
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup; 
import android.view.View.OnClickListener;
import android.widget.Button; 

public class Organizer_Forfait_create extends Fragment {

	Button btnNextEvent;
	View rootView;
	LayoutInflater mInflater;
	ViewGroup mContainer;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
 
		mInflater = inflater;
		mContainer = container;
		rootView = mInflater.inflate(R.layout.organizer_pack_create, container, false);
		
		 
			
			 
		return rootView;
		
	}

}