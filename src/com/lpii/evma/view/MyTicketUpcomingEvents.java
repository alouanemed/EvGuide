package com.lpii.evma.view;


import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import com.lpii.evma.EvmaApp;
import com.lpii.evma.R;
import com.lpii.evma.controller.ConnectionDetector;
import com.lpii.evma.controller.EventsController;
import com.lpii.evma.view.organizer.OrganizerMainActivity;
 

public class MyTicketUpcomingEvents extends ListFragment{
	private ProgressDialog pDialog;
	EventsController mEventsController;
	private boolean resumeHasRun = false;

	public static String UsernameOrga = "";
	TextView etEvtitle;
	ListAdapter adapter; 
	LinearLayout lyEmptyOrError;
	ConnectionDetector cd;
 
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		mEventsController = new EventsController(); 
		cd = new ConnectionDetector(getActivity());
 		Boolean isInternetPresent = cd.isConnectingToInternet(); 
		if (isInternetPresent) {
			System.out.println("wishone  => " + EvmaApp.MyTicketPast);

			if (getActivity() != null) {
				if ( EvmaApp.GlobaleUserPariticpationsEventListP== null || EvmaApp.GlobaleUserPariticpationsEventListP.isEmpty() || EvmaApp.GlobaleUserPariticpationsEventList== null || EvmaApp.GlobaleUserPariticpationsEventList.isEmpty()) {
					System.out.println(" MyTicketUpcomingEvents it's emtpyyx");
					new GetEvents().execute();
				}else{
 					if (EvmaApp.MyTicketPast) {
 						EvmaApp.MyTicketPast = false;
						System.out.println("MyTicketUpcomingEvents  it's Full ");
						adapter = new SimpleAdapter(getActivity(),
								EvmaApp.GlobaleUserPariticpationsEventListP, R.layout.my_ticket_event_item,
								new String[] { EventsController.TAG_NAME,
										EventsController.TAG_dateTime_day,
										EventsController.TAG_dateTime_month ,
										EventsController.TAG_ID }, new int[] {
										R.id.tvTicketEvTitle,
										R.id.tvTicketEvDay,
										R.id.tvTicketEvMonth,
										R.id.tvTicketevent_id });
						setListAdapter(adapter);
					}else{
 						EvmaApp.MyTicketPast = true;
						System.out.println("it's Fullxxx");
						adapter = new SimpleAdapter(getActivity(),
								EvmaApp.GlobaleUserPariticpationsEventList, R.layout.my_ticket_event_item,
								new String[] { EventsController.TAG_NAME,
										EventsController.TAG_dateTime_day,
										EventsController.TAG_dateTime_month ,
										EventsController.TAG_ID }, new int[] {
										R.id.tvTicketEvTitle,
										R.id.tvTicketEvDay,
										R.id.tvTicketEvMonth,
										R.id.tvTicketevent_id });
						setListAdapter(adapter);
					}
					 
				}
			}	 
		}else{
			System.out.println(" no tConnected...");
			NoEventsError mNoEventsError = new NoEventsError();
			getFragmentManager().beginTransaction().replace(R.id.fragment_container, mNoEventsError).commit();
		}
		
		  
	}
	

	 

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View v = inflater.inflate(R.layout.activity_user_my_ticket_upcomingevent, container, false);
		lyEmptyOrError = (LinearLayout) v.findViewById(R.id.eventsemptyerror);
 		Boolean isInternetPresent = cd.isConnectingToInternet(); 
		if (!isInternetPresent) {
			System.out.println("Not Connected...");
			lyEmptyOrError.setVisibility(LinearLayout.VISIBLE);
		}
		
		return v;
	}
	
	@Override
	public void onResume() {
	    super.onResume();
	    if (!resumeHasRun) {
	        resumeHasRun = true;
	        return;
	    }
	    System.out.println("onResume Bro");
	    
	}
	 
	@Override
	public void onListItemClick(ListView list, View v, int position, long id) {
		String EvTitle = ((TextView) v.findViewById(R.id.tvTicketEvTitle))
				.getText().toString();
//		String dateTime = ((TextView) v.findViewById(R.id.event_description_date))
//				.getText().toString();
		String EvID = ((TextView) v.findViewById(R.id.tvTicketevent_id))
				.getText().toString();
 
		System.out.println("===> "+ EvTitle);
		Intent in = new Intent(getActivity(), MyTicketForfaitsList.class);
		in.putExtra(EventsController.TAG_NAME, EvTitle);
		//in.putExtra(EventsController.TAG_dateTime, dateTime);
		in.putExtra(EventsController.TAG_ID, EvID);
 		EvmaApp.LastEventID  = EvID; 
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
			mEventsController.getUserMyTickets(EvmaApp.CurrentUsernameID +"");

			return null;
		}

		@Override
		protected void onPostExecute(Void result) {
			super.onPostExecute(result);
			// Dismiss the progress dialog
			if (pDialog.isShowing())
				pDialog.dismiss();
			System.out.println("it's done");
			EvmaApp.GlobaleUserPariticpationsEventList = mEventsController.getSingleUserEventsPariticpationsC();
			EvmaApp.GlobaleUserPariticpationsEventListP = mEventsController.getSingleUserEventsPariticpationsP();

			System.out.println("it's Full");
			if (getActivity() != null) {
				if (EvmaApp.MyTicketPast) {
					System.out.println("it's Fullxxx");
					adapter = new SimpleAdapter(getActivity(),
							EvmaApp.GlobaleUserPariticpationsEventListP, R.layout.my_ticket_event_item,
							new String[] { EventsController.TAG_NAME,
									EventsController.TAG_dateTime_day,
									EventsController.TAG_dateTime_month ,
									EventsController.TAG_ID }, new int[] {
									R.id.tvTicketEvTitle,
									R.id.tvTicketEvDay,
									R.id.tvTicketEvMonth,
									R.id.tvTicketevent_id });
					setListAdapter(adapter);
				}else{
					System.out.println("it's Fullxxx");
					adapter = new SimpleAdapter(getActivity(),
							EvmaApp.GlobaleUserPariticpationsEventList, R.layout.my_ticket_event_item,
							new String[] { EventsController.TAG_NAME,
									EventsController.TAG_dateTime_day,
									EventsController.TAG_dateTime_month ,
									EventsController.TAG_ID }, new int[] {
									R.id.tvTicketEvTitle,
									R.id.tvTicketEvDay,
									R.id.tvTicketEvMonth,
									R.id.tvTicketevent_id });
					setListAdapter(adapter);
				}
//				adapter = new SimpleAdapter(getActivity(),
//						EvmaApp.GlobaleUserPariticpationsEventList, R.layout.my_ticket_event_item, new String[] {
//								EventsController.TAG_NAME,
//								EventsController.TAG_dateTime_day,
//								EventsController.TAG_dateTime_month,
//								EventsController.TAG_ID }, new int[] {
//								R.id.tvTicketEvTitle, R.id.tvTicketEvDay,
//								R.id.tvTicketEvMonth, R.id.tvTicketevent_id });
//				setListAdapter(adapter);
			}
			 
 
		}

	}

}
