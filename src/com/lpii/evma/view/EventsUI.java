package com.lpii.evma.view;
 

import java.util.ArrayList;
import java.util.HashMap;

import com.lpii.evma.EvmaApp;
import com.lpii.evma.R;
import com.lpii.evma.controller.EventsController; 
import com.lpii.evma.model.EventsAdapter;
import com.lpii.evma.model.ForfaitAdapter;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;
import android.widget.TextView;
import android.content.Intent;
import android.graphics.Typeface;

public class EventsUI<MainActivity> extends ListFragment {

	private ProgressDialog pDialog;
	EventsController mEventsController;

	public static String UsernameOrga = "";
	TextView etEvtitle;
	ListAdapter adapter;
	Typeface tfBold;

	public EventsUI() {

	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		// ListAdapter listAdapter = new ArrayAdapter<String>(getActivity(),
		// R.layout.liste_item_v, myandroidphone);
		// setListAdapter(listAdapter);

		mEventsController = new EventsController();
		EvmaApp.CurrentmEventsController = mEventsController;
  
		if (getActivity() != null) {
			tfBold = Typeface.createFromAsset(getActivity().getAssets(), "fonts/Roboto-Medium.ttf");
			if ( EvmaApp.GlobaleventList== null) {
				System.out.println("it's emtpyyx");
				new GetEvents().execute();
			}else{
				new GetEvents().execute();
				System.out.println("it's Fullxxx");
				

				ListAdapter adapterx = new EventsAdapter(getActivity(),EvmaApp.GlobaleventList, tfBold, mEventsController);
				setListAdapter(adapterx);
			}
		}	 

		EvmaApp.GlobaleventList = mEventsController.getEventList(); 
		 
		/*
		 * Typeface tf = Typeface.createFromAsset(getActivity().getAssets(),
		 * "fonts/Roboto-Thin.ttf"); etEvtitle = (TextView)
		 * getActivity().findViewById(R.id.name); etEvtitle.setTypeface(tf);
		 */

	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		return inflater.inflate(R.layout.listview, container, false);
	}

	@Override
	public void onListItemClick(ListView list, View v, int position, long id) {
		System.out.println("position " + position);
		/*Toast.makeText(getActivity(),
				getListView().getItemAtPosition(position).toString(),
				Toast.LENGTH_LONG).show();*/
		
		

		String EvTitle = ((TextView) v.findViewById(R.id.EvTitle))
				.getText().toString();
		String dateTime = ((TextView) v.findViewById(R.id.event_description_date))
				.getText().toString();
		String dateTimeFIN = ((TextView) v.findViewById(R.id.event_xdescription_dateFIN))
				.getText().toString();
		String event_description = ((TextView) v.findViewById(R.id.card_event_description))
				.getText().toString();
		String EvID = ((TextView) v.findViewById(R.id.Ev_Idx))
				.getText().toString();
		System.out.println("===> "+ EvTitle);
		Intent in = new Intent(getActivity(), EventDescriptionUI.class);
		in.putExtra(EventsController.TAG_NAME, EvTitle);
		in.putExtra(EventsController.TAG_dateTime, dateTime);
		in.putExtra(EventsController.TAG_ID, EvID);
		in.putExtra(EventsController.TAG_event_Description, event_description);
		in.putExtra(EventsController.TAG_dateTime_fin, dateTimeFIN);
		
		in.putExtra("Organisateur", EventsController.getUsernameOrga());
		 

		startActivity(in);

	}

 
	private class GetEvents extends AsyncTask<Void, Void, Void> {

		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			// Showing progress dialog
			pDialog = new ProgressDialog(getActivity());
			pDialog.setMessage("Please wait...");
			pDialog.setCancelable(false);
			pDialog.show();

		}

		@Override
		protected Void doInBackground(Void... arg0) {
			mEventsController.getEvents();
			EvmaApp.GlobaleventList = mEventsController.getEventList();

			return null;
		}

		@Override
		protected void onPostExecute(Void result) {
			super.onPostExecute(result);
			// Dismiss the progress dialog
			if (pDialog.isShowing())
				pDialog.dismiss();
			ArrayList<HashMap<String, String>>  xeventList = null;

			if (EvmaApp.GlobaleventList != null && !EvmaApp.GlobaleventList.isEmpty()) {
				xeventList = EvmaApp.GlobaleventList;
			}else if (EvmaApp.GlobaleventList == null || EvmaApp.GlobaleventList.isEmpty()) {
				xeventList = mEventsController.getEventList();;
			}
//			System.out.println("size :" + xeventList.size());
//			System.out.println(xeventList.toString());
//			System.out.println("================");
			if (getActivity() == null) {
				System.out.println("null");
			}
 			ListAdapter adapterx = new EventsAdapter(getActivity(),xeventList, tfBold, mEventsController);


			setListAdapter(adapterx);
			
// 			adapter = new SimpleAdapter(getActivity(),
//					 xeventList, R.layout.list_event_item2,
//					new String[] { 
// 							EventsController.TAG_NAME,
//							EventsController.TAG_event_Description,
//							EventsController.TAG_dateTime,
//							EventsController.TAG_dateTime_fin,
//							EventsController.TAG_ID ,
//							EventsController.TAG_ID ,
//							EventsController.TAG_event_MinPrice ,
//							EventsController.TAG_dateTime_month ,
//							EventsController.TAG_dateTime_day }, 
//							new int[] {
// 							R.id.EvTitle,
//							R.id.card_event_description,
//							R.id.event_description_date,
//							R.id.event_xdescription_dateFIN,
//							R.id.Ev_Idx,R.id.Ev_idy,
//							R.id.Ev_Start_Pricex,
//							R.id.EvDateMonth,
//							R.id.EvDateDay });
//			System.out.println(adapter.getCount() + "  ==  "
//					+ adapter.getItem(0));
//			setListAdapter(adapter);
		}

	}

}
