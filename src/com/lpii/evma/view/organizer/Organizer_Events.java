package com.lpii.evma.view.organizer;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnLongClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import com.lpii.evma.EvmaApp;
import com.lpii.evma.MainEv;
import com.lpii.evma.R;
import com.lpii.evma.controller.EventsController;

public class Organizer_Events<MainActivity> extends ListFragment {

	private ProgressDialog pDialog;
	EventsController mEventsController;
	private boolean resumeHasRun = false;

	public static String UsernameOrga = "";
	TextView etEvtitle;
	ListAdapter adapter;

	public Organizer_Events() {

	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		mEventsController = new EventsController();

		if (getActivity() != null) {
			if (EvmaApp.GlobaleOrgaventList == null) {
				System.out.println("it's emtpyyx");
				new GetEvents().execute();
			} else {
				System.out.println("it's Fullxxx");
				adapter = new SimpleAdapter(getActivity(),
						EvmaApp.GlobaleOrgaventList, R.layout.liste_item_v,
						new String[] { EventsController.TAG_NAME,
								EventsController.TAG_dateTime,
								EventsController.TAG_visible,
								EventsController.TAG_dateTime_fin,
								EventsController.TAG_event_cover,
								EventsController.TAG_event_Description,
								EventsController.TAG_ID }, new int[] {
								R.id.name, R.id.event_description_datex,
								R.id.event_visible,
								R.id.event_description_dateFIN,
								R.id.event_cover_url,R.id.event_description, R.id.event_id });
				setListAdapter(adapter);
			}
		}

		EvmaApp.GlobaleOrgaventList = mEventsController.getEventList();
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		return inflater.inflate(R.layout.listview, container, false);
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
		String EvTitle = ((TextView) v.findViewById(R.id.name)).getText()
				.toString();
		String dateTime = ((TextView) v
				.findViewById(R.id.event_description_datex)).getText()
				.toString();
		String EvID = ((TextView) v.findViewById(R.id.event_id)).getText()
				.toString();
		String iSvisible = ((TextView) v.findViewById(R.id.event_visible))
				.getText().toString();
		String dateTime_fin = ((TextView) v
				.findViewById(R.id.event_description_dateFIN)).getText()
				.toString();
		String EvCover = ((TextView) v
				.findViewById(R.id.event_cover_url)).getText()
				.toString();
		String xevent_description = ((TextView) v
				.findViewById(R.id.event_description)).getText()
				.toString();
		 
		System.out.println("===> " + EvTitle);
		Intent in = new Intent(getActivity(), OrganizerMainActivity.class);
		in.putExtra(EventsController.TAG_NAME, EvTitle);
		in.putExtra(EventsController.TAG_dateTime, dateTime);
		in.putExtra(EventsController.TAG_ID, EvID);
		in.putExtra(EventsController.TAG_visible, iSvisible);
		in.putExtra(EventsController.TAG_dateTime_fin, dateTime_fin);
		in.putExtra(EventsController.TAG_event_cover, EvCover);
		in.putExtra(EventsController.TAG_event_Description, xevent_description);
		in.putExtra("type", "ShowEvent");
		EvmaApp.LastEventID = EvID;
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
			mEventsController.getOrgaEvents(EvmaApp.CurrentUsernameID + "");

			return null;
		}

		@Override
		protected void onPostExecute(Void result) {
			super.onPostExecute(result);
			// Dismiss the progress dialog
			if (pDialog.isShowing())
				pDialog.dismiss();
			System.out.println("it's done");
			EvmaApp.GlobaleOrgaventList = mEventsController.getEventList();
			System.out.println("it's Full");
			if (getActivity() != null) {
				adapter = new SimpleAdapter(getActivity(),
						mEventsController.getEventList(),
						R.layout.liste_item_v, new String[] {
					 EventsController.TAG_NAME,
						EventsController.TAG_dateTime,
						EventsController.TAG_visible,
						EventsController.TAG_dateTime_fin,
						EventsController.TAG_event_cover,
						EventsController.TAG_event_Description,
						EventsController.TAG_ID }, new int[] {
						R.id.name, R.id.event_description_datex,
						R.id.event_visible,
						R.id.event_description_dateFIN,
						R.id.event_cover_url,R.id.event_description, R.id.event_id });
				setListAdapter(adapter);
			}

		}

	}

}
