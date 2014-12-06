package com.lpii.evma.view;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.lpii.evma.EvmaApp;
import com.lpii.evma.MainEv;
import com.lpii.evma.R;

public class NoEventsError  extends Fragment {
	
	Button btnRetry;

	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
 		View rootView;	
		LayoutInflater mInflater;
		ViewGroup mContainer;
		mInflater = inflater;
		mContainer = container;
		rootView = mInflater.inflate(R.layout.no_events_error_ac, container, false);
 		
		btnRetry = (Button) rootView.findViewById(R.id.btnRetry);
		btnRetry.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				EvmaApp.forceGetEvent = true;
				startActivity(new Intent(getActivity(),MainEv.class));
			}
		});
		 
		return rootView;
		
	}
 

}
